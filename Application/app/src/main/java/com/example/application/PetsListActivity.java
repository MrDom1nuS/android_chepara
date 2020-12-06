package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class PetsListActivity extends AppCompatActivity {

    public static final String GROUP_NUMBER = "groupnumber";
    private Cursor cursor;
    private SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_list);

        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(GROUP_NUMBER,0);

        ListView listView = (ListView) findViewById(R.id.petsList);
        SimpleCursorAdapter adapter = getDataFromDB(grpNumber);
        if(adapter != null){
            listView.setAdapter(adapter);
        }

    }

    private SimpleCursorAdapter getDataFromDB (int groupId){
        SimpleCursorAdapter listAdapter = null;

        SQLiteOpenHelper sqliteHelper = new PetsDatabaseHelper(this);
        try {
            db = sqliteHelper.getReadableDatabase();
            cursor = db.rawQuery("select s.id _id, name, number\n"+
                    "from pets s inner join groups g on s.group_id = g.id\n"+
                    "where g.id = ?", new String[]{Integer.toString(groupId)});

            listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[] {"name"},
                    new int[] {android.R.id.text1},
                    0);

        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        return listAdapter;
    }
    protected void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
    public  void  onSendBtnClick(View view) {
        TextView textView = (TextView) findViewById(R.id.sendBtn);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plan");
        intent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT,"");
        startActivity(intent);
    }
}