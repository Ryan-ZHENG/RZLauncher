package zheng.ryan.rzlauncher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ryan ZHENG on 16-5-21.
 */
public class LauncherGridView extends View {

    public LauncherGridView(Context context) {
        super(context);
    }

    public LauncherGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LauncherGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
