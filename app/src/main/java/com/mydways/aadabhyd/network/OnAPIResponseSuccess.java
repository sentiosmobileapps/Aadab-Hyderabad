package com.mydways.aadabhyd.network;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class OnAPIResponseSuccess {

    public BaseAPIResponse response;
    public int apiId;

    OnAPIResponseSuccess(int apiId, BaseAPIResponse result) {
        response = result;
        this.apiId = apiId;
    }
}
