package com.maxwell.nc.adapter;

import com.github.maxwell.nc.reactivelib.Publisher;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * RxLite请求适配器工厂
 */
public class RxLiteCallAdapterFactory extends CallAdapter.Factory {

    public static RxLiteCallAdapterFactory create() {
        return new RxLiteCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);

        if (returnType instanceof ParameterizedType) {
            Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);

            if (rawType == Publisher.class) {//处理Publisher<T>

                if (getRawType(responseType) == Response.class) {//处理Publisher<Response<T>>
                    return new RxLiteCallAdapter(responseType, false);
                }

                return new RxLiteCallAdapter(responseType, true);
            }

        }

        return null;
    }

}
