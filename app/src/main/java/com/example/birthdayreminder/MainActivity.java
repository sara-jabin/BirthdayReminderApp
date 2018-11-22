package com.example.birthdayreminder;

import android.arch.persistence.room.Room;
import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private RecyclerView recyclerView;
    private BirthdayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(this, AppDatabase.class, "birthday_db")
                .allowMainThreadQueries()
                .build();

        recyclerView = findViewById(R.id.birthday_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BirthdayAdapter(database.getBirthdayDao(), new BirthdayAdapter.NotifyInterface() { //Hier wird kein Interface übergeben, sondern eine Anonyme Klasse, die das Interface implementiert
            @Override
            public void notifyEvent(int position) {
                adapter.removeItem(position);
            }
        });

        recyclerView.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add) {
            startActivity(new Intent(this, AddItemActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateData();
    }

}
