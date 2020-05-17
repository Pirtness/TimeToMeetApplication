package com.example.timetomeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventsLine extends AppCompatActivity {

    JsonPlaceHolderApi jsonPlaceHolderApi;


    LinearLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_line);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dry-everglades-97577.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        myLayout = (LinearLayout)findViewById(R.id.myLayout);

        showEvents();
    }

    private void showEvent(final Event event) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.weight = 1;

        TextView name = new TextView(EventsLine.this);
        name.setTag(new Long(event.getId()));
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsLine.this, EventActivity.class);
                long id = (long)((TextView)v).getTag();
                intent.putExtra("id", id);
                intent.putExtra("own", event.getUsername().equals(Profile.getUsername()));
                startActivity(intent);
            }
        });

        name.setTextSize(20);
        name.setTextColor(Color.BLACK);
        name.setLayoutParams(params);
        name.setText(event.getName());
        LinearLayout layout = new LinearLayout(EventsLine.this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(name);
        myLayout.addView(layout);

        TextView date = new TextView(EventsLine.this);
        date.setLayoutParams(params);
        date.setText(event.getDate());
        TextView time = new TextView(EventsLine.this);
        time.setLayoutParams(params);
        time.setText(event.getTime());
        time.setGravity(Gravity.RIGHT);

        layout = new LinearLayout(EventsLine.this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(date);
        layout.addView(time);


        TextView address = new TextView(EventsLine.this);
        address.setLayoutParams(params);
        address.setText(event.getAddress().toString());

        ImageView photo = new ImageView(EventsLine.this);
        myLayout.addView(photo);
        myLayout.addView(layout);
        myLayout.addView(address);


        View divider = new View(EventsLine.this);
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 40);
        divider.setBackgroundColor(Color.LTGRAY);
        divider.setLayoutParams(params);
        myLayout.addView(divider);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addEvent) {
            startActivity(new Intent(EventsLine.this, CreateEvent.class));
        } else if (id == R.id.showAll) {
            myLayout.removeAllViews();
            showEvents();
        } else {
            myLayout.removeAllViews();
            showUsersEvents();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        myLayout.removeAllViews();
        showEvents();
        super.onRestart();
    }




    //Requests
    private void showEvents() {
        Call<List<Event>> call = jsonPlaceHolderApi.getEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                for (Event event : response.body())
                    showEvent(event);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
            }
        });
    }

    private void showUsersEvents() {
        Call<List<Event>> call = jsonPlaceHolderApi.getEventsOfUser(Profile.username);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                for (Event event : response.body())
                    showEvent(event);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }


}
