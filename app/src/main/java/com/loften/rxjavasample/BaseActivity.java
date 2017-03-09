package com.loften.rxjavasample;

import android.support.v7.app.AppCompatActivity;

import rx.Subscription;

/**
 * Created by asus on 2016/9/22.
 */

public class BaseActivity extends AppCompatActivity {
    protected Subscription subscription;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
