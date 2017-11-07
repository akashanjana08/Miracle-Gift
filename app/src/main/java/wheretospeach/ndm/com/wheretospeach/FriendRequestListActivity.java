package wheretospeach.ndm.com.wheretospeach;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndexApi;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import wheretospeach.ndm.com.wheretospeach.database.FriendLocationDetailsTable;
import wheretospeach.ndm.com.wheretospeach.database.FriendMasterTable;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendLocationListObject;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendMasterObject;
import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;

public class FriendRequestListActivity extends SpeachActionBar implements AsyncResult
{

    private RecyclerView recyclerView;
    private FriendRequestListAdapter mAdapter;
    List<FriendLocationListObject> FriendterList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        setActionBarTitle("Friend Requests");
        //actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //FriendMasterTable friendMasterTable =new FriendMasterTable(this);
       // FriendterList=friendMasterTable.getAllFriendMaster();




        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                FriendMasterObject friendObject = FriendterList.get(position);
//
//                Intent friendLocationIntent = new Intent(getApplicationContext(), AddressListActivity.class);
//                friendLocationIntent.putExtra("FRIEND_ID", friendObject.getFriendID());
//                friendLocationIntent.putExtra("FRIEND_NAME", friendObject.getFriendName());
//                startActivity(friendLocationIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent homeIntent = new Intent(FriendRequestListActivity.this, MainActivity.class);
                startActivity(homeIntent);


            }
        });



        if (ConnectionDetector.isConnectingToInternet(this))
        {

            new HttpAsyncResult(this, WebUrl.GET_FRIEND_REQUEST_LIST+"?friend_one="+UserId+"&count_friend_request=1", null, "FRIEND_SUGGEST", this).execute();
        }
        else
        {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
        }
    }







    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private FriendRequestListActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FriendRequestListActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
        {

        }
    }

    @Override
    public void getResponse(String key, String data)
    {

        if(!data.equalsIgnoreCase("null")) {
            Gson gson = new Gson();
            FriendLocationListObject[] favoriteItems = gson.fromJson(data,
                    FriendLocationListObject[].class);

            List<FriendLocationListObject> FriendterList = Arrays.asList(favoriteItems);

            mAdapter = new FriendRequestListAdapter(FriendterList, this, UserId);


            recyclerView.setAdapter(mAdapter);

        }
    }
}
