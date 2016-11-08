package practice.gaolei.textrecyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnMyItemClickListener {

    private RecyclerView recycler;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = (RecyclerView)findViewById(R.id.recycler);
        List<String> list=new ArrayList<>();
        for (int i = 0; i <50; i++) {
            list.add(String.format(Locale.CHINA,"第%03d条数据%s",i,i%2==0?"":""));
        }
        myAdapter = new MyAdapter(this,list);
        //上下文 流向  是否反转布局(数据从下面往上面走,eg 聊天的记录)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3,GridLayoutManager.VERTICAL ,false);
        //跨行或列 看方向
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==0){
                    return 3;// position==0 时 跨三列
                }
                return 1;//跨一列或行  默认为0,不垮
            }
        });

        //瀑布流   数据的大小要不同  参差不齐的
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);


        //动画的实现
        MyItemAnimator animator=new MyItemAnimator();
       // animator.setRemoveDuration(3000);
       // animator.setMoveDuration(3000);
       // animator.setAddDuration(3000);

        //设置是否支持改变动画
        animator.setSupportsChangeAnimations(true);
        animator.setChangeDuration(3000);

        recycler.setItemAnimator(animator);


        //设置item 的间隔
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            private Bitmap bitmap;

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                c.drawColor(Color.YELLOW);
            }
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);

                //在recyclerview上绘制图片
                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                c.drawBitmap(bitmap,300,300,null);

            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int position = parent.getChildAdapterPosition(view);

                outRect.set(0,5+position,0,5+position);// 间距越来越宽
            }
        });



        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(myAdapter);
        myAdapter.setOnMyItemClickListener(this);
    }

    @Override
    public void OnMyItemClick(RecyclerView parent, View view, int position, String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
     //   myAdapter.remove(position);
     //   myAdapter.add(position,"新增数据");

        myAdapter.change(position,"改变");

    }
}
