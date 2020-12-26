package com.oconte.david.go4lunch;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bottom_nav) BottomNavigationView bottomNavigationView;
    @BindView(R.id.activity_main_drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView navigationView;


    //FOR FRAGMENTS
    // 1 - Declare fragment handled by Navigation Drawer
    private Fragment fragmentHome;
    private Fragment fragmentCoordonnees;
    private Fragment fragmentExperiences;
    private Fragment fragmentEtudes;
    private Fragment fragmentLangues;
    private Fragment fragmentInterests;

    //FOR DATAS
    // 2 - Identify each fragment with a number
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_MAP_VIEW = 1;
    private static final int FRAGMENT_EXPERIENCES = 2;
    private static final int FRAGMENT_LUNCH = 3;
    private static final int FRAGMENT_SETTINGS = 4;
    private static final int FRAGMENT_LOGOUT = 5;

    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.configureToolbar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.configureBottomView();

    }

    ////////////////////////////////////////////////////////////////////////
    // For auth
    ////////////////////////////////////////////////////////////////////////


    @OnClick(R.id.main_activity_button_login_google)
    public void onClickLoginButton() {
        // 3 - Launch Sign-In Activity when user clicked on Login Button
        this.startSignInActivity();
    }

    // 2 - Launch Sign-In Activity
    private void startSignInActivity(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .setIsSmartLockEnabled(false, true)
                        //.setLogo(R.drawable.)
                        .build(),
                RC_SIGN_IN);
    }


    //////////////////////////////////////////////////////////////////
    // NAVIGATION DRAWER                                            //
    //////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *  - Configure Drawer Layout
     */
    private void configureDrawerLayout(){
        //this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     *  - Configure NavigationView
     */
    private void configureNavigationView(){
        //this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_lunch:
                this.showFragment(FRAGMENT_LUNCH);
                break;
            case R.id.activity_main_drawer_settings:
                this.startSettingsActivity();
                return true;
            case R.id.activity_main_drawer_logout:
                this.showFragment(FRAGMENT_LOGOUT);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    // ---------------------
    // TOOLBAR
    // ---------------------

    /**
     *  - Configure the Toolbar
     */
    protected void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("I'm Hungry");
    }


    ////////////////////////////////////////////////////
    // BOTTOM MENU
    ////////////////////////////////////////////////////
    /**
     *  - Configure BottomView
     */
    public boolean onNavigationItemSelected(Integer integer) {

        switch (integer){
            case R.id.action_map:
                this.showFragment(FRAGMENT_LUNCH);
                break;
            case R.id.action_list:
                this.startSettingsActivity();
                return true;
            case R.id.action_workmates:
                this.showFragment(FRAGMENT_LOGOUT);
                break;
            default:
                break;
        }

        return true;
    }

    private void configureBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item-> onNavigationItemSelected(item.getItemId()));
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    // 5 - Show fragment according an Identifier

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_LUNCH:
                //this.showEtudesFragment();
                break;
            case FRAGMENT_LOGOUT:
                //this.showInterestsFragment();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}