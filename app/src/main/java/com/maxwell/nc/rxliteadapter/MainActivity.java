package com.maxwell.nc.rxliteadapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.github.maxwell.nc.reactivelib.FlowSubscriber;
import com.github.maxwell.nc.reactivelib.Publisher;
import com.github.maxwell.nc.reactivelib.scheduler.Schedulers;
import com.maxwell.nc.adapter.RxLiteCallAdapterFactory;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends Activity {

    private TextView tvContent;

    private GitHubService service;

    interface GitHubService {

        /**
         * Publisher<T>直接获取响应内容信息
         */
        @GET("maxwell-nc")
        Publisher<String> onlyBody();

        /**
         * Publisher<Response<T>>类型支持获取响应头等信息
         */
        @GET("maxwell-nc")
        Publisher<Response<String>> fullResponse();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new Retrofit.Builder()
                .baseUrl("https://github.com/")
                .addConverterFactory(StringConverter.create())
                .addCallAdapterFactory(RxLiteCallAdapterFactory.create())
                .build()
                .create(GitHubService.class);

        tvContent = (TextView) findViewById(R.id.tv_content);
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());//添加支持滚动

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSample();
            }
        });
    }

    /**
     * 请求例子
     */
    private void requestSample() {
        service.fullResponse()
                .subscribeOn(Schedulers.parallel())
                .observeOn(Schedulers.mainThread())
                .subscribe(new FlowSubscriber<Response<String>>() {
                    @Override
                    public void onNext(Response<String> s) {
                        //请求成功的操作
                        tvContent.setText(String.format("%s %s", s.headers().toString(), s.body()));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        //请求失败的操作
                        tvContent.setText(String.format("load error! %s", throwable.getMessage()));
                    }
                });
    }

}
