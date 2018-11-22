package com.example.birthdayreminder;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.Date;

public class AddItemActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendar;
    private EditText nameText;

    private String name;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.addToolbar);
        setSupportActionBar(toolbar);

        nameText = findViewById(R.id.editName);
        calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_save) {
            //get Data from Views
            this.name = nameText.getText().toString();

            Birthday birthday = new Birthday();
            birthday.setDay(this.day);
            birthday.setMonth(this.month);
            birthday.setYear(this.year);
            birthday.setName(this.name);

            AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "birthday_db")
                    .allowMainThreadQueries()
                    .build();

            database.getBirthdayDao().addBirthday(birthday);

            startActivity(new Intent(this, MainActivity.class));
        }
        return false;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month + 1;
        this.day = dayOfMonth;
    }
}
