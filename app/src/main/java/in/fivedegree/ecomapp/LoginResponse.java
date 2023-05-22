package in.fivedegree.ecomapp;

import android.provider.Telephony;

public class LoginResponse {

    String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
