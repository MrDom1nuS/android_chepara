package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPetGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_group);
    }
    public void OnAddGrpBtnClick(View view){
        EditText number = (EditText) findViewById(R.id.addgrpEdit);
        EditText faculty = (EditText) findViewById(R.id.addCategoryEdit);

        SQLiteOpenHelper sqliteHelper = new PetsDatabaseHelper(this);
        try {
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("number", number.getText().toString());
            contentValues.put("categoryName", faculty.getText().toString());
            contentValues.put("treatsLevel", 0);
            contentValues.put("dogExistsFlg ", 0);
            contentValues.put("catExistsFlg ", 0);
            db.insert("groups", null, contentValues);
            db.close();
            NavUtils.navigateUpFromSameTask(this);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}