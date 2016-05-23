package zheng.ryan.rzlauncher.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import zheng.ryan.rzlauncher.activity.LauncherActivity;

/**
 * Created by Ryan_ZHENG on 16-5-21.
 */
public class LauncherModel extends BroadcastReceiver{

    private static final String TAG = "LauncherModel";

    private static LauncherModel mInstance = new LauncherModel();
    private static LauncherActivity mContext;
    private static LauncherLoader mLoaderTask;
    private LauncherModel() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public static LauncherModel init(LauncherActivity context) {

        if(mContext == null){
            mContext = context;
        }
        if(mLoaderTask==null){
            mLoaderTask = new LauncherLoader(mContext);
        }
        mLoaderTask.startLoader(LauncherLoader.LOADER_TASK_MODE_INIT);
        return mInstance;
    }
}
