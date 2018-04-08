package com.mydways.aadabhyd.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class AppUtils {
    public static final String BASE_URL = " https://aadabhyderabad.in";
    public static final String SERVICE_PROVIDER = "/services/";

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager != null ? manager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public enum AppErrorCode {
        NO_INTERNET(-1, "Please check your internet."),
        TIME_OUT(-2, "REQUEST TIME OUT. Please try after some time.");

        public int errorCode;
        public String errorMessage;

        AppErrorCode(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }
}
