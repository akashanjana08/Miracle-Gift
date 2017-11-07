package wheretospeach.ndm.com.wheretospeach;

import android.content.Context;

import org.json.JSONObject;

import wheretospeach.ndm.com.wheretospeach.database.FriendLocationDetailsTable;
import wheretospeach.ndm.com.wheretospeach.database.FriendMaster;
import wheretospeach.ndm.com.wheretospeach.database.FriendMasterTable;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendLocationDetailsObject;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendMasterObject;
import wheretospeach.ndm.com.wheretospeach.general.GeneralNotification;

/**
 * Created by Ndm-PC on 9/3/2016.
 */
public class ParseLocationData
{
    public static void getParseLocationData(String parseData,Context mContext,int n)
    {
        FriendMasterTable friendMasterTable =new FriendMasterTable(mContext);
        FriendLocationDetailsTable friendLocationDetailsTable=new FriendLocationDetailsTable(mContext);

        try
        {
            JSONObject jsonObject=new JSONObject(parseData);
            String textMessage=jsonObject.getString("textMessage");
            String dateTime=jsonObject.getString("dateTime");
            String senderId=jsonObject.getString("senderId");
            String senderName=jsonObject.getString("senderName");

            GeneralNotification.setNotificationMessage(mContext, senderName +" Location ", textMessage, n, MainActivity.class);
            if(MainActivity.mainActivity!=null)
            {
                MainActivity.speakOut(textMessage);
            }

            if(friendMasterTable.isFriendIDExist(senderId))
            {
                friendMasterTable.insertdata(new FriendMasterObject(senderId, senderName));
            }

            friendLocationDetailsTable.insertdata(new FriendLocationDetailsObject(senderId, textMessage, dateTime));


        }
        catch (Exception e)
        {

        }
    }
}
