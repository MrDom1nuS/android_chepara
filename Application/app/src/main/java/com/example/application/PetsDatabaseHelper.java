package com.example.application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME= "pets";
    private static final int DB_VERSION = 2;

    public PetsDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlText = "CREATE TABLE Groups ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "number TEXT(10)  NOT NULL," +
                "categoryName TIME(100), " +
                "deliveryLevel INTEGER," +
                "dogExistsFlg BOOLEAN, " +
                "catExistsFlg  BOOLEAN);";
        sqLiteDatabase.execSQL(sqlText);

        updateShema(sqLiteDatabase, 0);

        populateDB(sqLiteDatabase);
    }

    private void populateDB(SQLiteDatabase db) {
        for(PetGroup group: PetGroup.getGroups()){
            insertRow(db, group);
        }
        populatePets(db);
    }

    private void insertRow(SQLiteDatabase db, PetGroup group){
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", group.getNumber());
        contentValues.put("categoryName", group.getCategoryName());
        contentValues.put("deliveryLevel", group.getDeliveryLevel());
        contentValues.put("dogExistsFlg ", group.isDogExistsFlg());
        contentValues.put("catExistsFlg ", group.isCatExistsFlg());
        db.insert("Groups", null, contentValues);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        updateShema(db, i);

    }

    private  void updateShema(SQLiteDatabase db, int ver){
        if(ver < 2){
            db.execSQL("CREATE TABLE Pets (\n" +
                    "id         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name       TEXT(100)  NOT NULL,\n" +
                    "group_id   INTEGER REFERENCES Groups (id)  ON DELETE RESTRICT\n" +
                    "                                           ON UPDATE RESTRICT\n" +
                    ");");
            populatePets(db);

        }
    }
    private void populatePets(SQLiteDatabase db){
        for(Pet pet: Pet.getPets()){
            insertRowToPet(db, pet);
        }
    }

    private  void insertRowToPet(SQLiteDatabase db, Pet pet){
        db.execSQL("insert into pets(name, group_id)\n" +
                "select '"+ pet.getName()+ "', id\n" +
                "from groups\n" +
                "where number='" + pet.getGroupName() +"'");
    }
}
