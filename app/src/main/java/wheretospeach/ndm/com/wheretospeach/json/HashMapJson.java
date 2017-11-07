package wheretospeach.ndm.com.wheretospeach.json;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Ndm-PC on 9/24/2016.
 */
public class HashMapJson
{
   public  static JSONObject getJsonObject(Map<String,String> stringMap)
   {


       JSONObject jsonObject=new JSONObject(stringMap);

       return jsonObject;

   }
}
