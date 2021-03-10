package com.lin.llcf.server.service;

import com.google.common.collect.Maps;
import com.lin.llcf.common.model.dtos.DictDTO;
import com.lin.llcf.server.dao.CfgDictDao;
import com.lin.llcf.server.dao.CfgDictDefinitionDao;
import com.lin.llcf.server.domain.dos.CfgDictDO;
import com.lin.llcf.server.domain.dos.CfgDictDefinitionDO;
import com.lin.llcf.server.domain.vos.CfgDictVO;
import com.lin.llcf.server.domain.vos.CfgModuleVO;
import com.lin.llcf.server.domain.vos.CfgProjectVO;
import com.lin.llcf.server.event.EventPublisher;
import com.lin.llcf.server.event.dictevent.DictUpdateEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;
import com.lin.llcf.common.model.request.*;
import com.lin.llcf.common.model.response.*;

@Service
public class CfgDictService {

    @Autowired
    private CfgDictDao cfgDictDao;
    @Autowired
    private CfgDictDefinitionDao cfgDictDefinitionDao;

    @Autowired
    private CfgProjectService cfgProjectService;
    @Autowired
    private CfgModuleService cfgModuleService;
    @Autowired
    private EventPublisher eventPublisher;

    public List<DictDTO> getDictByDefinition(Long projectId, Long moduleId, String DictKey) {
        CfgDictDefinitionDO definition = cfgDictDefinitionDao.getByProjectIdAndModuleIdAndDictKey(projectId, moduleId, DictKey);
        if (definition == null) {
            return Collections.emptyList();
        }
        List<CfgDictDO> DictList = cfgDictDao.getByDefinitionId(definition.getId());
        List<DictDTO> voList = new ArrayList<>(DictList.size());

        for (CfgDictDO DictVo : DictList) {
            DictDTO vo = new DictDTO();
            vo.setId(DictVo.getDictId());
            vo.setCode(DictVo.getDictCode());
            vo.setDesc(DictVo.getDictDesc());
            voList.add(vo);
        }

        return voList;
    }

