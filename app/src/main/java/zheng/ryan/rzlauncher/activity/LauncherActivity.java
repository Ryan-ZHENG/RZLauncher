package zheng.ryan.rzlauncher.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import zheng.ryan.rzlauncher.R;
import zheng.ryan.rzlauncher.controller.LauncherController;
import zheng.ryan.rzlauncher.entity.AppInfo;
import zheng.ryan.rzlauncher.fragment.LauncherPageFragment;
import zheng.ryan.rzlauncher.util.LauncherLog;

public class LauncherActivity extends Activity {

    private static final String TAG = "LauncherActivity";
    public static final int LOADER_TASK_COMPLETED = 1235;
    private LauncherController mController;
    private ArrayList<LauncherPageFragment> mFragments;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private ProgressBar mProgressbar;
    private LinearLayout mWorkspaceLayout;
    private static int mIndex = 0;

    public Handler getHandler() {
        return mHandler;
    }

    private LauncherHandler mHandler = new LauncherHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mController = LauncherController.getInstance();
        mController.prepare(this);
        initView();
        initListener();
    }

    private void initListener() {
        mWorkspaceLayout.setOnGenericMotionListener(new MotionListener());
    }

    private void initView() {
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressbar.setVisibility(View.VISIBLE);
        mWorkspaceLayout = (LinearLayout)findViewById(R.id.section_workspace_page);
    }

    private void prepareFragments(ArrayList<AppInfo> appInfos) {
        int size = appInfos.size() / 16 + 1;
        LauncherLog.d(TAG,"page size = " + size);
        mFragments = new ArrayList<>();
        LauncherLog.d(TAG, mFragments.toString());
        for(int i = 0 ; i < size; i ++){
            LauncherPageFragment fragment = new LauncherPageFragment();
            if(i == size - 1){
                fragment.setSubList(appInfos.subList(16 * i, appInfos.size()));
            }else{
                fragment.setSubList(appInfos.subList(16 * i, 16 * (i + 1)));
            }
            mFragments.add(fragment);
            LauncherLog.d(TAG, mFragments.toString());
        }
        LauncherLog.d(TAG, mFragments.toString());
        mFragmentManager = getFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.section_workspace_page, mFragments.get(mIndex));
        mTransaction.commit();
        initListener();
        mProgressbar.setVisibility(View.GONE);
    }


    private void goToNextPage() {
        LauncherLog.d(TAG,"goToNextPage");
        mIndex = mIndex == mFragments.size() -1 ? 0 : mIndex +1;
        mTransaction.replace(R.id.section_workspace_page, mFragments.get(mIndex));
        mTransaction.commit();
    }

    private void goToPrevPage() {
        LauncherLog.d(TAG,"goToPrevPage");
        mIndex = mIndex == 0 ? mFragments.size() - 1 : mIndex - 1;
        mTransaction.replace(R.id.section_workspace_page, mFragments.get(mIndex));
        mTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        //TODO - Implements this
    }

    /********************************Inner Class Begin*********************************/
    class LauncherHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOADER_TASK_COMPLETED:
                    LauncherLog.d(TAG, msg.obj.toString());
                    prepareFragments((ArrayList<AppInfo>)msg.obj);
                    break;
            }
        }
    }

    private class MotionListener implements View.OnGenericMotionListener {
        @Override
        public boolean onGenericMotion(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                //TODO - Implements move event
                return true;
            }
        }
    }
    /********************************Inner Class End*********************************/

}

