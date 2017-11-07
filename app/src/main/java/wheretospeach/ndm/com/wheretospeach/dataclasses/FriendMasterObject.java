package wheretospeach.ndm.com.wheretospeach.dataclasses;

import java.io.Serializable;

/**
 * Created by Ndm-PC on 9/3/2016.
 */
public class FriendMasterObject implements Serializable
{
   private  String friendID;
   private  String friendName;

    public FriendMasterObject(){}





    public FriendMasterObject(String friendID, String friendName) {
        this.friendID = friendID;
        this.friendName = friendName;
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
