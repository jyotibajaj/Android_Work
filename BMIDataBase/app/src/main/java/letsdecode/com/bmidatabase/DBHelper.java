package letsdecode.com.bmidatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aashi on 10/13/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bmidb";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "bmitable";
    public static final String COL_ROWID = "rowid";
    public static final String COL_BMI = "BMI";
    public static final String COL_DATE = "date";

    String CREATE_TABLE = "create table bmitable(rowid integer primary key autoincrement,date text,bmi text)";

    // to create db
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
    }

    // to create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE);
    }

    // to upgrade db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}





