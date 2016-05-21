package zheng.ryan.rzlauncher.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zheng.ryan.rzlauncher.R;
import zheng.ryan.rzlauncher.entity.AppInfo;
import zheng.ryan.rzlauncher.entity.AppViewHolder;

/**
 * Created by Ryan_ZHENG on 16-5-16.
 */
public class GridAdapter extends BaseAdapter{

    public static GridAdapter instance;
    private Context context;
    private List<AppInfo> mAppInfos;
    public GridAdapter(Context context, List<AppInfo> appInfos) {
        this.context = context;
        this.mAppInfos = appInfos;
    }

    @Override
    public int getCount() {
        return mAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppInfo appInfo = mAppInfos.get(position);
        AppViewHolder viewHolder= null;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.workspace_item, null);
            ImageView appLogo = (ImageView) convertView.findViewById(R.id.app_logo);
            TextView appTitle = (TextView) convertView.findViewById(R.id.app_title);
            viewHolder = new AppViewHolder(appLogo, appTitle);
            convertView.setTag(viewHolder);
        }
        viewHolder = (AppViewHolder) convertView.getTag();
        viewHolder.app_logo.setImageDrawable(appInfo.getAppLogo());
        viewHolder.app_title.setText(appInfo.getAppTitle());

        return convertView;
    }
}
