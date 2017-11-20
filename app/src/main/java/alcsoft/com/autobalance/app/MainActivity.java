package alcsoft.com.autobalance.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import alcsoft.com.autobalance.features.shared.interfaces.MainDataInterface;
import alcsoft.com.autobalance.features.purchases.Purchase;
import alcsoft.com.autobalance.features.purchases.PurchaseHandler;
import alcsoft.com.autobalance.R;
import alcsoft.com.autobalance.features.options.UserData;
import alcsoft.com.autobalance.features.purchases.PurchasesFragment;
import alcsoft.com.autobalance.features.quickinfo.QuickInfoFragment;
import alcsoft.com.autobalance.features.options.OptionsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainDataInterface {

    // Declares Handlers and Objects
    private PurchaseHandler purchaseHandler;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Fragment mContent = getSupportFragmentManager().getFragment(savedInstanceState, "SavedFragment");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, mContent);
            fragmentTransaction.commit();
        } else {
            // Sets the QuickInfoFragment as default fragment.
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, new QuickInfoFragment());
            fragmentTransaction.commit();
        }

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
        SharedPreferences sharedPreferences = getSharedPreferences("AutoBalanceData", Context.MODE_PRIVATE);
        // Loads the UserVar object in a temp string and loads it to the object
        String data = sharedPreferences.getString("UserValues", "none");
        userData = new UserData(data);

        // Loads the PurchaseList object in a temp string and loads it to the handler object
        data = sharedPreferences.getString("PurchaseList", "none");
        int tempint = sharedPreferences.getInt("TopValue", 0);
        float tempfl = sharedPreferences.getFloat("NetPAmt", 0.00f);
        purchaseHandler = new PurchaseHandler(data, tempint, tempfl);


    }

    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("AutoBalanceData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Saves the UserValues to storage
        editor.putString("UserValues", userData.getDataToSave());
        // Saves the Purchase ArrayList to storage
        editor.putString("PurchaseList", purchaseHandler.getDataToSave());
        // Saves the Top Value to storage
        editor.putInt("TopValue",purchaseHandler.getTop());
        // Saves the Total Purchase Amount to storage
        editor.putFloat("NetPAmt", purchaseHandler.getTotalPurchaseAmt());
        editor.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "SavedFragment", getSupportFragmentManager().findFragmentByTag("Fragment"));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_quickinfo_layout) {
            // Handle the main page
            fragmentManager.beginTransaction().replace(R.id.content_frame, new QuickInfoFragment(), "Fragment").commit();

        } else if (id == R.id.nav_options_layout) {
            // Handle Options page
            fragmentManager.beginTransaction().replace(R.id.content_frame, new OptionsFragment(), "Fragment").commit();

        } else if (id == R.id.nav_purchases_layout) {
            // Handle Transactions page
            fragmentManager.beginTransaction().replace(R.id.content_frame, new PurchasesFragment(), "Fragment").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onIncomeEdit(float income) {
        System.out.println(income);
        userData.setUserMonthlyIncome(income);
    }

    @Override
    public void onExpensesEdit(float expenses) {
        System.out.println(expenses);
        userData.setUserMonthlyExpenses(expenses);
    }

    @Override
    public void onPurchaseAdd(String name, float amt) {
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        purchaseHandler.addPurchase(name, date1, amt);
    }

    @Override
    public void onPurchaseEdit(int position, Date date, String name, float amt) {
        purchaseHandler.editPurchaseAt(position, date, name, amt);
    }

    @Override
    public void onPurchaseRemoveAt(int position) {
        purchaseHandler.removePurchaseAt(position);
    }

    @Override
    public void onPurchaseListReset() {
        purchaseHandler.removeAllPurchases();
    }

    // Accessors
    public String getCurrentIncome() {
        NumberFormat in = NumberFormat.getCurrencyInstance();
        return in.format(userData.getUserMonthlyIncome());
    }

    public String getCurrentExpenses() {
        NumberFormat in = NumberFormat.getCurrencyInstance();
        return in.format(userData.getUserMonthlyExpenses());
    }

    public String getCurrentNetIncome() {
        NumberFormat in = NumberFormat.getCurrencyInstance();
        return in.format(userData.getUserMonthlyNetIncome());
    }

    public String getCurrentAmtAvail() {
        NumberFormat in = NumberFormat.getCurrencyInstance();
        return in.format(userData.getUserMonthlyNetIncome() - purchaseHandler.getTotalPurchaseAmt());
    }

    public ArrayList<Purchase> getCurrentList() {
        return purchaseHandler.getPurchaseList();
    }

    public String getCurrentPurchaseAmtTotal() {
        NumberFormat in = NumberFormat.getCurrencyInstance();
        return in.format(purchaseHandler.getTotalPurchaseAmt());
    }

    public String getPurchaseInfoAt(int position) {
        Purchase purchase = purchaseHandler.getPurchaseInfoAt(position);
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        NumberFormat in = NumberFormat.getCurrencyInstance();
        return df.format(purchase.getPurchaseDate()) + " | " + purchase.getPurchaseName() + " | " + in.format(purchase.getPurchaseAmt());
    }

    @Override
    public Float getRawCurrentPurchaseAmtTotal() {
        return purchaseHandler.getTotalPurchaseAmt();
    }

    @Override
    public Float getRawCurrentIncome() {
        return userData.getUserMonthlyIncome();
    }

    @Override
    public Float getRawCurrentNetIncome() {
        return userData.getUserMonthlyNetIncome();
    }

}