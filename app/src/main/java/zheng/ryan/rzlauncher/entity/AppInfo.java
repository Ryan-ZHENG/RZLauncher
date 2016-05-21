package zheng.ryan.rzlauncher.entity;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

/**
 * Created by Ryan_ZHENG on 16-5-21.
 */
public class AppInfo implements Comparable{
    private ComponentName componentName;
    private boolean isVisible;
    private Drawable appLogo;
    private String appTitle;

    public AppInfo() {
    }

    public AppInfo(ComponentName componentName, boolean isVisible, Drawable appLogo, String appTitle) {
        this.componentName = componentName;
        this.isVisible = isVisible;
        this.appLogo = appLogo;
        this.appTitle = appTitle;
    }

    public ComponentName getComponentName() {
        return componentName;
    }

    public void setComponentName(ComponentName componentName) {
        this.componentName = componentName;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Drawable getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(Drawable appLogo) {
        this.appLogo = appLogo;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    @Override
    public int compareTo(Object another) {
        if(another == null) return -1;
        if(!(another instanceof AppInfo)) return -1;
        return this.getComponentName().compareTo(((AppInfo)another).getComponentName());
    }
}
