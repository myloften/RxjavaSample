package com.loften.rxjavasample.util;

// (c)2016 Flipboard Inc, All Rights Reserved.

import com.loften.rxjavasample.model.Datas;
import com.loften.rxjavasample.model.GankBeauty;
import com.loften.rxjavasample.model.GankBeautyResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import rx.functions.Func1;

public class GankBeautyResultToItemsMapper implements Func1<GankBeautyResult, List<Datas>>{
    private static GankBeautyResultToItemsMapper INSTANCE = new GankBeautyResultToItemsMapper();

    private GankBeautyResultToItemsMapper() {
    }

    public static GankBeautyResultToItemsMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Datas> call(GankBeautyResult gankBeautyResult) {
        List<GankBeauty> gankBeauties = gankBeautyResult.beauties;
        List<Datas> datas = new ArrayList<>(gankBeauties.size());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        for(GankBeauty gankBeauty : gankBeauties){
            Datas data = new Datas();
            try{
                Date date = inputFormat.parse(gankBeauty.createdAt);
                data.description = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                data.description = "unknown date";
            }
            data.image_url = gankBeauty.url;
            datas.add(data);
        }
        return datas;
    }
}
