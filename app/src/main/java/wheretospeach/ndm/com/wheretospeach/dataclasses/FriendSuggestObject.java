package wheretospeach.ndm.com.wheretospeach.dataclasses;

/**
 * Created by Ndm-PC on 9/26/2016.
 */
public class FriendSuggestObject
{
    private String userId;

    private String profileImageUrl;

    private String UserName;

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getProfileImageUrl ()
    {
        return profileImageUrl;
    }

    public void setProfileImageUrl (String profileImageUrl)
    {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserName ()
    {
        return UserName;
    }

    public void setUserName (String UserName)
    {
        this.UserName = UserName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [userId = "+userId+", profileImageUrl = "+profileImageUrl+", UserName = "+UserName+"]";
    }
}
