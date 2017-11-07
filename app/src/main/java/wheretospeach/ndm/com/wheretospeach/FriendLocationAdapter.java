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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendLocationDetailsObject;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendMasterObject;

public class FriendLocationAdapter extends RecyclerView.Adapter<FriendLocationAdapter.MyViewHolder>
{

    List<FriendLocationDetailsObject> FriendLocList;


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView fulladdress, datetextview,monthyeartextview,timetextview;
        public ImageView imgNews,imgapprovestatus;


        public MyViewHolder(View view)
        {
            super(view);
            fulladdress = (TextView) view.findViewById(R.id.fulladdresstextview);
            datetextview = (TextView) view.findViewById(R.id.datetextview);
            monthyeartextview = (TextView) view.findViewById(R.id.monthyeartextview);
            timetextview = (TextView) view.findViewById(R.id.timetextview);


        }
    }


    public FriendLocationAdapter(List<FriendLocationDetailsObject> FriendLocList)
    {
        this.FriendLocList = FriendLocList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        FriendLocationDetailsObject friendlocation = FriendLocList.get(position);
        holder.fulladdress.setText(friendlocation.getLocationName());

        try {
            Date meeting_date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(friendlocation.getDateTime());
            String format = new SimpleDateFormat("dd").format(meeting_date);
            String formatmonthyear = new SimpleDateFormat("MMM  yy").format(meeting_date);

            holder.datetextview.setText(format);
            holder.monthyeartextview.setText(formatmonthyear);


           // Date meeting_time = new SimpleDateFormat("HH:mm").parse(friendlocation.getDateTime());
            String formattime = new SimpleDateFormat("HH:mm").format(meeting_date);

            final String time = formattime;

            try
            {
                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt = _24HourSDF.parse(time);
                System.out.println(_24HourDt);
                System.out.println(_12HourSDF.format(_24HourDt));
                holder.timetextview.setText(_12HourSDF.format(_24HourDt));
            }
            catch (final ParseException e)
            {
                e.printStackTrace();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return FriendLocList.size();
    }


}
