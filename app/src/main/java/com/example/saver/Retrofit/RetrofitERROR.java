package com.example.saver.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class RetrofitERROR {

    public static ErrorUtils parseError(Response<?> response) {
        Converter<ResponseBody, ErrorUtils> converter = RetrofitBASE.retrofit
                .responseBodyConverter(ErrorUtils.class, new Annotation[0]);
        ErrorUtils errorUtils;

        try {
            errorUtils = converter.convert(Objects.requireNonNull(response.errorBody()));
        } catch (IOException e) {
            return new ErrorUtils();
        }

        return errorUtils;
    }

}
