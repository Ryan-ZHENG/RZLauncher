package zheng.ryan.rzlauncher.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

import zheng.ryan.rzlauncher.R;
import zheng.ryan.rzlauncher.adapter.GridAdapter;
import zheng.ryan.rzlauncher.entity.AppInfo;

/**
 * Created by Ryan ZHENG on 16-5-21.
 */
public class LauncherPageFragment extends Fragment{

    private List<AppInfo> mSubList;

    public LauncherPageFragment() {
    }

    public void setSubList(List<AppInfo> mSubList) {
        this.mSubList = mSubList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, null);
        GridView gridView = (GridView) view.findViewById(R.id.section_workspace_page_grid);
        gridView.setNumColumns(4);
        GridAdapter adapter = new GridAdapter(getActivity(),mSubList);
        gridView.setAdapter(adapter);
        return view;
    }

}
