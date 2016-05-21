package zheng.ryan.rzlauncher.util;

import android.util.Log;

/**
 * Created by Ryan_ZHENG on 16-5-21.
 */
public class LauncherLog {

    private static final boolean ISDEBUG = true;
    private static final String DEFAULT_TAG = "RZLauncher";
    public static void d(String tag, String text){
        if(ISDEBUG){
            Log.d(DEFAULT_TAG + tag,text);
        }
    }
}
