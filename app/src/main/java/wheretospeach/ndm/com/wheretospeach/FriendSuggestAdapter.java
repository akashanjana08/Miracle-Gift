package wheretospeach.ndm.com.wheretospeach;

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
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendSuggestObject;
import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;
import wheretospeach.ndm.com.wheretospeach.imageloader.ImageLoader;

/**
 * Created by Ndm-PC on 9/26/2016.
 */
public class FriendSuggestAdapter extends RecyclerView.Adapter<FriendSuggestAdapter.MyViewHolder> implements AsyncResult {

    private List<FriendSuggestObject> horizontalList;
    Context mContext;
    public static ImageLoader imageLoader;
    String UserId;
    AdapterResponse adapterResponse;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  txtView;
        public Button    button;
        public ImageView imgView;

        public MyViewHolder(View view) {
            super(view);
            txtView = (TextView) view.findViewById(R.id.txtView);
            button  = (Button) view.findViewById(R.id.add_friend_button);
            imgView = (ImageView) view.findViewById(R.id.imgView);

        }
    }


    public FriendSuggestAdapter(List<FriendSuggestObject> horizontalList,Context mContext,String UserId,AdapterResponse adapterResponse) {
        this.horizontalList = horizontalList;
        this.mContext=mContext;
        imageLoader = new ImageLoader(mContext);
        this.UserId=UserId;
        this.adapterResponse=adapterResponse;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.f_suggest_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.txtView.setText(horizontalList.get(position).getUserName());
        imageLoader.DisplayImage(horizontalList.get(position).getProfileImageUrl(), holder.imgView);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext, horizontalList.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                if (ConnectionDetector.isConnectingToInternet(mContext))
                {

                    new HttpAsyncResult(mContext, WebUrl.SEND_FRIEND_REQUEST+"?friend_one="+UserId+"&friend_two="+horizontalList.get(position).getUserId(), null, "FRIEND_SUGGEST",FriendSuggestAdapter.this).execute();
                }
                else
                {
                    Toast.makeText(mContext, "Please Connect To Internet", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }




    @Override
    public void getResponse(String key, String data)
    {
        adapterResponse.adapterResponse(data);
    }
}
