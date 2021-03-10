package com.lin.llcf.client.autoconfig.postprocessor;

import com.google.common.collect.Maps;
import com.lin.llcf.client.http.api.ServerApi;
import com.lin.llcf.client.http.api.ServerApiImpl;
import com.lin.llcf.common.constants.RequestPathConstants;
import com.lin.llcf.common.model.dtos.PropertyDTO;
import com.lin.llcf.common.model.request.FetchAllPropertyRequest;
import com.lin.llcf.common.model.response.FetchAllPropertyResponse;
import com.lin.llcf.common.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.List;
import java.util.Map;

public class LlcfPropertyInitPostProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "llcf_load_property_source";

    private static final String PROPERTY_SOURCE_APP_KEY = "llcf.app.key";

    private static final String PROPERTY_SOURCE_APP_ENV = "llcf.env";


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String appKey = environment.getProperty(PROPERTY_SOURCE_APP_KEY);
        String env = environment.getProperty(PROPERTY_SOURCE_APP_ENV);
        String apiServerHost = HttpUtils.getServerUrl(env);
        String url = apiServerHost + RequestPathConstants.FETCH_PROPERTY_ALL;

        if (StringUtils.isBlank(appKey)) {
            throw new RuntimeException("llcf配置的 llcf.app.key 不能为空");
        }

        ServerApi serverApi = new ServerApiImpl();
        FetchAllPropertyRequest request = new FetchAllPropertyRequest(appKey);
        FetchAllPropertyResponse response = serverApi.fetchAllProperty(url, request);
        List<PropertyDTO> propertyList = response.getPropertyList();

        Map<String, Object> sourceMap = Maps.newHashMap();
        for (PropertyDTO dto : propertyList) {
            sourceMap.put(dto.getKey(), dto.getValue());
        }
        LlcfLoadPropertySource propertySource = new LlcfLoadPropertySource(PROPERTY_SOURCE_NAME, sourceMap);
        environment.getPropertySources().addLast(propertySource);
    }

    private static class LlcfLoadPropertySource extends MapPropertySource {

        public LlcfLoadPropertySource(String name, Map<String, Object> source) {
            super(name, source);
        }

        @Override
        public Object getProperty(String name) {
            Object retObj = super.getProperty(name);
            if (retObj != null) {
                logger.info("正在查找配置数据");
            }
            return retObj;
        }
    }

}
