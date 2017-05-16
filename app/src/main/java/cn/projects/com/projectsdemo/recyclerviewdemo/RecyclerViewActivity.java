package cn.projects.com.projectsdemo.recyclerviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 2017/4/12.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    private ArrayList<String> mDates;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.id_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new MyRecyclerViewAdapter());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }


    private void initData() {
        mDates = new ArrayList();
        for(int i = 'A' ; i <= 'z'; i ++){
            mDates.add(""+(char)i);
        }
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_recycler_view_list,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDates.get(position));
        }

        @Override
        public int getItemCount() {
            return mDates.size();
        }
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_num);
        }
    }
}
