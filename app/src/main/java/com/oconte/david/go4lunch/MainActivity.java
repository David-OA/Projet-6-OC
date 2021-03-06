package com.oconte.david.go4lunch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.oconte.david.go4lunch.auth.AuthActivity;
import com.oconte.david.go4lunch.databinding.ActivityMainBinding;
import com.oconte.david.go4lunch.mapView.FragmentMapView;
import com.oconte.david.go4lunch.workMates.FragmentWorkMates;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //@BindView(R.id.toolbar) Toolbar toolbar;


    private ActivityMainBinding binding;

    // Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    //FOR FRAGMENTS
    // Declare fragment handled by Navigation Drawer
    private Fragment fragmentMapView;
    private Fragment fragmentListView;
    private Fragment fragmentWorkMates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ButterKnife.bind(this);

        // For UI -------------------
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureBottomView();;

        // For Fragment -------------
        this.showFirstFragment();

        // For auth
        //this.setUpForStartThisActivity();

        //this.startIfLoginOrNot();

        //this.startAuthActivity();

        this.checkLogOrNotLog();

        //faire la verification a se niveau.

    }

    //////////////////////////////////////////////////////////

    private void checkLogOrNotLog(){


        SharedPreferences preferences = getSharedPreferences("EXTRA_LOG", MODE_PRIVATE);
        boolean resultLogging = preferences.getBoolean(AuthActivity.EXTRA_IS_CONNECTED,false);
        if (!resultLogging) { //siginfie que si ce boolean est faux. equivaux a resultLogging == false
            this.startAuthActivity();
            finish();
        }

    }

    private void startAuthActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    //It's for start after the authentification
    public void setUpForStartThisActivity(){
        Intent i = new Intent(this, AuthActivity.class);
        startActivityForResult(i,RC_SIGN_IN);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //For SIGN OUT
    ///////////////////////////////////////////////////////////////////////////////////////

    // It's for sign out and restart AuthActivity
    private void resultSignOut() {
        //modifier mes sharedpreference pour mettre false au niveau de la clé extra_is_connected lors de la deconnection.
        this.signOutUserFromFirebase();
        this.startAuthActivity();
    }

    // It's for sign Out
    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //////////////////////////// UI ///////////////////////////////////////////////////////////////

    /** Configure the Toolbar */
    protected void configureToolbar() {
        setSupportActionBar(binding.layoutToolbar.toolbar);
        getSupportActionBar().setTitle("I'm Hungry !");
    }

    //////////////////////////////////////////////////////////////////
    // NAVIGATION DRAWER                                            //
    //////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (binding.activityMainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.activityMainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /** Configure Drawer Layout */
    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.activityMainDrawerLayout, binding.layoutToolbar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.activityMainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /** Configure NavigationView  */
    private void configureNavigationView(){
        binding.activityMainNavView.setNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_lunch:
                //this.showFragment(FRAGMENT_LUNCH);
                break;
            case R.id.activity_main_drawer_settings:
                this.startSettingsActivity();
                return true;
            case R.id.activity_main_drawer_logout:
                this.resultSignOut();
                break;
            default:
                break;
        }

        binding.activityMainDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    ////////////////////////////////////////////////////
    // BOTTOM MENU
    ////////////////////////////////////////////////////

    /** Configure BottomView */
    public boolean onNavigationItemSelected(Integer integer) {

        switch (integer){
            case R.id.action_map:
                this.showMapViewFragment();
                break;
            case R.id.action_list:
                this.showListViewFragment();
                return true;
            case R.id.action_workmates:
                this.showWorkMatesFragment();
            default:
                break;
        }

        return true;
    }

    private void configureBottomView() {
        binding.bottomNav.setOnNavigationItemSelectedListener(item-> onNavigationItemSelected(item.getItemId()));
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    // Show first fragment when activity is created
    private void showFirstFragment(){

        fragmentMapView = (FragmentMapView) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);

        if (fragmentMapView == null) {
            fragmentMapView = new FragmentMapView();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, fragmentMapView)
                    .commit();
        }

    }

    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    //Fragment bottom view
    private void showMapViewFragment(){
        if (this.fragmentMapView == null) this.fragmentMapView = FragmentMapView.newInstance();
        this.startTransactionFragment(this.fragmentMapView);
    }

    private void showListViewFragment(){
        if (this.fragmentListView == null) this.fragmentListView = FragmentListView.newInstance();
        this.startTransactionFragment(this.fragmentListView);
    }

    private void showWorkMatesFragment(){
        if (this.fragmentWorkMates == null) this.fragmentWorkMates = FragmentWorkMates.newInstance();
        this.startTransactionFragment(this.fragmentWorkMates);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}