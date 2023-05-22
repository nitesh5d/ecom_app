package in.fivedegree.ecomapp;

import retrofit2.http.Url;

public class ProductModel {

    String id;
    String title;
    String price;
    String category;
    String description;
    String image;
    RatingModel rating;

    public ProductModel(String id, String title, String price, String category, String description, String image, RatingModel rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String image) {
        this.image = image;
    }

    public RatingModel getRating() {
        return rating;
    }

    public void setRating(RatingModel rating) {
        this.rating = rating;
    }
}
