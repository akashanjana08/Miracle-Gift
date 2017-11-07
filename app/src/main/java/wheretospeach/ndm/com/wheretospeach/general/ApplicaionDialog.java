package wheretospeach.ndm.com.wheretospeach.general;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;


/**
 * Created by Ndm-PC on 4/30/2016.
 */
public class ApplicaionDialog
{

    static Context mContext;
    private static ApplicaionDialog instance = null;
    private static Typeface type;
    private static String  titlee,meaagae,posButtonName,NegButtonName;
    DialogInterface.OnClickListener negativeButton;


    private ApplicaionDialog()
    {

    }


     public static ApplicaionDialog setMessage(String title,String message,String positiveButtonName,String nagativeButtonName,Context mmContext)
     {
         mContext=mmContext;
         titlee=title;
         meaagae=message;
         posButtonName=positiveButtonName;
         NegButtonName=nagativeButtonName;

         if(instance == null)
         {
             instance = new ApplicaionDialog();
         }
        return  instance;
     }




    public void buildDialog(DialogInterface.OnClickListener positiveButton)
     {
         createDialog(positiveButton, negativeButton);

         negativeButton=new DialogInterface.OnClickListener()
         {
             public void onClick(DialogInterface dialog, int id)
             {
                 dialog.dismiss();
             }
         };

     }




    public void buildDialog(DialogInterface.OnClickListener positiveButton,DialogInterface.OnClickListener negativeButtonn)
    {
        createDialog(positiveButton, negativeButton);

        negativeButton=negativeButtonn;


    }





      private static void createDialog(DialogInterface.OnClickListener positiveButton,DialogInterface.OnClickListener negativeButton)
      {

          AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

          builder.setMessage(meaagae)
                  .setCancelable(false)
                  .setPositiveButton(posButtonName, positiveButton)
                  .setNegativeButton(NegButtonName, negativeButton) ;

          final AlertDialog alert = builder.create();


          alert.show();

      }





}
