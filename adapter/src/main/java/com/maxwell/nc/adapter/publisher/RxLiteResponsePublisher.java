package com.maxwell.nc.adapter.publisher;

import com.github.maxwell.nc.reactivelib.Publisher;
import com.github.maxwell.nc.reactivelib.Subscriber;

import retrofit2.Call;
import retrofit2.Response;


/**
 * 需要完整响应的生产者
 */
public class RxLiteResponsePublisher<T> extends Publisher<Response<T>> {

    private Call<T> call;

    public RxLiteResponsePublisher(Call<T> call) {
        this.call = call;
    }

    @Override
    protected void subscribeActual(Subscriber<Response<T>> subscriber) {
        subscriber.onSubscribe(new RxLiteResponseSubscription<>(subscriber, call));
    }


    private static final class RxLiteResponseSubscription<T> extends BaseRxLiteSubscription<Response<T>, T> {

        RxLiteResponseSubscription(Subscriber<Response<T>> actual, Call<T> call) {
            super(actual, call);
        }

        @Override
        protected Response<T> nextData() throws Exception {
            return call.execute();
        }
    }

}
