package com.transaction.serviceimpl;

import com.google.gson.Gson;
import com.transaction.configurations.AppConfigurationProperties;
import com.transaction.dto.recaptcha.ReCaptchaResponse;
import com.transaction.services.ReCaptchaService;
import com.transaction.utils.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Named
@Slf4j
@RequiredArgsConstructor
public class ReCaptchaServiceImpl implements ReCaptchaService {
    private final Gson gson;
    private final AppConfigurationProperties appConfigurationProperties;
    private ReCaptchaApi reCaptchaApi;


    @PostConstruct
    public void init(){
        String siteUrl = appConfigurationProperties.getGoogleBaseUrl();
        if(!siteUrl.endsWith("/")) siteUrl = siteUrl + "/";
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(siteUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        reCaptchaApi = retrofit.create(ReCaptchaApi.class);

    }




    @Override
    public Boolean verifyCaptchaToken(String token) {
        if(StringUtils.isBlank(token)){
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "No token provided");
        }

        Response<ReCaptchaResponse> response;
        try{
            Call<ReCaptchaResponse> retrofitCall = reCaptchaApi.verifyToken(appConfigurationProperties.getReCaptchaSecretKey(), token);
            response = retrofitCall.execute();
            if(response.isSuccessful() && response.body() != null){
                ReCaptchaResponse reCaptchaResponse = response.body();
                if(!reCaptchaResponse.getSuccess()){
                    String errorCodes =  Arrays.toString(reCaptchaResponse.getErrorCodes());
                    log.error("Error Codes :::::: {}", errorCodes);

                }

                return reCaptchaResponse.getSuccess();
            }

            else {
                ResponseBody errorBody = response.errorBody();
                String error = errorBody != null ? errorBody.string() : "An unknown error occurred when trying to validate re-captcha";
                log.info("Error message===================> {}", error);
                throw new ErrorResponse(response.code(), error);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new ErrorResponse(HttpStatus.EXPECTATION_FAILED, "An error occurred");
        }
    }



    private interface ReCaptchaApi {
        @FormUrlEncoded
        @POST("/recaptcha/api/siteverify")
        Call<ReCaptchaResponse> verifyToken(
                @Field("secret") String secret,
                @Field("response") String response
        );
    }
}
