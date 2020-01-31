package com.illicitintelligence.finalproject.util;

import android.util.Log;

public class Logger {

    public static void logIt(String message){
        Log.d(Constants.LOG_TAG, message);
    }
    public static void logError(String message){
        Log.e(Constants.ERROR_TAG, message);
    }
}
