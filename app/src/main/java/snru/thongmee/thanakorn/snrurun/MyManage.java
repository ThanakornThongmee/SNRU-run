package snru.thongmee.thanakorn.snrurun;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 17/5/2559.
 */
public class MyManage {

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_name = "Name";
    public static final String column_user = "User";
    public static final String column_password = "Password";
    public static final String column_avata = "Avata";

    public MyManage(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }// Constructor

    public long addUser(String strName,
                        String strUser,
                        String strPassword,
                        String strAvata) {

        ContentValues contentValues = new ContentValues();//เปลี่ยนตัวอักษรให้เป้นตัวเลข
        contentValues.put(column_name, strName);
        contentValues.put(column_user, strUser);
        contentValues.put(column_password, strPassword);
        contentValues.put(column_avata, strAvata);

        return sqLiteDatabase.insert(user_table, null, contentValues);
    }

}//Main Class
