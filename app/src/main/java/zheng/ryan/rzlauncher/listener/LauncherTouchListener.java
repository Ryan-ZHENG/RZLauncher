package zheng.ryan.rzlauncher.listener;

import android.view.MotionEvent;
import android.view.View;

import zheng.ryan.rzlauncher.activity.LauncherActivity;
import zheng.ryan.rzlauncher.util.LauncherLog;

/**
 * Created by Ryan ZHENG on 16-5-24.
 */
public class LauncherTouchListener implements View.OnTouchListener{

    private static final String TAG = "LauncherTouchListener";
    private final LauncherActivity mContext;
    public boolean mIsMotionFinished = false;
    public static final float MOVE_THRESHOLD = 100;
    private float mOriginX = 0;
    private int mAction = -1;
    private long mStartTime;

    private static final int ACTION_MODE_GO_TO_PREV_PAGE = 12345;
    private static final int ACTION_MODE_GO_TO_NEXT_PAGE = 12346;

    public LauncherTouchListener(LauncherActivity context) {
        mContext = context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        LauncherLog.d(TAG,"EVENT = " + event.getAction());
        LauncherLog.d(TAG,"X = " + event.getX());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mOriginX = event.getX();
                mStartTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                if(currentX - mOriginX > MOVE_THRESHOLD){
                    mAction = ACTION_MODE_GO_TO_PREV_PAGE;
                    mIsMotionFinished = true;
                }else if(currentX - mOriginX < -MOVE_THRESHOLD){
                    mAction = ACTION_MODE_GO_TO_NEXT_PAGE;
                    mIsMotionFinished = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mIsMotionFinished) {
                    switch (mAction) {
                        case ACTION_MODE_GO_TO_NEXT_PAGE:
                            mContext.goToNextPage();
                            break;
                        case ACTION_MODE_GO_TO_PREV_PAGE:
                            mContext.goToPrevPage();
                            break;
                        default:
                            return false;
                    }
                    mIsMotionFinished = false;
                    mOriginX = 0;
                    return true;
                }else if(System.currentTimeMillis() - mStartTime > 500){
                    //TODO - LongPress here
                    return false;
                }else{
                    //SingleClick
                    return false;
                }
        }
        return false;

//        if(!mIsTouchDown){
//            if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                mAction = -1;
//                mStartTime = System.currentTimeMillis();
//                mIsMotionFinished = false;
//                mIsTouchDown = true;
//                mOriginX = event.getX();
//                return true;
//            }
//        }else{
//            switch (event.getAction()){
//                case MotionEvent.ACTION_MOVE:
//                    float currentX = event.getX();
//                    if(currentX - mOriginX > MOVE_THRESHOLD){
//                        mAction = ACTION_MODE_GO_TO_PREV_PAGE;
//                        mIsMotionFinished = true;
//                        return true;
//                    }else if(currentX - mOriginX < -MOVE_THRESHOLD){
//                        mAction = ACTION_MODE_GO_TO_NEXT_PAGE;
//                        mIsMotionFinished = true;
//                        return true;
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//                    if(!mIsMotionFinished || System.currentTimeMillis() - mStartTime < 200){
//                        mOriginX = event.getX();
//                        return false;
//                    }
//                    if(mIsMotionFinished){
//                        switch (mAction){
//                            case ACTION_MODE_GO_TO_NEXT_PAGE:
//                                mContext.goToNextPage();
//                                break;
//                            case ACTION_MODE_GO_TO_PREV_PAGE:
//                                mContext.goToPrevPage();
//                                break;
//                            default:
//                                return false;
//                        }
//                        return true;
//                    }
//                    mOriginX = event.getX();
//                    return false;
//
//            }
//        }
//        return false;

        /*
        if(!mIsMotionFinished){
            if(!mIsTouchDown && event.getAction() == MotionEvent.ACTION_DOWN) {
                mIsTouchDown = true;
                mOriginX = event.getX();
            }else if(mIsTouchDown && event.getAction() == MotionEvent.ACTION_MOVE){
                float currentX = event.getX();
                if(currentX - mOriginX > MOVE_THRESHOLD){
                    mAction = ACTION_MODE_GO_TO_PREV_PAGE;
                    mIsMotionFinished = true;
                    return true;
                }else if(currentX - mOriginX < -MOVE_THRESHOLD){
                    mAction = ACTION_MODE_GO_TO_NEXT_PAGE;
                    mIsMotionFinished = true;
                    return true;
                }
            }
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(!mIsMotionFinished) {
                mIsTouchDown = false;
                return false;
            }else{
                switch (mAction){
                    case ACTION_MODE_GO_TO_NEXT_PAGE:
                        mContext.goToNextPage();
                        mAction = -1;
                        break;
                    case ACTION_MODE_GO_TO_PREV_PAGE:
                        mContext.goToPrevPage();
                        mAction = -1;
                        break;
                    case -1:
                        return false;
                    default:
                        return false;
                }
                mIsTouchDown = false;
                mIsMotionFinished = false;
            }
        }
        return true;
*/
    }
}
