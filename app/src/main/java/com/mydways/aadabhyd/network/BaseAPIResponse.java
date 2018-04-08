package com.mydways.aadabhyd.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class BaseAPIResponse<T> {
    @SerializedName("msg")
    String message;
    @SerializedName("status")
    String statusCode;
    @SerializedName("result")
    public T responseResult;
}
