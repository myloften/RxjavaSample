package com.loften.rxjavasample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loften.rxjavasample.adapter.MyAdapter;
import com.loften.rxjavasample.model.Datas;
import com.loften.rxjavasample.model.ZhuangbiImage;
import com.loften.rxjavasample.network.Network;
import com.loften.rxjavasample.util.GankBeautyResultToItemsMapper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class Main3Activity extends BaseActivity implements View.OnClickListener {

    private int page = 1;

    private TextView pageTv;
    private AppCompatButton previousPageBt;
    private AppCompatButton nextPageBt;
    private RecyclerView gridRv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter myAdapter;
    private Observer<List<Datas>> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        initData();
        loadPage(page);
    }

    private void initView() {
        pageTv = (TextView) findViewById(R.id.pageTv);
        previousPageBt = (AppCompatButton) findViewById(R.id.previousPageBt);
        nextPageBt = (AppCompatButton) findViewById(R.id.nextPageBt);
        gridRv = (RecyclerView) findViewById(R.id.gridRv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        previousPageBt.setOnClickListener(this);
        nextPageBt.setOnClickListener(this);
    }

    private void initData(){
        myAdapter = new MyAdapter();
        gridRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        gridRv.setAdapter(myAdapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        observer = new Observer<List<Datas>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error:", e.getMessage());
//                swipeRefreshLayout.setRefreshing(false);
//                Toast.makeText(Main3Activity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<Datas> datases) {
                swipeRefreshLayout.setRefreshing(false);
                pageTv.setText("第"+page+"页");
                myAdapter.setDatas(datases);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previousPageBt:
                loadPage(--page);
                if(page == 1){
                    previousPageBt.setEnabled(false);
                }
                break;
            case R.id.nextPageBt:
                loadPage(++page);
                if(page == 2){
                    previousPageBt.setEnabled(true);
                }
                break;
        }
    }

    private void loadPage(int page){
        swipeRefreshLayout.setRefreshing(true);
        unsubscribe();
//        //map应用
//        subscription = Network.getGankApi()
//                .getBeauties(10,page)
//                .map(GankBeautyResultToItemsMapper.getInstance())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
        //zip应用
        subscription = Observable.zip(Network.getGankApi().getBeauties(10, page).map(GankBeautyResultToItemsMapper.getInstance()),
                Network.getZhuangbiApi().search("装逼"),
                new Func2<List<Datas>, List<ZhuangbiImage>, List<Datas>>() {
                    @Override
                    public List<Datas> call(List<Datas> datases, List<ZhuangbiImage> zhuangbiImages) {
                        List<Datas> items = new ArrayList<>();
                        for(int i = 0; i< datases.size()/2 && i < zhuangbiImages.size(); i++){
                            items.add(datases.get(i * 2));
                            items.add(datases.get(i * 2 + 1));
                            Datas zhuangbiItem = new Datas();
                            ZhuangbiImage zhuangbiImage = zhuangbiImages.get(i);
                            zhuangbiItem.description = zhuangbiImage.description;
                            zhuangbiItem.image_url = zhuangbiImage.image_url;
                            items.add(zhuangbiItem);
                        }
                        return items;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
