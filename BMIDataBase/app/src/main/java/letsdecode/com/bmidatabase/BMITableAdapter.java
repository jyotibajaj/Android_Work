package letsdecode.com.bmidatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by aashi on 10/13/15.
 */
public class BMITableAdapter {

        DBHelper dbHelper;
        SQLiteDatabase database;

        public BMITableAdapter(Context context) {
            // TODO Auto-generated constructor stub
            dbHelper = new DBHelper(context);
        }

        // to open db
        public void openDB() {
            database = dbHelper.getWritableDatabase();
        }

        // to close db
        public void closeDB() {
            database.close();
        }

        // to insert the records
        public void insertValues(String date, String bmiValue) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.COL_DATE, date);
            contentValues.put(DBHelper.COL_BMI, bmiValue);
            database.insert(DBHelper.TABLE_NAME, null, contentValues);
        }

        // to get all records
        public Cursor getAllRecords() {
            return database.rawQuery("select * from " + DBHelper.TABLE_NAME, null);
        }

        // to delete all records
        public void deleteAllRecords() {
            database.delete(DBHelper.TABLE_NAME, null, null);
        }

        // to delete record
        public void deleteRecord(String rowid) {
            database.delete(DBHelper.TABLE_NAME, rowid + "=" + DBHelper.COL_ROWID, null);
        }

    }
