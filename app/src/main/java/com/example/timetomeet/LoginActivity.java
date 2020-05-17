package com.example.timetomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button regBtn, loginBtn;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    EditText username, password;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        result = findViewById(R.id.result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dry-everglades-97577.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        username = findViewById(R.id.login);
        password = findViewById(R.id.password);
        regBtn = findViewById(R.id.registration_button);
        loginBtn = findViewById(R.id.login_button);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        Call<Map<String, String>> call = jsonPlaceHolderApi.login(username.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                result.setText(response.body().get("result"));
                if (response.body().get("result").equals("success")) {
                    Profile.setUsername(username.getText().toString());
                    enter();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }

    private void enter() {
        startActivity(new Intent(LoginActivity.this, EventsLine.class));
    }
}
