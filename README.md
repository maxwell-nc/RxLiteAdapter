# RxLiteAdapter
我的[ReactiveLite库](https://github.com/maxwell-nc/ReactiveLite)的Retrofit的请求适配器，方便使用Retrofit + RxLite 方式开发。

A Retrofit CallAdapter with my ReactiveX Lite library

## 用法 Usage

```java
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://github.com/")
                .addCallAdapterFactory(RxLiteCallAdapterFactory.create())//设置Adapter工厂
                .build()

//创建请求
Publish<T> publisher = retrofit.create(XxxService.class).xxx();

//使用Rxjava方式
publisher.subscribeOn(Schedulers.parallel())//子线程请求
         .observeOn(Schedulers.mainThread())//主线程回调
         .subscribe(new FlowSubscriber<Response<String>>() {
             @Override
             public void onNext(Response<String> s) {
                 //请求成功的操作
             }

             @Override
             public void onError(Throwable throwable) {
                 super.onError(throwable);
                 //请求失败的操作
             }
         });

```

**更多用法请参考示例代码**