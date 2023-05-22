package in.fivedegree.ecomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        TextView tokenTv = findViewById(R.id.token);

        SharedPreferences sharedPreferences = getSharedPreferences("UserAuth", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        LinearLayout userDetails = findViewById(R.id.userDetailsCont);
        LinearLayout loginCont = findViewById(R.id.loginCont);
        if (token != null){
            tokenTv.setText(token);
            userDetails.setVisibility(View.VISIBLE);
            loginCont.setVisibility(View.GONE);
        }
        else {
            userDetails.setVisibility(View.GONE);
            loginCont.setVisibility(View.VISIBLE);
        }


        EditText userInp = findViewById(R.id.userInp);
        EditText pwInp = findViewById(R.id.pwInp);
        Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(View ->{
            String username = userInp.getText().toString();
            String pw =  pwInp.getText().toString();
            if (username.equals("") || pw.equals("")) {
                Toast.makeText(getApplicationContext(), "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
            }
            else {
                RetrofitInstance.getInstance().getApiInterface().login(new LoginRequest(username, pw)).enqueue(new Callback<LoginResponse>(){
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        String token = loginResponse.getToken();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserAuth", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.apply();
                        Toast.makeText(UserActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(UserActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}