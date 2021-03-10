package com.lin.llcf.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.llcf.common.enums.CfgPropertiesValueType;
import com.lin.llcf.common.model.dtos.PropertyDTO;
import com.lin.llcf.common.utils.JsonUtils;
import com.lin.llcf.server.dao.CfgPropertyDao;
import com.lin.llcf.server.dao.CfgPropertyDefinitionDao;
import com.lin.llcf.server.domain.dos.CfgPropertyDO;
import com.lin.llcf.server.domain.dos.CfgPropertyDefinitionDO;
import com.lin.llcf.server.domain.vos.CfgPropertyVO;
import com.lin.llcf.server.domain.vos.CfgModuleVO;
import com.lin.llcf.server.domain.vos.CfgProjectVO;
import com.lin.llcf.server.event.EventPublisher;
import com.lin.llcf.server.event.propertyevent.PropertyUpdateEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CfgPropertyService {

    @Autowired
    private CfgPropertyDefinitionDao cfgPropertyDefinitionDao;
    @Autowired
    private CfgPropertyDao cfgPropertyDao;

    @Autowired
    private CfgProjectService cfgProjectService;
    @Autowired
    private CfgModuleService cfgModuleService;
    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private ObjectMapper objectMapper;

    public PropertyDTO getProperty(Long projectId, Long moduleId, String parentKey, String propertyKey) {
        CfgPropertyDefinitionDO definition = cfgPropertyDefinitionDao.getByProjectIdAndModuleIdAndPropertyKey(projectId, moduleId, parentKey);
        if (definition == null) {
            return null;
        }
        CfgPropertyDO propertyDo = cfgPropertyDao.getByDefinitionIdAndPropertyKey(definition.getId(), propertyKey);
        List<PropertyDTO> res = convertDoToDto(Collections.singletonList(propertyDo), definition.getPropertyKey());
        if (CollectionUtils.isEmpty(res)) {
            return null;
        }else {
            return res.get(0);
        }
    }

    public List<PropertyDTO> getPropertyList(String appKey) {
        CfgModuleVO moduleVo = cfgModuleService.getByAppKey(appKey);
        if (moduleVo == null) {
            throw new RuntimeException("当前appKey不存在");
        }

        List<CfgPropertyDefinitionDO> definitionList = cfgPropertyDefinitionDao.getByProjectIdAndModuleId(moduleVo.getProjectId(), moduleVo.getId());
        if (CollectionUtils.isEmpty(definitionList)) {
            return Collections.emptyList();
        }

        List<PropertyDTO> resultList = new ArrayList<>(definitionList.size());
        for (CfgPropertyDefinitionDO definition : definitionList) {
            List<CfgPropertyDO> propertyList = cfgPropertyDao.getByDefinitionId(definition.getId());
            resultList.addAll(convertDoToDto(propertyList, definition.getPropertyKey()));
        }

        return resultList;
    }

    public List<PropertyDTO> convertDoToDto(List<CfgPropertyDO> propertyList, String preKey){
        if (CollectionUtils.isEmpty(propertyList)) {
            return Collections.emptyList();
        }
        List<PropertyDTO> dtoList = new ArrayList<>(propertyList.size());
        for (CfgPropertyDO propertyDo : propertyList) {
            PropertyDTO dto = new PropertyDTO();
            CfgPropertiesValueType valueType = CfgPropertiesValueType.getById(propertyDo.getCfgType());
            dto.setValueType(valueType);
            dto.setKey(preKey + "." + propertyDo.getPropertyKey());
            dto.setOriginValue(propertyDo.getPropertyValue());

            Object value = null;
            switch (valueType){
                case STRING: value = propertyDo.getPropertyValue(); break;
                case INTEGER: value = Integer.valueOf(propertyDo.getPropertyValue()); break;
                case DECIMAL: value = new BigDecimal(propertyDo.getPropertyValue()); break;
                case INT_ARRAY: {
                    value = JsonUtils.readList(propertyDo.getPropertyValue(), Integer.class);
                }
                break;
                case STR_ARRAY:{
                    value = JsonUtils.readList(propertyDo.getPropertyValue(), String.class);
                }
                break;
                default:break;
            }
            dto.setValue(value);

            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void save(CfgPropertyVO vo) {
        checkProjectAndModule(vo);

        CfgPropertyDefinitionDO definition = cfgPropertyDefinitionDao.getAndLockByPidAndMid(vo.getProjectId(), vo.getModuleId(), vo.getPropertyKey());
        if (definition == null) {
            Long definitionId = savePropertyDefinition(vo);
            saveOrUpdatePropertyItem(vo, definitionId);
        }else {
            saveOrUpdatePropertyItem(vo, definition.getId());
        }
        eventPublisher.pushAfterTransactional(new PropertyUpdateEvent(vo));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void update(CfgPropertyVO vo) {
        checkProjectAndModule(vo);

        CfgPropertyDefinitionDO definition = cfgPropertyDefinitionDao.getAndLockByPidAndMid(vo.getProjectId(), vo.getModuleId(), vo.getPropertyKey());
        if (definition == null) {
            throw new RuntimeException("此字典数据不存在，无法更新数据:" + vo.getProjectId());
        }

        boolean flag = false;
        if (!Objects.equals(definition.getPropertyKey(), vo.getPropertyKey())) {
            definition.setPropertyKey(vo.getPropertyKey());
            flag = true;
        }
        if (!Objects.equals(definition.getPropertyName(), vo.getPropertyName())) {
            definition.setPropertyName(vo.getPropertyName());
            flag = true;
        }
        if (!Objects.equals(definition.getPropertyDesc(), vo.getPropertyDesc())) {
            if (StringUtils.hasText(vo.getPropertyDesc())) {
                definition.setPropertyDesc(vo.getPropertyDesc());
            }else {
                definition.setPropertyDesc("");
            }
            flag = true;
        }

        if (flag) {
            cfgPropertyDefinitionDao.updateById(definition);
        }
        saveOrUpdatePropertyItem(vo, definition.getId());
        eventPublisher.pushAfterTransactional(new PropertyUpdateEvent(vo));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void remove(CfgPropertyVO vo) {
        checkProjectAndModule(vo);
        CfgPropertyDefinitionDO definition = cfgPropertyDefinitionDao.getAndLockByPidAndMid(vo.getProjectId(), vo.getModuleId(), vo.getPropertyKey());
        if (definition == null) {
            throw new RuntimeException("此字典数据不存在，无法更新数据:" + vo.getProjectId());
        }
        List<CfgPropertyDO> propertyList = cfgPropertyDao.getByDefinitionId(definition.getId());
        cfgPropertyDefinitionDao.delete(definition);
        cfgPropertyDao.deleteAll(propertyList);

        eventPublisher.pushAfterTransactional(new PropertyUpdateEvent(vo));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void removeItem(CfgPropertyVO vo) {
        checkProjectAndModule(vo);
        CfgPropertyDefinitionDO definition = cfgPropertyDefinitionDao.getAndLockByPidAndMid(vo.getProjectId(), vo.getModuleId(), vo.getPropertyKey());
        if (definition == null) {
            throw new RuntimeException("此字典数据不存在，无法更新数据:" + vo.getProjectId());
        }
        Set<String> removeKeys = vo.getItemList().stream().map(CfgPropertyVO.CfgPropertyItemVO::getPropertyKey)
                .collect(Collectors.toSet());

        List<CfgPropertyDO> removeList = cfgPropertyDao.getByDefinitionId(definition.getId());
        removeList.removeIf(o -> !removeKeys.contains(o.getPropertyKey()));
        if (!CollectionUtils.isEmpty(removeList)) {
            cfgPropertyDao.deleteAll(removeList);
        }

        eventPublisher.pushAfterTransactional(new PropertyUpdateEvent(vo));
    }

    private Long savePropertyDefinition(CfgPropertyVO vo) {
        CfgPropertyDefinitionDO definition = new CfgPropertyDefinitionDO();
        BeanUtils.copyProperties(vo, definition);
        if(!StringUtils.hasText(definition.getPropertyDesc())) {
            definition.setPropertyDesc("");
        }
        definition.setCreateTime(new Date());
        definition.setUpdateTime(new Date());
        cfgPropertyDefinitionDao.save(definition);
        return definition.getId();
    }

    private void saveOrUpdatePropertyItem(CfgPropertyVO vo, Long definitionId) {
        if (CollectionUtils.isEmpty(vo.getItemList())) {
            return;
        }

        List<CfgPropertyDO> propertyList = new ArrayList<>(vo.getItemList().size());
        for (CfgPropertyVO.CfgPropertyItemVO item : vo.getItemList()) {
            validateProperties(item.getCfgType(), item.getPropertyValue());

            CfgPropertyDO property = new CfgPropertyDO();
            BeanUtils.copyProperties(item, property);
            property.setDefinitionId(definitionId);
            property.setCreateTime(new Date());
            property.setUpdateTime(new Date());
            property.setCfgType(item.getCfgType().getId());
            if (!StringUtils.hasText(property.getPropertyValueDesc())) {
                property.setPropertyValueDesc("");
            }
            propertyList.add(property);
        }

        List<CfgPropertyDO> existPropertyList = cfgPropertyDao.getByDefinitionId(definitionId);
        for (CfgPropertyDO property : propertyList) {
            CfgPropertyDO existProperty = existPropertyList.stream().filter(o -> Objects.equals(property.getPropertyKey(), o.getPropertyKey()))
                    .findFirst().orElse(null);
            if (existProperty != null) {
                if (!Objects.equals(existProperty.getCfgType(), property.getCfgType())) {
                    throw new RuntimeException("不允许更新属性的类型:" + existProperty.getPropertyKey());
                }
                property.setId(existProperty.getId());
                cfgPropertyDao.updateById(property);
            }else {
                cfgPropertyDao.save(property);
            }
        }
    }

    private void validateProperties(CfgPropertiesValueType valueType, String inputContext) {
        if (valueType == CfgPropertiesValueType.STRING) {
            // no op
            return;
        }
        if (!StringUtils.hasText(inputContext)) {
            throw new RuntimeException("输入的数据不能为空:" + inputContext);
        }
        if (valueType == CfgPropertiesValueType.INTEGER) {
            boolean flag = org.apache.commons.lang3.StringUtils.isNumeric(inputContext);
            if (!flag) {
                throw new RuntimeException("输入的数据不是数值:" + inputContext);
            }
        }
        if (valueType == CfgPropertiesValueType.DECIMAL) {
            String[] splitStrs = inputContext.split("\\.");
            boolean flag = splitStrs.length == 2 && org.apache.commons.lang3.StringUtils.isNumeric(splitStrs[0])
                    && org.apache.commons.lang3.StringUtils.isNumeric(splitStrs[1]);
            if (!flag) {
                throw new RuntimeException("输入的数据不是小数:" + inputContext);
            }
        }
        if (valueType == CfgPropertiesValueType.STR_ARRAY) {
            // no op
        }
        if (valueType == CfgPropertiesValueType.INT_ARRAY) {
            // no op
        }
    }

    private void checkProjectAndModule(CfgPropertyVO cfgEnumVo) {
        CfgProjectVO projectVo = cfgProjectService.getById(cfgEnumVo.getProjectId());
        if (projectVo == null) {
            throw new RuntimeException("项目不存在，无法插入数据:" + cfgEnumVo.getProjectId());
        }

        CfgModuleVO moduleVo = cfgModuleService.getById(cfgEnumVo.getModuleId());
        if (moduleVo == null) {
            throw new RuntimeException("项目模块不存在，无法再插入:" + cfgEnumVo.getModuleId());
        }
    }

}
