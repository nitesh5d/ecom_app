package in.fivedegree.ecomapp;

public class OrdersModel {

    String id, userToken, productId, qty, orderStatus, payMode, transactionId;
    double cost;

    public OrdersModel(String id, String userToken, String productId, String qty, double cost, String orderStatus, String payMode, String transactionId) {
        this.id = id;
        this.userToken = userToken;
        this.productId = productId;
        this.qty = qty;
        this.cost = cost;
        this.orderStatus = orderStatus;
        this.payMode = payMode;
        this.transactionId = transactionId;
    }

    public OrdersModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
