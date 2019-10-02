package com.nzsoft.springcar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;

public class Utils {

    public static void savePreferences (Context context, Long id){
        SharedPreferences preferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("userId", id);
        editor.commit();
    }

    public static Long loadPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);

        Long userId = preferences.getLong("userId", 0);

        return userId;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}

