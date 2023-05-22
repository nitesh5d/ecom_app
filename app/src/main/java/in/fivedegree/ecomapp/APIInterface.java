package in.fivedegree.ecomapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/products")
    Call<List<ProductModel>> getProducts();
}
