package wheretospeach.ndm.com.wheretospeach;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import wheretospeach.ndm.com.wheretospeach.database.FriendLocationDetailsTable;
import wheretospeach.ndm.com.wheretospeach.database.FriendMasterTable;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendLocationDetailsObject;
import wheretospeach.ndm.com.wheretospeach.dataclasses.FriendMasterObject;
import wheretospeach.ndm.com.wheretospeach.general.ConnectionDetector;
import wheretospeach.ndm.com.wheretospeach.general.WebUrl;
import wheretospeach.ndm.com.wheretospeach.htturlasync.AsyncResult;
import wheretospeach.ndm.com.wheretospeach.htturlasync.HttpAsyncResult;
import wheretospeach.ndm.com.wheretospeach.speach.recognition.SpeechTextConversation;
import wheretospeach.ndm.com.wheretospeach.speach.recognition.TextToSpeechRecognition;

public class AddressListActivity extends SpeachActionBar implements AsyncResult
{

    private RecyclerView recyclerView;
    private FriendLocationAdapter mAdapter;
    List<FriendLocationDetailsObject> FriendLocList;
    TextToSpeechRecognition textToSpeechRecognition;
    String friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);


        //actionBarTitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        String friendId =getIntent().getStringExtra("FRIEND_ID");
        friendName =getIntent().getStringExtra("FRIEND_NAME");

        setActionBarTitle(friendName);
        FriendLocationDetailsTable friendLocationDetailsTable =new FriendLocationDetailsTable(this);
        FriendLocList=friendLocationDetailsTable.getAllBankMaster(friendId);

        mAdapter = new FriendLocationAdapter(FriendLocList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                textToSpeechRecognition=new TextToSpeechRecognition();
                textToSpeechRecognition.speakOut("Where is "+friendName, AddressListActivity.this);
                if (ConnectionDetector.isConnectingToInternet(AddressListActivity.this))
                {
                   new HttpAsyncResult(AddressListActivity.this, WebUrl.GET_USER_LOCATION_URL+"?userName="+friendName.trim()+"&userId="+UserId, null, "DEVICE_REGISTER", AddressListActivity.this).execute();
                } else {
                    Toast.makeText(AddressListActivity.this, "Please Connect To Internet", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    @Override
    public void getResponse(String key, String data)
    {

        if(key.equalsIgnoreCase("DEVICE_REGISTER"))
        {
            Toast.makeText(this,data+"",Toast.LENGTH_LONG).show();
            if(data.length()>20)
            {
                textToSpeechRecognition.speakOut("Please Wait While we find The Location",AddressListActivity.this);
            }
            else
            {
                textToSpeechRecognition.speakOut("User Not For Try again",AddressListActivity.this);
            }
        }

    }
}
