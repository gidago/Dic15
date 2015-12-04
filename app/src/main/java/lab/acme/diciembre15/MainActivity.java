package lab.acme.diciembre15;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static Calendar dateTime = Calendar.getInstance();

    private TextView mDiasLeft;
    private TextView mHoursLeft;
    private TextView mMinutesLeft;
    private TextView mSecondsLeft;

    private Context mContext;

    public MediaPlayer mp;
    private boolean mSonando = false;

    private int mArtista = R.raw.fin_modulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MainActivity.this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                if(!mSonando)
                    play_mp();
                else{
                    stop_mp();
                }
                mSonando = !mSonando;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        mDiasLeft    = (TextView) findViewById(R.id.pis_day);
        mHoursLeft   = (TextView) findViewById(R.id.pis_hour);
        mMinutesLeft = (TextView) findViewById(R.id.pis_minute);
        mSecondsLeft = (TextView) findViewById(R.id.pis_second);
        ImageView factCardImage = (ImageView) findViewById(R.id.detail_icon);
        Picasso.with(mContext).load(R.drawable.no_category).into(factCardImage);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        calculo();
                    }
                });
            }
        }, 0, 100);
    }




    private void play_mp() {
    // TODO Auto-generated method stub
        mp= MediaPlayer.create(this, mArtista);
        mp.start();
    }
    private void stop_mp() {
    // TODO Auto-generated method stub
        mp.stop();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_img_spinner) {
            // Handle the  action
            Intent activityPis;
            activityPis = new Intent(this, ActivityImageSpinner.class);
            startActivity(activityPis);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_modulos) {
            mArtista = R.raw.fin_modulos;
        } else if (id == R.id.nav_medina) {
            mArtista = R.raw.fin_medina;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void calculo(){

        //String dateStart = "21/11/2015 09:29:58";
        String dateStart = getCurrentDate("dd/MM/yyyy HH:mm:ss");
        //String dateStop = "01/04/2016 00:00:00";
        String dateStop =  "01/04/2016 00:00:00";

        //Log.e(LOG_TAG, "===========================>>>> dateStar:   " + dateStart);
        //Log.e(LOG_TAG, "===========================>>>> dateStop:   " + dateStop);

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date d1;
        Date d2;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            mDiasLeft.setText(String.format(" %03d ",diffDays));
            mHoursLeft.setText(String.format(" %02d ",diffHours));
            mMinutesLeft.setText(String.format(" %02d ", diffMinutes));
            mSecondsLeft.setText(String.format(" %02d ", diffSeconds));

            //salida = "( " + diffDays + " dias, " + diffHours + " horas, " + diffMinutes + " minutos, " + diffSeconds + " segundos."+ " )" ;
            //mTitle.setText(salida);
            //Log.e(LOG_TAG, "***diff dates: :   " + diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds.");

            //mTitle.invalidate();
            mDiasLeft.invalidate();
            mHoursLeft.invalidate();
            mMinutesLeft.invalidate();
            mSecondsLeft.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get today's date in any format.
     *
     * @param dateFormat pass format for get like: "yyyy-MM-dd hh:mm:ss"
     * @return current date in string format
     */
    public static String getCurrentDate(String dateFormat) {
        Date d = new Date();
        String currentDate = new SimpleDateFormat(dateFormat).format(d.getTime());
        return currentDate;
    }

}
