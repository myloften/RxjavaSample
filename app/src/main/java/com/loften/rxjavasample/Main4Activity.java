package com.loften.rxjavasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        initDatas();
    }

    private void initDatas() {
        test1();
        test2();
    }

    private void test2() {
        Observable.create(new Observable.OnSubscribe<Integer>(){
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    for (int i = 0;i < 5; i++){
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Toast.makeText(Main4Activity.this, "完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e("num", integer+"");
            }
        });
    }

    private void test1() {
        Observable.just("On","Off","On","On")
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s!="Off";
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("DDDDD","结束观察。。。\n");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("DDDDDd","handle this----"+s);
                    }
                });
    }
}
