package csv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by george on 10/11/2015.
 */
public class CreateCsv {
    String extStorageDirectory;
    Context context;
    public CreateCsv(Context context){
        this.context=context;
    }
    public CreateCsv(){}


    private void exportTheDB(Cursor cursor) throws IOException
    {
        File myFile;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String TimeStampDB = sdf.format(cal.getTime());
        extStorageDirectory= Environment.getExternalStorageDirectory().toString();

        try {

            myFile = new File(extStorageDirectory+"/Export_"+TimeStampDB+".csv");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("First Name,Otber names,Id Number,Phone number");
            myOutWriter.append("\n");

            if (cursor!= null) {
                while (cursor.moveToNext()){

                    String f_name = cursor.getString(0);
                    String o_names = cursor.getString(1);
                    String id_number = cursor.getString(2);
                    String phone_number = cursor.getString(3);

                    myOutWriter.append(f_name+","+o_names+","+id_number+","+phone_number);
                    myOutWriter.append("\n");
                }

                cursor.close();
                myOutWriter.close();
                fOut.close();
            }


        } catch (SQLiteException se)
        {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }


    }
}
