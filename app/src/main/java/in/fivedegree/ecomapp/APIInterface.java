package in.fivedegree.ecomapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @GET("/products")
    Call<List<ProductModel>> getProducts();

    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
