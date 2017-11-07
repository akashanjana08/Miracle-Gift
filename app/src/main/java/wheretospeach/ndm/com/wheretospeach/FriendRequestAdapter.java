package wheretospeach.ndm.com.wheretospeach;

/**
 * Created by Ndm-PC on 8/22/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wheretospeach.ndm.com.wheretospeach.database.FriendLocationDetailsTable;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendMasterObject;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.MyViewHolder>
{

    List<FriendMasterObject> friendterList;
    FriendLocationDetailsTable friendLocationDetailsTable;


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, lastlocation;
        public ImageView imgNews,imgapprovestatus;


        public MyViewHolder(View view)
        {
            super(view);
//            name = (TextView) view.findViewById(R.id.name);
//            lastlocation = (TextView) view.findViewById(R.id.lastlocation);
//            imgNews= (ImageView) view.findViewById(R.id.imgNews);
//            imgapprovestatus= (ImageView) view.findViewById(R.id.imageViewapprove);

        }
    }


    public FriendRequestAdapter(List<FriendMasterObject> friendterList, FriendLocationDetailsTable friendLocationDetailsTable)
    {
        this.friendterList = friendterList;
        this.friendLocationDetailsTable=friendLocationDetailsTable;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

//        FriendMasterObject friend = friendterList.get(position);
//        holder.name.setText(friend.getFriendName());
//        //holder.description.setText(report.getInfoId());
//
//        String location=friendLocationDetailsTable.getlatestLocationoverFriendId(friend.getFriendID());
//        holder.lastlocation.setText(location);

    }

    @Override
    public int getItemCount() {
        return friendterList.size();
    }


}
