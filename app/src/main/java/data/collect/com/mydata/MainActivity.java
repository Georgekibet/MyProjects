package data.collect.com.mydata;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gordonwong.materialsheetfab.MaterialSheetFab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import DataBase.Farmer;
import Repositories.FarmerRepository;
import utils.Fab;


public class MainActivity extends ActionBarActivity implements BaseFragment.OnFabsetup {
    MaterialSheetFab materialSheetFab;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Fab fabButton;
    private int phone,idnumber;
    private String firstName,otherNAmes;

    TextView createNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        setupDrawer();

        FragmentManager fm=getFragmentManager();
        Fragment fragment=new DataListingFragment();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();

        TextView dumpDbTv= (TextView) findViewById(R.id.drawer_dumpdb);
        dumpDbTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();

                new DBBack_Up().execute();
            }
        });
    }

   /* */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id== android.R.id.home){getDrawerToggleDelegate();}
        return super.onOptionsItemSelected(item);
    }
    private void setupActionBar() {
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_list_black_48dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDis
    }
    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer,
                R.string.closedrawer);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    public MaterialSheetFab getFabSheet(){
        return  materialSheetFab;
    }

    public void setFabButton(int resid){
        fabButton.setImageResource(resid);
    }

    @Override
    public void setUpFab(int resId) {



    }

    @Override
    public void onFabClicked() {

    }

    public void setDate(String date){

           Button b= (Button)findViewById(R.id.button_date);
           this.dateString=date;
           b.setText(date);
    }
    public Date getDateString(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

        Date date=new Date();
        try {

             date = formatter.parse(this.dateString);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  date;
    }
    private String dateString="yy/mm/dd";


    private class DBBack_Up extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog;
        // can use UI thread here
        protected void onPreExecute() {
            /*dialog=new ProgressDialog(getApplicationContext());
            dialog.setMessage("Exporting database...");
            dialog.show();*/
        }

        // automatically done on worker thread (separate from UI thread)
        protected Boolean doInBackground(final String... args) {
            String DATABASE_NAME = "mydata_db";
            File from = getApplicationContext().getDatabasePath(DATABASE_NAME);
            File exportDir = Environment.getExternalStorageDirectory();
            String backupDBPath = "mydata_db.db";
            File file = new File(exportDir, backupDBPath);

            try {
                this.copyFile(from, file);
                return true;
            } catch (IOException e) {
                Log.e("mypck", e.getMessage(), e);
                return false;
            }
        }

        // can use UI thread here
        protected void onPostExecute(final Boolean success) {
           /* if (dialog.isShowing()) {
                dialog.dismiss();
            }*/


            if (success) {
                Toast.makeText(getApplicationContext(), "Export successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Export failed", Toast.LENGTH_SHORT).show();
            }
        }

        @SuppressWarnings("resource")
        void copyFile(File src, File dst) throws IOException {
            FileChannel inChannel = new FileInputStream(src).getChannel();
            FileChannel outChannel = new FileOutputStream(dst).getChannel();
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } finally {
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            }
        }
    }
}
