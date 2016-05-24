package zheng.ryan.rzlauncher.fragment;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import zheng.ryan.rzlauncher.R;
import zheng.ryan.rzlauncher.adapter.GridAdapter;
import zheng.ryan.rzlauncher.entity.AppInfo;
import zheng.ryan.rzlauncher.listener.LauncherTouchListener;

/**
 * Created by Ryan ZHENG on 16-5-21.
 */
public class LauncherPageFragment extends Fragment{

    private List<AppInfo> mSubList;


    private GridView mGridView;
    private LauncherTouchListener mListener;

    public LauncherPageFragment() {
    }

    public void setSubList(List<AppInfo> mSubList) {
        this.mSubList = mSubList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, null);
        mGridView = (GridView) view.findViewById(R.id.section_workspace_page_grid);
        mGridView.setNumColumns(4);
        GridAdapter adapter = new GridAdapter(getActivity(),mSubList);
        mGridView.setAdapter(adapter);
        mGridView.setOnTouchListener(mListener);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComponentName compName = mSubList.get(position).getComponentName();
                Intent intent = new Intent();
                intent.setComponent(compName);
                startActivity(intent);
            }
        });
        return view;
    }

    public void setListener(LauncherTouchListener listener) {
        this.mListener = listener;

    }
}
