package com.maxwell.nc.rxliteadapter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * retrofit的响应结果转换器，仅用于sample
 */
public class StringConverter extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(type);
        if (rawType == String.class || rawType == Response.class) {
            return getConverter();
        }
        return null;
    }

    private Converter<ResponseBody, String> getConverter() {
        return new Converter<ResponseBody, String>() {
            @Override
            public String convert(ResponseBody value) throws IOException {
                return value.string();
            }
        };
    }

    public static Converter.Factory create() {
        return new StringConverter();
    }

}

