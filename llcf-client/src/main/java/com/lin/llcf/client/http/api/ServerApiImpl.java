package com.lin.llcf.client.http.api;

import com.lin.llcf.common.model.request.FetchAllPropertyRequest;
import com.lin.llcf.common.model.request.FetchDictRequest;
import com.lin.llcf.common.model.request.FetchPropertyRequest;
import com.lin.llcf.common.model.response.FetchAllPropertyResponse;
import com.lin.llcf.common.model.response.FetchDictResponse;
import com.lin.llcf.common.model.response.FetchOnePropertyResponse;
import com.lin.llcf.common.utils.HttpUtils;
import com.lin.llcf.common.utils.JsonUtils;

public class ServerApiImpl implements ServerApi{

    @Override
    public FetchDictResponse fetchDict(FetchDictRequest request) {
        return null;
    }

    @Override
    public FetchOnePropertyResponse fetchProperty(FetchPropertyRequest request) {
        return null;
    }

    @Override
    public FetchAllPropertyResponse fetchAllProperty(String url, FetchAllPropertyRequest request) {
        String json = JsonUtils.writeToString(request);
        if (json == null) {
            return null;
        }
        String respString = HttpUtils.post(url, json);
        if (respString == null) {
            return null;
        }
        return JsonUtils.read(respString, FetchAllPropertyResponse.class);
    }



}
