package com.maxwell.nc.adapter.publisher;

import com.github.maxwell.nc.reactivelib.Publisher;
import com.github.maxwell.nc.reactivelib.Subscriber;

import retrofit2.Call;

/**
 * 只需要响应内容的生产者
 */
public class RxLiteBodyPublisher<T> extends Publisher<T> {

    private Call<T> call;

    public RxLiteBodyPublisher(Call<T> call) {
        this.call = call;
    }

    @Override
    protected void subscribeActual(Subscriber<T> subscriber) {
        subscriber.onSubscribe(new RxLiteBodySubscription<>(subscriber, call));
    }

    private static final class RxLiteBodySubscription<T> extends BaseRxLiteSubscription<T, T> {

        RxLiteBodySubscription(Subscriber<T> actual, Call<T> call) {
            super(actual, call);
        }

        @Override
        protected T nextData() throws Exception {
            return call.execute().body();
        }

    }
}
