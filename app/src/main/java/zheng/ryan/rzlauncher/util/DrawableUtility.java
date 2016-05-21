package zheng.ryan.rzlauncher.util;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;


/**
 * Created by Ryan_ZHENG on 16-5-21.
 */
public class DrawableUtility {
    private DrawableUtility(){}
    public static Bitmap createCustomizedIcon(Context context, ComponentName componentName){
        String packageName = componentName.getPackageName();
        Drawable custIcon = queryFromCustList(context, packageName);
        if(custIcon==null){
        }
        return null;
    }

    private static Drawable queryFromCustList(Context context, String packageName) {
        //TODO - Implements customized icon
        return null;
    }

    public static Drawable cropWallPaper(Drawable drawable) {
        //TODO - Crop the wallpaper
        return drawable;
    }
}
