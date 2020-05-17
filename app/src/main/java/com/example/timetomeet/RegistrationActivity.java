package com.example.timetomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    Button regBtn;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    EditText username, password1, password2;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dry-everglades-97577.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        username = findViewById(R.id.login);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);

        result = findViewById(R.id.result);
        regBtn = findViewById(R.id.registration_button);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }


    private void register() {

        if (!password1.getText().toString().equals(password2.getText().toString())) {
            result.setText("Пароли не совпадают");
            return;
        }

        Call<Map<String, String>> call = jsonPlaceHolderApi.register(username.getText().toString(), password1.getText().toString());
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                result.setText(response.body().get("result"));
                if (response.body().get("result").equals("success")) {
                    Profile.setUsername(username.getText().toString());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }
}
