package wheretospeach.ndm.com.wheretospeach.general;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AlertDialogRadio extends DialogFragment
{

    /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListener alertPositiveListener;
    static public String[] reason;
    private String title,type;



    public void setArryalertDialogRadio(String[] array,String title,String type)
    {
        reason=array;
        this.type=type;
        this.title=title;
    }



    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    public interface AlertPositiveListener
    {
        public void onPositiveClick(int position, String type);
    }

    /** This is a callback method executed when this fragment is attached to an activity.
     *  This function ensures that, the hosting activity implements the interface AlertPositiveListener
     * */
    public void onAttach(android.app.Activity activity)
    {
        super.onAttach(activity);
        try{
            alertPositiveListener = (AlertPositiveListener) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }

    /** This is the OK button listener for the alert dialog,
     *  which in turn invokes the method onPositiveClick(position)
     *  of the hosting activity which is supposed to implement it
     */
    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;


            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClick(position,type);
        }
    };

    /** This is a callback method which will be executed
     *  on creating this fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        /** Getting the arguments passed to this fragment */
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");

        /** Creating a builder for the alert dialog window */
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        /** Setting a title for the window */
        b.setTitle(title);

        /** Setting items to the alert dialog */
        b.setSingleChoiceItems(reason, position, null);

        /** Setting a positive button and its listener */
        b.setPositiveButton("OK",positiveListener);

        /** Setting a positive button and its listener */
        b.setNegativeButton("Cancel", null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();

//        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        int dividerId = getResources().getIdentifier("android:id/titleDivider", null, null);
       // View divider = d.findViewById(dividerId);
       // divider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

 //       colorAlertDialogTitle(d, dividerId);

        /** Return the alert dialog window */
        return d;
    }

    public static void colorAlertDialogTitle(AlertDialog dialog, int color) {
        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        if (dividerId != 0) {
            View divider = dialog.findViewById(dividerId);
            divider.setBackgroundColor(color);
        }

        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        if (textViewId != 0) {
            TextView tv = (TextView) dialog.findViewById(textViewId);
            tv.setTextColor(color);
        }

        int iconId = dialog.getContext().getResources().getIdentifier("android:id/icon", null, null);
        if (iconId != 0) {
            ImageView icon = (ImageView) dialog.findViewById(iconId);
            icon.setColorFilter(color);
        }
    }
}