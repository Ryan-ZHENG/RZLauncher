package zheng.ryan.rzlauncher.entity;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ryan_ZHENG on 16-5-21.
 */
public class AppViewHolder {
    public ImageView app_logo;
    public TextView app_title;

    public AppViewHolder(ImageView app_logo, TextView app_title) {
        this.app_logo = app_logo;
        this.app_title = app_title;
    }
}
