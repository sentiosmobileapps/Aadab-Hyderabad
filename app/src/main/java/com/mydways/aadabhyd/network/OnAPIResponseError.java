package com.mydways.aadabhyd.network;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class OnAPIResponseError {
    public AadabHydException exception;
    public int apiId;
    public OnAPIResponseError(int apiId,AadabHydException exception) {
        this.apiId = apiId;
        this.exception = exception;
    }
}
