package com.nzsoft.springcar.utils;

import android.content.Context;
import android.content.SharedPreferences;

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



}

