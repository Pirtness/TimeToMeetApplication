package com.example.timetomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateEvent extends AppCompatActivity {

    Button chooseTime;
    Button chooseDate;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int curHours;
    int curMinute;
    int curDay, curMonth, curYear;

    long id;

    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        calendar = Calendar.getInstance();
        chooseTime = findViewById(R.id.time);
        chooseDate = findViewById(R.id.date);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curHours = calendar.get(Calendar.HOUR_OF_DAY);
                curMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(CreateEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        chooseTime.setText(String.format("%02d:%02d",hourOfDay, minute));
                    }
                },curHours, curMinute, true);
                timePickerDialog.show();
            }
        });

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curYear = calendar.get(Calendar.YEAR);
                curMonth = calendar.get(Calendar.MONTH);
                curDay = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(CreateEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        chooseDate.setText(String.format("%02d.%02d.%d", dayOfMonth, month, year));
                    }
                }, curYear, curMonth, curDay);
                datePickerDialog.show();
            }
        });

        id = getIntent().getLongExtra("id", -1);
        if (id != -1)
            getAndShowEvent(id);

        saveAndExit();
    }

    private void setInitialValues(Event event) {
        ((EditText)findViewById(R.id.event_name)).setText(event.getName());
        ((EditText)findViewById(R.id.event_description)).setText(event.getDescription());

        ((EditText)findViewById(R.id.country)).setText(event.getCountry());
        ((EditText)findViewById(R.id.city)).setText(event.getCity());
        ((EditText)findViewById(R.id.street)).setText(event.getStreet());
        ((EditText)findViewById(R.id.house)).setText(event.getHouse());
        ((EditText)findViewById(R.id.building)).setText(event.getBuilding());

        ((Button)findViewById(R.id.time)).setText(event.getTime());
        ((Button)findViewById(R.id.date)).setText(event.getDate());
    }

    private void getAndShowEvent(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dry-everglades-97577.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Event> call = jsonPlaceHolderApi.getEvent(id);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                setInitialValues(response.body());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
            }
        });
    }


    private void saveAndExit() {
        saveBtn = (Button)findViewById(R.id.saveButton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.event_name)).getText().toString();
                String description = ((EditText)findViewById(R.id.event_description)).getText().toString();

                String country, city, street, house, building;
                country = ((EditText)findViewById(R.id.country)).getText().toString();
                city = ((EditText)findViewById(R.id.city)).getText().toString();
                street = ((EditText)findViewById(R.id.street)).getText().toString();
                house = ((EditText)findViewById(R.id.house)).getText().toString();
                building = ((EditText)findViewById(R.id.building)).getText().toString();

                String time = ((Button)findViewById(R.id.time)).getText().toString();
                String date = ((Button)findViewById(R.id.date)).getText().toString();

                Event event = new Event(name, description, country, city, street, house,
                        building, time, date);
                if (id != -1)
                    changeEvent(id, event);
                else
                    saveEvent(event);
                finish();
            }
        });
    }

    private void saveEvent(Event event) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dry-everglades-97577.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Event> call = jsonPlaceHolderApi.createEvent(event);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
            }
        });

    }

    private void changeEvent(Long id, Event event) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dry-everglades-97577.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Event> call = jsonPlaceHolderApi.putEvent(id, event);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
            }
        });

    }

}
