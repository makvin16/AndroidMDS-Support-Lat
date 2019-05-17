package com.zm.mds.mds_support.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by moska on 18.10.2017.
 */

public class InternetConnection {
    public static boolean checkConnection(Context context) {
        return ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
