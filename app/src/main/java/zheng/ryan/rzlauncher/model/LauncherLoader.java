package zheng.ryan.rzlauncher.model;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import zheng.ryan.rzlauncher.activity.LauncherActivity;
import zheng.ryan.rzlauncher.entity.AppInfo;
import zheng.ryan.rzlauncher.util.LauncherLog;

/**
 * Created by Ryan ZHENG on 16-5-23.
 */
public class LauncherLoader {

    private static final String TAG = "LauncherLoader";
    static final int LOADER_TASK_MODE_INIT = 1234;
    private final LauncherActivity mContext;
    private ArrayList<AppInfo> mAppInfos;
    private LinkedList<Integer> mTaskQueue;
    private LoaderThread[] mThreadPool;

    public LauncherLoader(LauncherActivity context) {
        mContext = context;
        mTaskQueue = new LinkedList<>();
        mThreadPool = new LoaderThread[4];
    }

    public void startLoader(int taskMode){
        mTaskQueue.addLast(taskMode);
        startThreadifPossible();
    }

    private void startThreadifPossible() {
        if(mTaskQueue.size()<=0){
            return;
        }
        int taskMode = mTaskQueue.removeFirst();
        int index = checkVacancy();
        if(index!=-1){
            mThreadPool[index].start(taskMode);
        }else{
            //TODO - Fix this later
        }
    }

    private int checkVacancy() {
        for(int i = 0 ; i < mThreadPool.length; i++){
            if(mThreadPool[i] == null){
                mThreadPool[i] = new LoaderThread();
                return i;
            }
            if(!mThreadPool[i].isRunning){
                return i;
            }
        }
        return -1;
    }
    /********************************Inner Class Begin*********************************/

    private class LoaderThread extends Thread{
        private boolean isRunning = false;
        private int mTask = -1;
        public LoaderThread() {
        }

        public void start(int task) {
            isRunning = true;
            mTask = task;
            start();
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

        @Override
        public void run() {
            if(mTask == -1){
                return;
            }
            switch (mTask){
                case LOADER_TASK_MODE_INIT:
                    loadAppInfoList();
                    Collections.sort(mAppInfos);
                    notifyAppInfosChanged();
                    break;
            }
            isRunning = false;
        }
    }
    /********************************Inner Class End*********************************/

}
