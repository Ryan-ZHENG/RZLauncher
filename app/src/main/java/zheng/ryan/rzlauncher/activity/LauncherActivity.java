package zheng.ryan.rzlauncher.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import zheng.ryan.rzlauncher.R;
import zheng.ryan.rzlauncher.controller.LauncherController;
import zheng.ryan.rzlauncher.entity.AppInfo;
import zheng.ryan.rzlauncher.fragment.LauncherPageFragment;
import zheng.ryan.rzlauncher.listener.LauncherTouchListener;
import zheng.ryan.rzlauncher.util.LauncherLog;

public class LauncherActivity extends Activity {

    private static final String TAG = "LauncherActivity";
    public static final int LOADER_TASK_COMPLETED = 1235;
    private LauncherController mController;
    private ArrayList<LauncherPageFragment> mFragments;
    private ProgressBar mProgressbar;
    private static int mIndex = 0;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private LauncherTouchListener mListener;
    private LinearLayout mPageSurface;

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
    }

    private void initListener() {
        if(mListener == null)
            mListener = new LauncherTouchListener(this);
        mPageSurface.setLongClickable(true);
        mPageSurface.setOnTouchListener(mListener);
        updateListener();
    }

    private void updateListener() {
        mFragments.get(mIndex).setListener(mListener);
    }

    private void initView() {
        mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressbar.setVisibility(View.VISIBLE);
        mPageSurface = (LinearLayout) findViewById(R.id.section_workspace_page);
    }

    private void prepareFragments(ArrayList<AppInfo> appInfos) {
        int size = appInfos.size() / 16 + 1;
        LauncherLog.d(TAG, "page size = " + size);
        mFragments = new ArrayList<>();
        LauncherLog.d(TAG, mFragments.toString());
        for (int i = 0; i < size; i++) {
            LauncherPageFragment fragment = new LauncherPageFragment();
            if (i == size - 1) {
                fragment.setSubList(appInfos.subList(16 * i, appInfos.size()));
            } else {
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


    public void goToNextPage() {
        LauncherLog.d(TAG, "goToNextPage");
        mTransaction = mFragmentManager.beginTransaction();
        mIndex = mIndex == mFragments.size() - 1 ? 0 : mIndex + 1;
        mTransaction.replace(R.id.section_workspace_page, mFragments.get(mIndex));
        mTransaction.commit();
        updateListener();
        //TODO
    }

    public void goToPrevPage() {
        LauncherLog.d(TAG, "goToPrevPage");
        mTransaction = mFragmentManager.beginTransaction();
        mIndex = mIndex == 0 ? mFragments.size() - 1 : mIndex - 1;
        mTransaction.replace(R.id.section_workspace_page, mFragments.get(mIndex));
        mTransaction.commit();
        updateListener();
        //TODO
    }

    @Override
    public void onBackPressed() {
        //TODO - Implements this
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    /********************************
     * Inner Class Begin
     *********************************/
    class LauncherHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADER_TASK_COMPLETED:
                    LauncherLog.d(TAG, msg.obj.toString());
                    prepareFragments((ArrayList<AppInfo>) msg.obj);
                    break;
            }
        }
    }

    private abstract class LauncherMotionListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
        /**
         * 左右滑动的最短距离
         */
        private int distance = 100;
        /**
         * 左右滑动的最大速度
         */
        private int velocity = 200;

        /**
         * 向左滑的时候调用的方法，子类应该重写
         *
         * @return
         */
        abstract public boolean left();

        /**
         * 向右滑的时候调用的方法，子类应该重写
         *
         * @return
         */
        abstract public boolean right();

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            LauncherLog.d(TAG,"onFling");
            // 向左滑
            if (e1.getX() - e2.getX() > distance
                    && Math.abs(velocityX) > velocity) {
                left();
            }
            // 向右滑
            if (e2.getX() - e1.getX() > distance
                    && Math.abs(velocityX) > velocity) {
                right();
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            LauncherLog.d(TAG,"onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            LauncherLog.d(TAG,"onTouch");
            return true;
        }
    }
    /********************************Inner Class End*********************************/

}

