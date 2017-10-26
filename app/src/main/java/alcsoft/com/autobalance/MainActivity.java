package alcsoft.com.autobalance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import alcsoft.com.autobalance.internal.PurchaseHandler;
import alcsoft.com.autobalance.internal.UserVars;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Declares Handlers and Objects
    public static PurchaseHandler purchaseHandler;
    public static UserVars userVars;
    public static String AvailSpendingAmt = Float.toString(0.00f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Call method to initialize handler and object
        initHandlersandObject();
        // Sets the MainFragment as default fragment.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,new MainFragment());
        fragmentTransaction.commit();
    }

    protected void onPause(){
        super.onPause();
        // Save all values and pause activity
        SharedPreferences sharedPreferences = getSharedPreferences("AutoBalanceData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Saves the UserValues to storage
        editor.putString("UserValues",userVars.getUserVarsForStorage());
        // Saves the Purchase ArrayList to storage
        editor.putString("PurchaseList",purchaseHandler.getPurchaseListforStorage());
        // Saves the Top Value to storage
        editor.putInt("topValue",purchaseHandler.getTop());
        // Saves the Total Purchase Amount to storage
        editor.putFloat("NetPAmt",purchaseHandler.getTotalPAmt());
        editor.commit();
    }

    protected void onResume(){
        super.onResume();
        // Reload the objects and values from storage and resume activity
        initHandlersandObject();
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_main_layout) {
            // Handle the main page
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

        } else if (id == R.id.nav_options_layout) {
            // Handle Options page
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SetupFragment()).commit();

        } else if (id == R.id.nav_purchases_layout){
            // Handle Transactions page
            fragmentManager.beginTransaction().replace(R.id.content_frame, new PurchasesFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Method to initialize handlers and objects
    private void initHandlersandObject(){
        // Loads the sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AutoBalanceData", Context.MODE_PRIVATE);
        // Loads the UserVar object in a temp string and loads it to the object
        String data = sharedPreferences.getString("UserValues","none");
        userVars = new UserVars(data);

        // Loads the PurchaseList object in a temp string and loads it to the handler object
        data = sharedPreferences.getString("PurchaseList","none");
        int tempint = sharedPreferences.getInt("TopValue",0);
        float tempfl = sharedPreferences.getFloat("NetPAmt",0.00f);
        purchaseHandler = new PurchaseHandler(data,tempint,tempfl);
    }

    public static void updateSpendingAmt(){
        AvailSpendingAmt = String.format("%.02f",userVars.getUserNetIncome() - purchaseHandler.getTotalPAmt());
    }

}