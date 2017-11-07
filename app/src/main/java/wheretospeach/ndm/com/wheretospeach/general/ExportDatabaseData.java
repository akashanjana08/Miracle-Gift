package wheretospeach.ndm.com.wheretospeach.general;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ExportDatabaseData
{

	public static void exportDB(String SAMPLE_DB_NAME,Context ctx)
	{
		File sd = Environment.getExternalStorageDirectory();
	    File data = Environment.getDataDirectory();
	       FileChannel source=null;
	       FileChannel destination=null;
	       String currentDBPath = "/data/"+ "wheretospeach.ndm.com.wheretospeach" +"/databases/"+SAMPLE_DB_NAME;
	       String backupDBPath = SAMPLE_DB_NAME;
	       File currentDB = new File(data, currentDBPath);
	       File backupDB = new File(sd, backupDBPath);
	       try {
	            source = new FileInputStream(currentDB).getChannel();
	            destination = new FileOutputStream(backupDB).getChannel();
	            destination.transferFrom(source, 0, source.size());
	            source.close();
	            destination.close();
	           // Toast.makeText(ctx, "DB Exported!", Toast.LENGTH_LONG).show();
	        } catch(IOException e) {
	        	e.printStackTrace();
	        }
	}
}
