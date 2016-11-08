package practice.gaolei.textrecyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gaoleideapple on 16/10/10.
 */

public class MyRecycleSpaceItemDecoration extends RecyclerView.ItemDecoration{


    int space;
    public MyRecycleSpaceItemDecoration(int space) {
       this.space=space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom=space;
        outRect.top=space;
        outRect.left=space;
        outRect.right=space;
    }
}
