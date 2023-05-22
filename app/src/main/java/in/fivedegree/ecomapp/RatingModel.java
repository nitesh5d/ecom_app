package in.fivedegree.ecomapp;

public class RatingModel {

    String rate;
    String count;

    public RatingModel(String rate, String count) {

        this.rate = rate;
        this.count = count;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateCount() {
        return count;
    }

    public void setRateCount(String rateCount) {
        this.count = count;
    }
}
