package wheretospeach.ndm.com.wheretospeach.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class SpeachDatabase extends SQLiteOpenHelper
{
    String CREATE_TABLE_FRIEND_MASTER,CREATE_TABLE_FRIEND_LOCATION_DETAILS;


    //Database Details
    private static final String DATABASE_NAME="Speech_Database";
    private static final int DATABASE_VERSION=1;

    //Table

    protected static final String TABLE_FRIEND_MASTER            = "FriendMaster";
    protected static final String TABLE_FRIEND_LOCATION_DETAILS  = "FriendLocationDetails";




    // TABLE_FRIEND_MASTER
    protected static final String KEY_FRIEND_ID                   = "FriendID";
    protected static final String KEY_FRIEND_NAME                 = "FriendName";



    // TABLE_FRIEND_LOCATION_DETAILS
    protected static final String KEY_LOCATION_NAME               = "LocationName";
    protected static final String KEY_DATE_TIME                   = "DateTime";





    public SpeachDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        InitialTable();

        db.execSQL(CREATE_TABLE_FRIEND_MASTER);
        db.execSQL(CREATE_TABLE_FRIEND_LOCATION_DETAILS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    private void InitialTable()
    {

        CREATE_TABLE_FRIEND_MASTER  = "CREATE TABLE " + TABLE_FRIEND_MASTER + "("
                + KEY_FRIEND_ID + " TEXT,"
                + KEY_FRIEND_NAME +" TEXT"+")";


        CREATE_TABLE_FRIEND_LOCATION_DETAILS  = "CREATE TABLE " + TABLE_FRIEND_LOCATION_DETAILS + "("
                + KEY_FRIEND_ID + " TEXT,"
                + KEY_LOCATION_NAME + " TEXT," + KEY_DATE_TIME +" TEXT"+")";


    }

}

