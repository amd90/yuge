package amd.nrk.yuge.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.zip.CheckedOutputStream;

/**
 * Created by Andres on 4/13/2017.
 */

public class Preferences {

    private static final String PREFERENCES = "preferences";
    private static final String YUGE_COUNTER = "yuge_counter";
    private static final String FORCE = "force";

    private Preferences(){}

    public static void setYugeCounter(Context context, int yugeCount){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(YUGE_COUNTER, yugeCount);
        editor.apply();
    }

    public static int getYugeCounter(Context context){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(YUGE_COUNTER, 0);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void setForce(Context context, boolean status){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(FORCE, status);
        editor.apply();
    }

    public   static boolean isForce(Context context){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(FORCE, true);
    }

}




