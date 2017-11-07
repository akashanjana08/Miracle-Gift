package wheretospeach.ndm.com.wheretospeach.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendLocationDetailsObject;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendMasterObject;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class FriendLocationDetailsTable extends  SpeachDatabase
{

    Context ctx;

    public FriendLocationDetailsTable(Context context) {
        super(context);
        ctx = context;

    }


    public String insertdata(FriendLocationDetailsObject attObj)
    {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FRIEND_ID, attObj.getFriendID());
        values.put(KEY_LOCATION_NAME, attObj.getLocationName());
        values.put(KEY_DATE_TIME, attObj.getDateTime());

        db.insert(TABLE_FRIEND_LOCATION_DETAILS, null, values);

        db.close();

        return null;
    }




    public List<FriendLocationDetailsObject> getAllBankMaster(String friendId)
    {
        List<FriendLocationDetailsObject> friendLocationMaster= new ArrayList<FriendLocationDetailsObject>();
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_FRIEND_LOCATION_DETAILS +" where "+KEY_FRIEND_ID +" ='"+friendId+"'";
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                FriendLocationDetailsObject obj = new FriendLocationDetailsObject();
                obj.setDateTime(cursor.getString(cursor.getColumnIndex(KEY_DATE_TIME)));
                obj.setLocationName(cursor.getString(cursor.getColumnIndex(KEY_LOCATION_NAME)));

                friendLocationMaster.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  friendLocationMaster;

    }



    public String getlatestLocationoverFriendId(String friendId)
    {
        String locationName="Not Available";
        SQLiteDatabase db= getReadableDatabase();
        String query="select FriendLocationDetails.LocationName, max(FriendLocationDetails.DateTime) from FriendLocationDetails where FriendLocationDetails.FriendID='"+friendId+"'";
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
              locationName=cursor.getString(cursor.getColumnIndex(KEY_LOCATION_NAME));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  locationName;

    }


//    public String getStateNameOverStateId(String stateId)
//    {
//        String state="";
//        SQLiteDatabase db= getReadableDatabase();
//        String query="select * from "+TABLE_STATE_MASTER +" where "+KEY_STATE_ID +" = '"+stateId+"'";
//        Cursor cursor=db.rawQuery(query, null);
//        if (cursor.moveToFirst())
//        {
//            do
//            {
//
//                state=cursor.getString(cursor.getColumnIndex(KEY_STATE_NAME));
//
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return  state;
//
//    }
}
