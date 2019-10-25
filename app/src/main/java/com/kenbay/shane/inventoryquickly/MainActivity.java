package com.kenbay.shane.inventoryquickly;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private dbHelper db;
    private Button add_data;
    EditText add_name;
    private ListView userList;
    private ArrayList<String> listItem;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new dbHelper(this);
        listItem = new ArrayList<>();

        userList = findViewById(R.id.users_list);
        add_data = findViewById(R.id.add_data);
        add_name = findViewById(R.id.add_name);

        viewData();

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String text = userList.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }


        });

        userList.setOnItemLongClickListener();

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = add_name.getText().toString();
                if (!name.equals("") && db.insertData(name)) {
                    Toast.makeText(MainActivity.this, "Data Added!", Toast.LENGTH_SHORT).show();
                    add_name.setText("");
                    listItem.clear();
                    viewData();
                } else {
                    Toast.makeText(MainActivity.this, "Data Not Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewData() {
        Cursor cursor = db.viewData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listItem.add(cursor.getString(1));
            }

            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
            userList.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> userslist = new ArrayList<>();

                for (String user : listItem) {
                    if (user.toLowerCase().contains(newText.toLowerCase())) {
                        userslist.add(user);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        MainActivity.this, android.R.layout.simple_list_item_1, userslist);
                userList.setAdapter(adapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


//    private void updateUI() {
//        ArrayList<String> invList = new ArrayList<>();
//        SQLiteDatabase db = db.getReadableDatabase();
//        Cursor cursor = d
//    }
}
