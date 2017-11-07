package wheretospeach.ndm.com.wheretospeach;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import wheretospeach.ndm.com.wheretospeach.general.SharedPrefence;

/**
 * Created by Ndm-PC on 9/1/2016.
 */
public class SpeachActionBar extends ActionBarActivity
{


    public SharedPreferences sharedPreference;
    public SharedPreferences.Editor sharedPreferenceEdit;
    public String cityName;
    public ActionBar actionBar;
    public TextView actionBarTitleTextView;
    String UserId;

    protected void setActionBarTitle(String actionBarTitle)
    {

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDark));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.actionbar_textview, null);
        actionBarTitleTextView=(TextView)v.findViewById(R.id.title);
        actionBarTitleTextView.setText(actionBarTitle);


        actionBar.setCustomView(v);



        // setsharedPrefrence
        setSharedPrefrence();
    }


    void setSharedPrefrence()
    {
        sharedPreference= SharedPrefence.getSharedPrefence(this);
        sharedPreferenceEdit=sharedPreference.edit();


        UserId=sharedPreference.getString("USER_ID", "");

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.

                this.finish();

                // overridePendingTransition(R.animator.anim_slide_in_right,R.animator.anim_slide_out_right);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }






}
