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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventActivity extends AppCompatActivity {

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        id = getIntent().getLongExtra("id", -1);
        showEvent(id);

    }

    private void showEvent(Event event) {

        LinearLayout myLayout = (LinearLayout)findViewById(R.id.myLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.weight = 1;

        TextView name = new TextView(EventActivity.this);
        name.setTextSize(20);
        name.setTextColor(Color.BLACK);
        name.setLayoutParams(params);
        name.setText(event.getName());

        TextView descr = new TextView(EventActivity.this);
        descr.setTextSize(20);
        descr.setTextColor(Color.LTGRAY);
        descr.setLayoutParams(params);
        descr.setText(event.getDescription());

        LinearLayout layout = new LinearLayout(EventActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(name);
        layout.addView(descr);
        myLayout.addView(layout);

        TextView date = new TextView(EventActivity.this);
        date.setLayoutParams(params);
        date.setText(event.getDate());
        TextView time = new TextView(EventActivity.this);
        time.setLayoutParams(params);
        time.setText(event.getTime());
        time.setGravity(Gravity.RIGHT);

        layout = new LinearLayout(EventActivity.this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(date);
        layout.addView(time);


        TextView address = new TextView(EventActivity.this);
        address.setLayoutParams(params);
        address.setText(event.getAddress().toString());

        ImageView photo = new ImageView(EventActivity.this);
        myLayout.addView(photo);
        myLayout.addView(layout);
        myLayout.addView(address);


        View divider = new View(EventActivity.this);
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 40);
        divider.setBackgroundColor(Color.LTGRAY);
        divider.setLayoutParams(params);
        myLayout.addView(divider);

    }

    private void showEvent(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dry-everglades-97577.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Event> call = jsonPlaceHolderApi.getEvent(id);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                showEvent(response.body());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

            }
        });
    }


    private void deleteEvent() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dry-everglades-97577.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Void> call = jsonPlaceHolderApi.deleteEvent(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        boolean own = getIntent().getBooleanExtra("own", false);
        if (own) {
            menu.findItem(R.id.changeEvent).setEnabled(true);
            menu.findItem(R.id.deleteEvent).setEnabled(true);
        } else {
            menu.findItem(R.id.changeEvent).setEnabled(false);
            menu.findItem(R.id.deleteEvent).setEnabled(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.changeEvent) {
            Intent intent = new Intent(EventActivity.this, CreateEvent.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } else if (menuId == R.id.deleteEvent) {
            deleteEvent();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
        LinearLayout myLayout = (LinearLayout)findViewById(R.id.myLayout);
        myLayout.removeAllViews();
        showEvent(id);
        super.onRestart();
    }
}
