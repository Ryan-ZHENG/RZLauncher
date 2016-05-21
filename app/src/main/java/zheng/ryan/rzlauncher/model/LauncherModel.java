package zheng.ryan.rzlauncher.model;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import zheng.ryan.rzlauncher.activity.LauncherActivity;
import zheng.ryan.rzlauncher.entity.AppInfo;
import zheng.ryan.rzlauncher.util.LauncherLog;

/**
 * Created by Ryan_ZHENG on 16-5-21.
 */
public class LauncherModel extends BroadcastReceiver{

    private static final String TAG = "LauncherModel";
    private static final int LOADER_TASK_MODE_INIT = 1234;
    private static LauncherModel ourInstance = new LauncherModel();
    private LauncherActivity mContext;
    private AsyncTask<Integer, Integer, Integer> mLoaderTask;
    private ArrayList<AppInfo> mAppInfos;
    public static LauncherModel getInstance() {
        return ourInstance;
    }

    private LauncherModel() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public void init(LauncherActivity context) {
        this.mContext = context;
        if(mLoaderTask==null){
            mLoaderTask = new LauncherLoaderTask();
        }
        mLoaderTask.execute(LOADER_TASK_MODE_INIT);
    }

    /********************************Inner Class Begin*********************************/
    class LauncherLoaderTask extends AsyncTask<Integer,Integer,Integer>{

        private int mTaskMode;

        @Override
        protected Integer doInBackground(Integer... params) {
            mTaskMode = params[0];
            switch (mTaskMode){
                case LOADER_TASK_MODE_INIT:
                    loadAppInfoList();
                    Collections.sort(mAppInfos);
                    notifyAppInfosChanged();
                    break;
            }
            return null;
        }

        private void notifyAppInfosChanged() {
            Message message = new Message();
            message.what = LauncherActivity.LOADER_TASK_COMPLETED;
            message.obj = mAppInfos;
            mContext.getHandler().sendMessage(message);
            LauncherLog.d(TAG, mAppInfos.toString());
        }

        private void loadAppInfoList() {
            PackageManager packageManager = mContext.getPackageManager();
            List<ResolveInfo> resolvedList = packageManager.queryIntentActivities(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), packageManager.GET_RESOLVED_FILTER);
            if(mAppInfos == null){
                mAppInfos = new ArrayList<>(resolvedList.size());
            }
            for(ResolveInfo info : resolvedList){
                ComponentInfo componentInfo = info.activityInfo;
                ComponentName componentName = new ComponentName(componentInfo.packageName, componentInfo.name);
                AppInfo appInfo = new AppInfo();
                appInfo.setVisible(true);
                appInfo.setComponentName(componentName);
                appInfo.setAppTitle(componentInfo.loadLabel(packageManager).toString());
                appInfo.setAppLogo(componentInfo.loadIcon(packageManager));
                mAppInfos.add(appInfo);
            }
        }
    }
    /********************************Inner Class End*********************************/
}
