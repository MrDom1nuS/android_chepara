package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class GroupListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_list);


        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int group = ((PetGroup) adapterView.getItemAtPosition(i)).getId();
                Intent intent = new Intent(GroupListActivity.this,
                        PetGroupActivity.class);
                intent.putExtra(PetGroupActivity.GROUP_NUMBER, group);
                startActivity(intent);
            }
        };


        ListView listView = (ListView) findViewById(R.id.groups_list);
        listView.setOnItemClickListener(listener);

        ArrayAdapter<PetGroup> adapter = new ArrayAdapter<PetGroup>(
                this,
                android.R.layout.simple_list_item_1,
                getDataFromDB()
        );
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_menu, menu);

        String text = "";
        for(PetGroup group: PetGroup.getGroups()) {
            text += group.getNumber() +"\n";
        }

        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        shareActionProvider.setShareIntent(intent);

        return super.onCreateOptionsMenu(menu);

    }

    public void AddGrpClick(View view){
        Intent intent = new Intent(GroupListActivity.this,
                AddPetGroupActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_group:
                startActivity(
                        new Intent(this,AddPetGroupActivity.class)
                );
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<PetGroup> getDataFromDB() {
        ArrayList<PetGroup> groups = new ArrayList<PetGroup>();

        SQLiteOpenHelper sqliteHelper = new PetsDatabaseHelper(this);
        try {
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            Cursor cursor = db.query("groups",
                    new String[]{"number", "categoryName", "deliveryLevel",
                            "dogExistsFlg", "catExistsFlg", "id"},
                    null, null, null,

                    null, "number");
            while (cursor.moveToNext()) {
                groups.add(
                        new PetGroup(
                                cursor.getInt(5),
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                (cursor.getInt(3) > 0),
                                (cursor.getInt(4) > 0)
                        )
                );
            }
            cursor.close();
            db.close();
        }

        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,"Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();

        }
        return  groups;

    }
}