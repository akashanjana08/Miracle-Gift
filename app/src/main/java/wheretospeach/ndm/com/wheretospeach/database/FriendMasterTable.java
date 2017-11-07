package wheretospeach.ndm.com.wheretospeach.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendMasterObject;

/**
 * Created by Ndm-PC on 8/17/2016.
 */
public class FriendMasterTable extends  SpeachDatabase
{

    Context ctx;

    public FriendMasterTable(Context context) {
        super(context);
        ctx = context;

    }


    public String insertdata(FriendMasterObject attObj) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FRIEND_ID, attObj.getFriendID());
        values.put(KEY_FRIEND_NAME, attObj.getFriendName());

        db.insert(TABLE_FRIEND_MASTER, null, values);

        db.close();

        return null;
    }




    public List<FriendMasterObject> getAllFriendMaster()
    {
        List<FriendMasterObject> friendMaster= new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        String query="select * from "+TABLE_FRIEND_MASTER;
        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do
            {
                FriendMasterObject obj = new FriendMasterObject();
                obj.setFriendID(cursor.getString(cursor.getColumnIndex(KEY_FRIEND_ID)));
                obj.setFriendName(cursor.getString(cursor.getColumnIndex(KEY_FRIEND_NAME)));

                friendMaster.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  friendMaster;

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

    public boolean isFriendIDExist(String fid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_FRIEND_MASTER, null,KEY_FRIEND_ID+" =?", new String[]{String.valueOf(fid)}, null, null,null);

        if(cursor.getCount()==0 ||cursor.getCount()==-1)
        {
            return	true;
        }
        else
        {

            return  false;
        }
    }



}
