package com.example.garorasu.bay;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.example.garorasu.bay.Fragment.Dashboard;
import com.example.garorasu.bay.Fragment.DialogVehicleOutFragment;
import com.example.garorasu.bay.Fragment.DialogVehicleTypeFragment;
import com.example.garorasu.bay.Fragment.HistoryFragment;
import com.example.garorasu.bay.Fragment.InFragment;
import com.example.garorasu.bay.Fragment.ParkedVehiclesFragment;
import com.example.garorasu.bay.Fragment.OutFragment;
import com.example.garorasu.bay.Fragment.SetFareFragment;
import com.example.garorasu.bay.Fragment.VehicleDetailFragment;
import com.example.garorasu.bay.Helper.PreferencesHelper;
import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.Persistance.DbHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Dashboard.clickFragmentListener,
        InFragment.submitButtonFragmentListener ,
        OutFragment.submitButtonFragmentListener,
        ParkedVehiclesFragment.listParkedVehiclesFragmentInteractionListener,
        HistoryFragment.historyFragmentInteractionListener,
        DialogVehicleTypeFragment.OnDialogVehicleTypeFragmentInteractionListener,
        DialogVehicleOutFragment.OnDialogVehicleOutFragmentInteractionListener,
        SetFareFragment.OnSetFareFragmentInteractionListener{

    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
        setDashboard();
        }
        if(!PreferencesHelper.isFareSet(getApplicationContext())){
            showSetFareFragmentDialog();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            setDashboard();
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
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            db.deleteAllData();
            setDashboard();
            return true;
        }
        if(id == R.id.action_set_fare){
            showSetFareFragmentDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        hideKeyboard();
        if (id == R.id.nav_camera) {
            setDashboard();
        } else if (id == R.id.nav_gallery) {
            setParkedListFragment();
        } else if (id == R.id.nav_slideshow) {
            setHistoryFragment();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDashboard(){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_main, new Dashboard(), "Dashboard").
                    commit();

    }
    public void setInFragment(){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container_main, new InFragment(), "InFragment").
                commit();
    }
    public void setOutFragment(){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container_main, new OutFragment(), "OutFragment").
                commit();
    }
    public void setParkedListFragment(){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container_main, new ParkedVehiclesFragment(), "ParkedVehicle").
                commit();
    }
    public void setVehicleDetailFragment(int uid){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container_main, new VehicleDetailFragment().newInstance(uid), "VehicleDetail").
                commit();
    }
    public void setHistoryFragment(){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container_main, new HistoryFragment(), "ParkingHistory").
                commit();
    }
    public void hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
    public void showVehicleTypeDialog(String vid){
        DialogVehicleTypeFragment d = new DialogVehicleTypeFragment(vid);
        d.show(getSupportFragmentManager(),"VehicleTypeDialog");
    }
    public void vehicleIn(String vid,int type){
        InFragment inFragment = (InFragment) getSupportFragmentManager().findFragmentByTag("InFragment");
        inFragment.vehicleIn(vid,type);
    }
    public void showVehicleOutPaymentDialog(Vehicle vehicle){
        DialogVehicleOutFragment d = new DialogVehicleOutFragment(vehicle);
        d.show(getSupportFragmentManager(),"VehicleOutPaymentDialog");
    }
    public void showSetFareFragmentDialog(){
        SetFareFragment setFareFragment = new SetFareFragment();
        setFareFragment.show(getSupportFragmentManager(),"SetFareFragmentDialog");
    }
}
