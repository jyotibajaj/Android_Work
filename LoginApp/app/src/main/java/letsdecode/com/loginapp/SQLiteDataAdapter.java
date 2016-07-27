package letsdecode.com.loginapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class SQLiteDataAdapter {
    static SQLiteDataBaseHelper sqLiteDataBaseHelper;

    /*
     just so we can have a reference of object of inner class, in order to call getWritableDatabase method and get a
     reference of SQlite database object.
      */
    private Context context;

    public SQLiteDataAdapter(Context context) {
        sqLiteDataBaseHelper = new SQLiteDataBaseHelper(context);
        this.context = context;

//        //Test Queries
//        insertItemData("item 1", "1 lb");
//        insertItemData("item 2", "1 lb");
//        insertItemData("item 3", "1 lb");
//        insertItemData("item 4", "1 lb");
//        insertItemData("item 5", "1 lb");
//
//        Log.d(SQLiteDataAdapter.class.getSimpleName(), " To DO Items Start =========");
//        ArrayList<Item> todo = getToDoItemData();
//        print(todo);
//        Log.d(SQLiteDataAdapter.class.getSimpleName(), " To DO Items End =========");
//
//        updateItemData("1", "item 1 done", "1 lb");
//        updateItemData("2", "item 2 done", "1 lb");
//        updateItemData("3", "item 3 done", "1 lb");
//
//        Log.d(SQLiteDataAdapter.class.getSimpleName(), " To DO Items after done  Start =========");
//        todo = getToDoItemData();
//        print(todo);
//        Log.d(SQLiteDataAdapter.class.getSimpleName(), " To DO Items after done End =========");
//
//
//        Log.d(SQLiteDataAdapter.class.getSimpleName(), " Done Items Start =========");
//        ArrayList<Item> done = getDoneItemData();
//        print(done);
//        Log.d(SQLiteDataAdapter.class.getSimpleName(), " Done Items End =========");

    }

    void print(ArrayList<Item> toprint) {
        for (Item i : toprint) {
            Log.d(SQLiteDataAdapter.class.getSimpleName(), " item " + i.toString());
        }
    }

    public long insertLoginData(String name, String password, String fullName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteDataBaseHelper.NAME, fullName);
        contentValues.put(SQLiteDataBaseHelper.USERNAME, name);
        contentValues.put(SQLiteDataBaseHelper.PASSWORD, password);
        SQLiteDatabase dbObject;
        dbObject = sqLiteDataBaseHelper.getWritableDatabase();
        long id = dbObject.insert(SQLiteDataBaseHelper.TABLE_USER_INFO, null, contentValues);
        return id;
    }


    //method to insert data(item name and quantity) from input box to data box
    public static void updateItemData(String id, String name, String quantity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteDataBaseHelper.ITEM_NAME, name);
        contentValues.put(SQLiteDataBaseHelper.QUANTITY, quantity);
        contentValues.put(SQLiteDataBaseHelper.STATUS, "done");
        SQLiteDatabase dbObject;
        dbObject = sqLiteDataBaseHelper.getWritableDatabase();
        dbObject.update(SQLiteDataBaseHelper.TABLE_ITEM, contentValues, "item_id=?", new String[]{id});
    }


    //method to insert data(item name and quantity) from input box to data box
    public long insertItemData(String itemName, String quantity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteDataBaseHelper.ITEM_NAME, itemName);
        contentValues.put(SQLiteDataBaseHelper.QUANTITY, quantity);
        contentValues.put(SQLiteDataBaseHelper.STATUS, "todo");
        SQLiteDatabase dbObject;
        dbObject = sqLiteDataBaseHelper.getWritableDatabase();
        long id = dbObject.insert(SQLiteDataBaseHelper.TABLE_ITEM, null, contentValues);
        return id;
    }

    public String getAllData() {
        SQLiteDatabase dbObject;
        dbObject = sqLiteDataBaseHelper.getWritableDatabase();
        String[] columns = {sqLiteDataBaseHelper.UUID, sqLiteDataBaseHelper.USERNAME, sqLiteDataBaseHelper.PASSWORD};
        /* public Cursor query( String table, String[] columns,
        String selection, String[] selectionArgs, String groupBy,
                String having, String orderBy) {
                */
        /* return cursor type object and this cursor object contains the subset of table containing the result.
        cursor is used to retrieve data and we use query method of SQliteDatabase
         */

        Cursor cursor = dbObject.query(sqLiteDataBaseHelper.TABLE_USER_INFO, columns, null, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        // when reach end of the statement moveNxt will return false and execution of loop stops.
        //cursor steps up row wise, it complete one row and then moves to next.
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(sqLiteDataBaseHelper.UUID);
            int index2 = cursor.getColumnIndex(sqLiteDataBaseHelper.USERNAME);
            int index3 = cursor.getColumnIndex(sqLiteDataBaseHelper.PASSWORD);
            int uid = cursor.getInt(index1);
            String userName = cursor.getString(index2);
            String password = cursor.getString(index3);
            stringBuffer.append("" + uid + " " + userName + " " + password + "\n");


        }
        String finalString = stringBuffer.toString();
        return finalString;


    }// method to get specific username and password

    public ArrayList<Item> getToDoItemData() {
        SQLiteDatabase dbObject;
        dbObject = sqLiteDataBaseHelper.getWritableDatabase();
        String[] columns = {sqLiteDataBaseHelper.UID, sqLiteDataBaseHelper.ITEM_NAME, sqLiteDataBaseHelper.QUANTITY, sqLiteDataBaseHelper.STATUS};
        /* public Cursor query( String table, String[] columns,
        String selection, String[] selectionArgs, String groupBy,
                String having, String orderBy) {
                */
        /* return cursor type object and this cursor object contains the subset of table containing the result.
        cursor is used to retrieve data and we use query method of SQliteDatabase
         */
        Cursor cursor = dbObject.query(sqLiteDataBaseHelper.TABLE_ITEM, columns, "status=?", new String[]{"todo"}, null, null, null);
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        // when reach end of the statement moveNxt will return false and execution of loop stops.
        //cursor steps up row wise, it complete one row and then moves to next.
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(sqLiteDataBaseHelper.UID);
            int index2 = cursor.getColumnIndex(sqLiteDataBaseHelper.ITEM_NAME);
            int index3 = cursor.getColumnIndex(sqLiteDataBaseHelper.QUANTITY);
            int index4 = cursor.getColumnIndex(sqLiteDataBaseHelper.STATUS);
            int uid = cursor.getInt(index1);
            String itemName = cursor.getString(index2);
            String quantity = cursor.getString(index3);
            String status = cursor.getString(index4);
            itemArrayList.add(new Item(itemName, quantity, status, uid));

        }
        return itemArrayList;


    }

    public ArrayList<Item> getDoneItemData() {
        SQLiteDatabase dbObject;
        dbObject = sqLiteDataBaseHelper.getWritableDatabase();
        String[] columns = {sqLiteDataBaseHelper.UID, sqLiteDataBaseHelper.ITEM_NAME, sqLiteDataBaseHelper.QUANTITY, sqLiteDataBaseHelper.STATUS};
        /* public Cursor query(String table, String[] columns,
        String selection, String[] selectionArgs, String groupBy,
                String having, String orderBy) {
                */
        /* return cursor type object and this cursor object contains the subset of table containing the result.
        cursor is used to retrieve data and we use query method of SQliteDatabase
         */
        Cursor cursor = dbObject.query(sqLiteDataBaseHelper.TABLE_ITEM, columns, "status=?", new String[]{"done"}, null, null, null);
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        // when reach end of the statement moveNxt will return false and execution of loop stops.
        //cursor steps up row wise, it complete one row and then moves to next.
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(sqLiteDataBaseHelper.UID);
            int index2 = cursor.getColumnIndex(sqLiteDataBaseHelper.ITEM_NAME);
            int index3 = cursor.getColumnIndex(sqLiteDataBaseHelper.QUANTITY);
            int index4 = cursor.getColumnIndex(sqLiteDataBaseHelper.STATUS);
            int uid = cursor.getInt(index1);
            String itemName = cursor.getString(index2);
            String quantity = cursor.getString(index3);
            String status = cursor.getString(index4);
            itemArrayList.add(new Item(itemName, quantity, status, uid));
        }
        return itemArrayList;
    }


    public String validateEnteredInfoWhileLogin(String name, String userPassword) {
        // select name, password from Table where name = 'abc';
        SQLiteDatabase dbObject;
        dbObject = sqLiteDataBaseHelper.getWritableDatabase();
        String[] columns = {sqLiteDataBaseHelper.USERNAME, sqLiteDataBaseHelper.PASSWORD};
        String[] selectionArgs = {name, userPassword};
        /* public Cursor query( String table, String[] columns,
        String selection, String[] selectionArgs, String groupBy,
                String having, String orderBy) {
                */
        // return cursor type object and this cursor object contains the subset of table containing the result.
        Cursor cursor = dbObject.query(sqLiteDataBaseHelper.TABLE_USER_INFO, columns, sqLiteDataBaseHelper.USERNAME + " =? AND " + sqLiteDataBaseHelper.PASSWORD + " =? ", selectionArgs, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        // when reach end of the statement moveNxt will return false and execution of loop stops.
        while (cursor.moveToNext()) {
            int index2 = cursor.getColumnIndex(sqLiteDataBaseHelper.USERNAME);
            int index3 = cursor.getColumnIndex(sqLiteDataBaseHelper.PASSWORD);
            String userName = cursor.getString(index2);
            LogMessage.logInfo(context, userName.toString());
            String password = cursor.getString(index3);
            stringBuffer.append(userName + " " + password + "\n");
        }
        String finalString = stringBuffer.toString();
        return finalString;


    }

    /*
      This method validates whether the data entered is already there in a data base.
       */
    public String validateEnteredInfoWhileSignUp(String name, String userName, String userPassword) {
        // select name, password from Table where name = 'abc';
        SQLiteDatabase dbObject;
        dbObject = sqLiteDataBaseHelper.getReadableDatabase();
        String[] columns = {sqLiteDataBaseHelper.NAME, sqLiteDataBaseHelper.USERNAME, sqLiteDataBaseHelper.PASSWORD};
        String[] selectionArgs = {name, userName, userPassword};
        /* public Cursor query( String table, String[] columns,
        String selection, String[] selectionArgs, String groupBy,
                String having, String orderBy) {
                */
        // return cursor type object and this cursor object contains the subset of table containing the result.
        Cursor cursor = dbObject.query(sqLiteDataBaseHelper.TABLE_USER_INFO, columns, sqLiteDataBaseHelper.NAME + "=? AND " + sqLiteDataBaseHelper.USERNAME + "=? AND " + sqLiteDataBaseHelper.PASSWORD + "=? ", selectionArgs, null, null, null, null);
        Log.i("cursor", cursor.toString());
        StringBuffer stringBuffer = new StringBuffer();
        // when reach end of the statement moveNxt will return false and exection of loop stops.
        while (cursor.moveToNext()) {
            int index2 = cursor.getColumnIndex(sqLiteDataBaseHelper.NAME);
            int index3 = cursor.getColumnIndex(sqLiteDataBaseHelper.USERNAME);
            int index4 = cursor.getColumnIndex(sqLiteDataBaseHelper.PASSWORD);
            String fullName = cursor.getString(index2);
            String userNam = cursor.getString(index3);
            String password = cursor.getString(index4);

            stringBuffer.append(fullName + " " + userNam + " " + password + "\n");


        }
        String finalString = stringBuffer.toString();
        return finalString;


    }

    // inner class so that its private variable can only be accessible to outer class
    static class SQLiteDataBaseHelper extends SQLiteOpenHelper {
        /*
        This class talks about creating schema eg: database name, table name, columns names etc.

         */

        private static final int DATABASE_VERSION = 3;
        private static final String DATABASE_NAME = "userInformationDataBase";

        //USER info table and columns
        private static final String TABLE_USER_INFO = "userInformationTable";
        private static final String UUID = "_id";
        private static final String USERNAME = "userName";
        private static final String PASSWORD = "password";
        private static final String NAME = "fullName";
        private static final String DROP_USER_INFO_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER_INFO;


        //USER info table and columns
        private static final String TABLE_ITEM = "itemInformationTable";
        private static final String UID = "item_id";
        private static final String ITEM_NAME = "itemName";
        private static final String QUANTITY = "quantity";
        private static final String STATUS = "status";


        private static final String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS " + TABLE_ITEM;


        private Context context;
        private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_USER_INFO + "("
                + UUID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " + USERNAME + " VARCHAR(255), " + PASSWORD + " VARCHAR(255)) ";


        private static final String CREATE_TABLE_ITEM = "CREATE TABLE " + TABLE_ITEM + "("
                + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME + " VARCHAR(255), " + QUANTITY + " VARCHAR(255), " + STATUS + " VARCHAR(255)) ";

        // constructor
        public SQLiteDataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            LogMessage.logInfo(context, "Constructor executed");
            this.context = context;
        }


        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            // toast message to get info if onCreate is called
            LogMessage.logInfo(context, "onCreate executed");
            try {
                db.execSQL(CREATE_TABLE_LOGIN);
                db.execSQL(CREATE_TABLE_ITEM);
            } catch (SQLException e) {
                LogMessage.logInfo(context, "" + e);
                Log.d("exception on create", "" + e);


            }

        }


        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LogMessage.logInfo(context, "onUpgrade executed");

            // Drop older table if existed
            try {
                db.execSQL(DROP_USER_INFO_TABLE);
                db.execSQL(DROP_ITEM_TABLE);
                // Create tables again
                onCreate(db);
            } catch (SQLException e) {
                LogMessage.logInfo(context, "" + e);
            }


        }


    }
}
