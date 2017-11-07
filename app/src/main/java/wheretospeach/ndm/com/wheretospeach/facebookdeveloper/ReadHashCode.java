package wheretospeach.ndm.com.wheretospeach.facebookdeveloper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Ndm-PC on 8/30/2016.
 */
public class ReadHashCode
{
 public static String getFacebookRegisterHashCode(Context mContext,String packageName)
 {

     PackageInfo info;
     try {
         info = mContext.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
         for (android.content.pm.Signature signature : info.signatures) {
             MessageDigest md;
             md = MessageDigest.getInstance("SHA");
             md.update(signature.toByteArray());
             String something = new String(Base64.encode(md.digest(), 0));
             //String something = new String(Base64.encodeBytes(md.digest()));
             Log.e("hash key", something);
             return something;
         }
     } catch (PackageManager.NameNotFoundException e1) {
         Log.e("name not found", e1.toString());
         return null;
     } catch (NoSuchAlgorithmException e) {
         Log.e("no such an algorithm", e.toString());
         return null;
     } catch (Exception e) {
         Log.e("exception", e.toString());
         return null;
     }
     return null;
 }
}
