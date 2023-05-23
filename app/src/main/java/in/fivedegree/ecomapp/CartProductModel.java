package in.fivedegree.ecomapp;

public class CartProductModel {

    String id;
    String title;
    String price;
    String image;
    String qty;

    public CartProductModel(String id, String title, String price, String image, String qty) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.qty = qty;
    }

    public CartProductModel() {

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


    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String image) {
        this.image = image;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
