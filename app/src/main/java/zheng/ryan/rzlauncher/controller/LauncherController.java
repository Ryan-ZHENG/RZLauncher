package zheng.ryan.rzlauncher.controller;

import zheng.ryan.rzlauncher.activity.LauncherActivity;
import zheng.ryan.rzlauncher.model.LauncherModel;

/**
 * Created by Ryan_ZHENG on 16-5-21.
 */
public class LauncherController {
    private static LauncherController ourInstance = new LauncherController();
    private LauncherActivity mContext;
    private LauncherModel mModel;

    public static LauncherController getInstance() {
        return ourInstance;
    }

    private LauncherController() {
    }

    public void prepare(LauncherActivity context) {
        this.mContext = context;
        mModel = LauncherModel.init(context);
    }
}
