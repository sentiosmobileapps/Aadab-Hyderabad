package com.mydways.aadabhyd.network;

import com.mydways.aadabhyd.utilities.AppUtils;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class AadabHydException extends Exception {
    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    private String errorMessage;
    private int errorCode;

    public AadabHydException(int errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public AadabHydException(int errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = "Please check Internet";
    }

    public AadabHydException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AadabHydException(AppUtils.AppErrorCode appErrorCode) {
        this.errorCode = appErrorCode.errorCode;
        this.errorMessage = appErrorCode.errorMessage;
    }
}
