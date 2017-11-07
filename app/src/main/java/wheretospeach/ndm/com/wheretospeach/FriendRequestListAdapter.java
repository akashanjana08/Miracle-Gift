package wheretospeach.ndm.com.wheretospeach;

/**
 * Created by Ndm-PC on 8/22/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import wheretospeach.ndm.com.wheretospeach.database.FriendLocationDetailsTable;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendLocationListObject;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendMasterObject;
import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;
import wheretospeach.ndm.com.wheretospeach.imageloader.ImageLoader;

public class FriendRequestListAdapter extends RecyclerView.Adapter<FriendRequestListAdapter.MyViewHolder>
{

    List<FriendLocationListObject> friendterList;
    FriendLocationDetailsTable friendLocationDetailsTable;
    public static ImageLoader imageLoader;
    Context mContext;
    String userId;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, lastlocation;
        public ImageView imgView;
        public Button confirmbutton,cancelrequest;

        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            //lastlocation = (TextView) view.findViewById(R.id.lastlocation);
            imgView= (ImageView) view.findViewById(R.id.imageView);
            confirmbutton= (Button) view.findViewById(R.id.confirmbutton);
            cancelrequest= (Button) view.findViewById(R.id.cancelrequest);

        }
    }


    public FriendRequestListAdapter(List<FriendLocationListObject> friendterList, Context mContext,String userId)
    {
        this.friendterList = friendterList;

        imageLoader = new ImageLoader(mContext);
        this.mContext=mContext;
        this.userId=userId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position)
    {

//        SELECT `friend_one` a FROM `friendsRelation` WHERE `status`='1' and `friend_two`='GB2809d2'
//        union
//        SELECT `friend_two` a FROM `friendsRelation` WHERE `status`='1' and `friend_one`='GB280975'


       final FriendLocationListObject friend = friendterList.get(position);
        holder.name.setText(friend.getUserName());

        imageLoader.DisplayImage(friend.getProfileImageUrl(), holder.imgView);
        holder.confirmbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext, ""+friend.getUserId(), Toast.LENGTH_LONG).show();
                if (ConnectionDetector.isConnectingToInternet(mContext))
                {
                    new HttpAsyncResult(mContext, WebUrl.CONFIRM_FRIEND_REQUEST+"?friend_one="+userId+"&friend_two="+friendterList.get(position).getUserId()+"&status=1", null, "FRIEND_CONFIRM",null).execute();
                }
                else
                {
                  //  Toast.makeText(mContext, "Please Connect To Internet", Toast.LENGTH_LONG).show();

                }

            }
        });

        holder.cancelrequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (ConnectionDetector.isConnectingToInternet(mContext))
                {
                    new HttpAsyncResult(mContext, WebUrl.CONFIRM_FRIEND_REQUEST+"?friend_one="+userId+"&friend_two="+friendterList.get(position).getUserId()+"&status=2", null, "FRIEND_CONFIRM",null).execute();
                }
                else
                {
                    //  Toast.makeText(mContext, "Please Connect To Internet", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return friendterList.size();
    }


}
