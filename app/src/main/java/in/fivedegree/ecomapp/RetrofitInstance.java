package in.fivedegree.ecomapp;

import static in.fivedegree.ecomapp.MainActivity.API;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static RetrofitInstance retrofitInstance;
    APIInterface apiInterface;

    RetrofitInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);
    }

    public static RetrofitInstance getInstance(){
        if (retrofitInstance == null){
            retrofitInstance = new RetrofitInstance();
        }
        return retrofitInstance;
    }
}
