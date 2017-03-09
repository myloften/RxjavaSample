package com.loften.rxjavasample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loften.rxjavasample.adapter.Main2Adapter;
import com.loften.rxjavasample.model.ZhuangbiImage;
import com.loften.rxjavasample.network.Network;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Main2Activity extends BaseActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Main2Adapter myAdapter;
    Observer<List<ZhuangbiImage>> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
        search("装逼");//"可爱""110""在下""装逼"
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
    }

    private void initData(){
        myAdapter = new Main2Adapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(myAdapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);

        observer = new Observer<List<ZhuangbiImage>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(Main2Activity.this, "数据加载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNext(List<ZhuangbiImage> datases) {
                swipeRefreshLayout.setRefreshing(false);
                myAdapter.setDatas(datases);
            }
        };
    }

    private void search(String key){
        subscription = Network.getZhuangbiApi()
                .search(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
