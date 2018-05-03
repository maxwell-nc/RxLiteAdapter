package com.maxwell.nc.adapter;

import com.maxwell.nc.adapter.publisher.RxLiteBodyPublisher;
import com.maxwell.nc.adapter.publisher.RxLiteResponsePublisher;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * RxLite请求适配器
 */
class RxLiteCallAdapter<R> implements CallAdapter<R, Object> {

    private Type responseType;

    /**
     * 是否只需要响应内容
     */
    private boolean onlyBody;

    RxLiteCallAdapter(Type responseType, boolean onlyBody) {
        this.responseType = responseType;
        this.onlyBody = onlyBody;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<R> call) {
        if (onlyBody) {
            return new RxLiteBodyPublisher<>(call);
        } else {
            return new RxLiteResponsePublisher<>(call);
        }
    }

}
