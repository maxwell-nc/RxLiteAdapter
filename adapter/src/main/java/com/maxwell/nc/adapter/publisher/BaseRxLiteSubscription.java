package com.maxwell.nc.adapter.publisher;

import com.github.maxwell.nc.reactivelib.Subscriber;
import com.github.maxwell.nc.reactivelib.subscription.FlowSubscription;

import retrofit2.Call;

/**
 * Subscription基类
 */
abstract class BaseRxLiteSubscription<T, R> extends FlowSubscription {

    private final Subscriber<T> actual;
    final Call<R> call;

    BaseRxLiteSubscription(Subscriber<T> actual, Call<R> call) {
        this.actual = actual;
        this.call = call;
    }

    protected abstract T nextData() throws Exception;

    @Override
    public void request(long count) {
        try {
            if (!cancelled) {//仅仅请求一次
                actual.onNext(nextData());
            }
        } catch (Exception e) {
            cancel();
            actual.onError(e);
            return;
        }

        if (!cancelled) {
            actual.onComplete();
        }
    }

}