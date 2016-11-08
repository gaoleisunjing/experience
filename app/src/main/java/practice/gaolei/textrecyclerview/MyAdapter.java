package practice.gaolei.textrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gaoleideapple on 16/10/22.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<String> list;

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adapter, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //现有的数据绑定到adapter上  对ViewHolder的复用
        holder.text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        //adapter 一共有多少条数据
        return list.size();
    }


    @Override
    public void onClick(View v) {
        if (recyclerView != null && listener != null) {
            //取得位置
            int position = recyclerView.getChildAdapterPosition(v);
            listener.OnMyItemClick(recyclerView,v,position,list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_text);
        }
    }


    RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    private OnMyItemClickListener listener;

    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnMyItemClickListener {
        //view 被点击的view
        void OnMyItemClick(RecyclerView parent, View view, int position, String data);
    }

    public void remove(int position){
        list.remove(position);
     //   notifyDataSetChanged();
        notifyItemRemoved(position);//要用动画效果,必须使用recyclerview自己的提醒方式

    }

    public void add(int position,String data){
        list.add(position,data);
        notifyItemInserted(position);
    }
    public void change(int position,String data){
        list.remove(position);
        list.add(position,data);
        notifyItemChanged(position);
    }

    //多item布局时候写的方法   listview中有getitemType 和getitemCount  还必须getitemType<getitemCount
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
