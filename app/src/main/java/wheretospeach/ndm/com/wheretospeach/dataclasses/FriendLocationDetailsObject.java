package wheretospeach.ndm.com.wheretospeach.dataclasses;

/**
 * Created by Ndm-PC on 9/3/2016.
 */
public class FriendLocationDetailsObject
{
   private  String friendID;
   private  String locationName;
   private  String dateTime;


    public FriendLocationDetailsObject(){}


    public FriendLocationDetailsObject(String friendID, String locationName, String dateTime)
    {
        this.friendID = friendID;
        this.locationName = locationName;
        this.dateTime = dateTime;
    }


    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
