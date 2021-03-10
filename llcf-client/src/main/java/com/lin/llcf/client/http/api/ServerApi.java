package com.lin.llcf.client.http.api;

import com.lin.llcf.common.model.request.FetchAllPropertyRequest;
import com.lin.llcf.common.model.request.FetchDictRequest;
import com.lin.llcf.common.model.request.FetchPropertyRequest;
import com.lin.llcf.common.model.response.FetchAllPropertyResponse;
import com.lin.llcf.common.model.response.FetchDictResponse;
import com.lin.llcf.common.model.response.FetchOnePropertyResponse;

public interface ServerApi {

    FetchDictResponse fetchDict(FetchDictRequest request);

    FetchOnePropertyResponse fetchProperty(FetchPropertyRequest request);

    FetchAllPropertyResponse fetchAllProperty(String url, FetchAllPropertyRequest request);

}
