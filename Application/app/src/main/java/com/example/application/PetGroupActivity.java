package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PetGroupActivity extends AppCompatActivity {

    public  static final String GROUP_NUMBER = "groupnumber";
    private PetGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_group2);

        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(PetsListActivity.GROUP_NUMBER,0);
        group = null;
        SQLiteOpenHelper sqliteHelper = new PetsDatabaseHelper(this);
        try {
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            Cursor cursor = db.query("groups",
                    new String[]{"number", "categoryName", "deliveryLevel",
                            "dogExistsFlg", "catExistsFlg", "id"},
                    "id=?", new String[] {Integer.toString(grpNumber)}, null,

                    null, null);
            if (cursor.moveToFirst()) {
                group = new PetGroup(

                        cursor.getInt(5),
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        (cursor.getInt(3) > 0),
                        (cursor.getInt(4) > 0)
                );

            }
            else{
                Toast toast = Toast.makeText(this,
                        "Can't find group with id " + Integer.toString(grpNumber),
                        Toast.LENGTH_SHORT);

            }
            cursor.close();
            db.close();
        }

        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,"Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();

        }
        if (group != null) {
            EditText txtGrpNumber = (EditText) findViewById(R.id.grpEdit);
            txtGrpNumber.setText(group.getNumber());

            EditText txtCategoryName = (EditText) findViewById(R.id.categoryEdit);
            txtCategoryName.setText(group.getCategoryName());

            TextView txtImgGrp = (TextView) findViewById(R.id.grpNumberImageTxt);
            txtImgGrp.setText(group.getNumber());


            if(group.getDeliveryLevel() == 0){
                ((RadioButton) findViewById(R.id.edu_level_d)).setChecked(true);
            }
            else{
                ((RadioButton) findViewById(R.id.edu_level_pick)).setChecked(true);
            }
            ((CheckBox) findViewById(R.id.dog_flg)).setChecked(
                    group.isDogExistsFlg()
            );
            ((CheckBox) findViewById(R.id.cat_flg)).setChecked(
                    group.isCatExistsFlg()
            );
        }
    }

    public void onOkBtnClick(View view){
        SQLiteOpenHelper sqliteHelper = new PetsDatabaseHelper(this);

        ContentValues contentValues = new ContentValues();
        contentValues.put("number",
                ((TextView) findViewById(R.id.grpEdit)).getText().toString()
        );
        contentValues.put("categoryName",
                ((TextView) findViewById(R.id.categoryEdit)).getText().toString()
        );
        contentValues.put("deliveryLevel",
                ((RadioButton) findViewById(R.id.edu_level_pick)).isChecked()?1:0
        );
        contentValues.put("dogExistsFlg",
                ((CheckBox) findViewById(R.id.dog_flg)).isChecked()
        );
        contentValues.put("catExistsFlg",
                ((CheckBox) findViewById(R.id.cat_flg)).isChecked()
        );

        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(GROUP_NUMBER,0);

        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            db.update("groups",
                    contentValues,
                    "id=?",
                    new String[] {Integer.toString(grpNumber)}
            );
            db.close();
            NavUtils.navigateUpFromSameTask(this);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onDelete(View view) {

        SQLiteOpenHelper sqliteHelper = new PetsDatabaseHelper(this);

        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(GROUP_NUMBER, 0);

        try {
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            db.delete("groups",
                    "id=?",
                    new String[]{Integer.toString(grpNumber)}
            );
            db.close();
            NavUtils.navigateUpFromSameTask(this);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onBtnPetListClick (View view) {

        Intent newIntent = new Intent(this, PetsListActivity.class);
        newIntent.putExtra(PetsListActivity.GROUP_NUMBER, group.getId());
        startActivity(newIntent);
    }
}