    public Map<String, Map<String, String>> fetchDictMap(Long projectId, Long moduleId) {
        List<CfgDictDefinitionDO> definition = cfgDictDefinitionDao.getByProjectIdAndModuleId(projectId, moduleId);
        if (CollectionUtils.isEmpty(definition)) {
            return Collections.emptyMap();
        }
        Map<Long, CfgDictDefinitionDO> idGroupMap = definition.stream().collect(Collectors.toMap(CfgDictDefinitionDO::getId, o -> o));
        List<CfgDictDO> DictList = cfgDictDao.getByDefinitionIds(new ArrayList<>(idGroupMap.keySet()));
        Map<Long, List<CfgDictDO>> definitionIdMap = DictList.stream().collect(Collectors.groupingBy(CfgDictDO::getDefinitionId));

        Map<String, Map<String, String>> resultMap = Maps.newHashMap();
        definitionIdMap.forEach((definitionId, groupedDictList) -> {
            CfgDictDefinitionDO theDefinition = idGroupMap.get(definitionId);
            if (theDefinition == null) {
                return;
            }
            Map<String, String> DictMap = Maps.newHashMap();
            groupedDictList.forEach(DictItem -> DictMap.put(DictItem.getDictId().toString(), DictItem.getDictDesc()));
            resultMap.put(theDefinition.getDictKey(), DictMap);
        });
        return resultMap;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void save(CfgDictVO cfgDictVo) {
        checkProjectAndModule(cfgDictVo);
        // 锁住字典数据
        CfgDictDefinitionDO cfgDictDefinition = cfgDictDefinitionDao.getAndLockByPidAndMid(
                cfgDictVo.getProjectId(), cfgDictVo.getModuleId(), cfgDictVo.getDictKey());
        if (cfgDictDefinition == null) {
            Long definitionId = saveDictDefinition(cfgDictVo);
            saveOrUpdateDictItem(cfgDictVo, definitionId);
        }else {
            saveOrUpdateDictItem(cfgDictVo, cfgDictDefinition.getId());
        }
        eventPublisher.pushAfterTransactional(new DictUpdateEvent(cfgDictVo));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void update(CfgDictVO cfgDictVo) {
        checkProjectAndModule(cfgDictVo);
        // 锁住字典数据
        CfgDictDefinitionDO cfgDictDefinition = cfgDictDefinitionDao.getAndLockByPidAndMid(
                cfgDictVo.getProjectId(), cfgDictVo.getModuleId(), cfgDictVo.getDictKey());
        if (cfgDictDefinition == null) {
            throw new RuntimeException("此枚举数据不存在，无法更新数据:" + cfgDictVo.getProjectId());
        }

        boolean flag = false;
        if (!Objects.equals(cfgDictDefinition.getDictKey(), cfgDictVo.getDictKey())) {
            cfgDictDefinition.setDictKey(cfgDictVo.getDictKey());
            flag = true;
        }
        if (!Objects.equals(cfgDictDefinition.getDictName(), cfgDictVo.getDictName())) {
            cfgDictDefinition.setDictName(cfgDictVo.getDictName());
            flag = true;
        }
        if (!Objects.equals(cfgDictDefinition.getDictDesc(), cfgDictVo.getDictDesc())) {
            if (StringUtils.hasText(cfgDictVo.getDictDesc())) {
                cfgDictDefinition.setDictDesc(cfgDictVo.getDictDesc());
            }else {
                cfgDictDefinition.setDictDesc("");
            }
            flag = true;
        }

        if (flag) {
            cfgDictDefinitionDao.updateById(cfgDictDefinition);
        }
        saveOrUpdateDictItem(cfgDictVo, cfgDictDefinition.getId());

        eventPublisher.pushAfterTransactional(new DictUpdateEvent(cfgDictVo));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void remove(CfgDictVO cfgDictVo) {
        checkProjectAndModule(cfgDictVo);
        // 锁住字典数据
        CfgDictDefinitionDO cfgDictDefinition = cfgDictDefinitionDao.getAndLockByPidAndMid(
                cfgDictVo.getProjectId(), cfgDictVo.getModuleId(), cfgDictVo.getDictKey());
        if (cfgDictDefinition == null) {
            throw new RuntimeException("此枚举数据不存在，无法删除数据:" + cfgDictVo.getProjectId());
        }
        List<CfgDictDO> existDictDoList = cfgDictDao.getByDefinitionId(cfgDictDefinition.getId());
        cfgDictDao.deleteAll(existDictDoList);
        cfgDictDefinitionDao.delete(cfgDictDefinition);
        eventPublisher.pushAfterTransactional(new DictUpdateEvent(cfgDictVo));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void removeItems(CfgDictVO cfgDictVo) {
        if (CollectionUtils.isEmpty(cfgDictVo.getItemList())) {
            return;
        }
        checkProjectAndModule(cfgDictVo);
        // 锁住字典数据
        CfgDictDefinitionDO cfgDictDefinition = cfgDictDefinitionDao.getAndLockByPidAndMid(
                cfgDictVo.getProjectId(), cfgDictVo.getModuleId(), cfgDictVo.getDictKey());
        if (cfgDictDefinition == null) {
            throw new RuntimeException("此字典数据不存在，无法删除数据:" + cfgDictVo.getProjectId());
        }
        Set<Integer> DictIds = cfgDictVo.getItemList().stream()
                .map(CfgDictVO.CfgDictItemVO::getDictId).collect(Collectors.toSet());

        List<CfgDictDO> removeList = cfgDictDao.getByDefinitionId(cfgDictDefinition.getId());
        removeList.removeIf(o -> !DictIds.contains(o.getDictId()));
        if (!CollectionUtils.isEmpty(removeList)) {
            cfgDictDao.deleteAll(removeList);
        }

        eventPublisher.pushAfterTransactional(new DictUpdateEvent(cfgDictVo));
    }

    private Long saveDictDefinition(CfgDictVO cfgDictVo) {
        CfgDictDefinitionDO definition = new CfgDictDefinitionDO();
        BeanUtils.copyProperties(cfgDictVo, definition);
        if (!StringUtils.hasText(definition.getDictDesc())) {
            definition.setDictDesc("");
        }
        definition.setCreateTime(new Date());
        definition.setUpdateTime(new Date());
        cfgDictDefinitionDao.save(definition);
        return definition.getId();
    }

    private void saveOrUpdateDictItem(CfgDictVO cfgDictVo, Long definitionId) {
        List<CfgDictVO.CfgDictItemVO> itemList = cfgDictVo.getItemList();
        if (CollectionUtils.isEmpty(itemList)) {
            return;
        }

        List<CfgDictDO> DictList = new ArrayList<>(itemList.size());
        for (CfgDictVO.CfgDictItemVO itemVo : itemList) {
            CfgDictDO DictDo = new CfgDictDO();
            BeanUtils.copyProperties(itemVo, DictDo);
            DictDo.setDefinitionId(definitionId);
            DictDo.setCreateTime(new Date());
            DictDo.setUpdateTime(new Date());
            DictList.add(DictDo);
        }

        List<CfgDictDO> existDictDoList = cfgDictDao.getByDefinitionId(definitionId);
        for (CfgDictDO DictDo : DictList) {
            CfgDictDO existDo = existDictDoList.stream()
                    .filter(o -> Objects.equals(o.getDictId(), DictDo.getDictId()))
                    .findFirst().orElse(null);
            if (existDo == null) {
                cfgDictDao.save(DictDo);
            }else {
                DictDo.setId(existDo.getId());
                cfgDictDao.updateById(DictDo);
            }
        }
    }

    private void checkProjectAndModule(CfgDictVO cfgDictVo) {
        CfgProjectVO projectVo = cfgProjectService.getById(cfgDictVo.getProjectId());
        if (projectVo == null) {
            throw new RuntimeException("项目不存在，无法插入数据:" + cfgDictVo.getProjectId());
        }

        CfgModuleVO moduleVo = cfgModuleService.getById(cfgDictVo.getModuleId());
        if (moduleVo == null) {
            throw new RuntimeException("项目模块不存在，无法再插入:" + cfgDictVo.getModuleId());
        }
    }

}
