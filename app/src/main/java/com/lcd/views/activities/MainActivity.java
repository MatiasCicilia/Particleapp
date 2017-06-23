package com.lcd.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lcd.controllers.PhotonController;
import com.lcd.views.fragments.DeviceListFragment;
import com.lcd.views.fragments.GlobalConfigListFragment;
import com.lcd.views.fragments.GlobalListFragment;
import com.lcd.views.fragments.InputListFragment;
import com.lcd.views.fragments.OutputListFragment;
import com.lcd.views.fragments.VariableListFragment;
import com.lcd.views.fragments.dialogs.CreateGlobalDialogFragment;
import com.lcd.views.fragments.dialogs.CreateInputDialogFragment;
import com.lcd.views.fragments.dialogs.CreateOutputDialogFragment;
import com.lcd.views.fragments.dialogs.CreateVariableDialogFragment;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleEventVisibility;
import io.particle.android.sdk.utils.Async;
import lcd.particle.R;

import static lcd.particle.R.id.fab;
import static lcd.particle.R.id.nav_config;
import static lcd.particle.R.id.nav_devices;
import static lcd.particle.R.id.nav_globals;
import static lcd.particle.R.id.nav_inputs;
import static lcd.particle.R.id.nav_outputs;
import static lcd.particle.R.id.nav_variables;

/*
Cloud SDK usage mostly revolves around two main classes:

    ParticleCloud, which is the interface for all cloud operations not specific to a claimed device,
     such as user authentication, retrieving a user's device list, claiming devices, and more

    ParticleDevice, which represents a claimed device. Each instance enables operations specific to that device,
     e.g.: invoking functions, reading variables, and accessing basic info like the device's name and version info.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DeviceListFragment deviceListFragment;
    private VariableListFragment variableListFragment;
    private OutputListFragment outputListFragment;
    private InputListFragment inputListFragment;
    private GlobalListFragment globalListFragment;
    private GlobalConfigListFragment globalConfigListFragment;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PhotonController.getInstance().load(this);
        ParticleCloudSDK.init(this);

        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {
            @Override
            public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                ParticleCloudSDK.getCloud().logIn("nachoberdinas@gmail.com", "wololo");

                PhotonController.getInstance().setDevices(ParticleCloudSDK.getCloud().getDevices());
                return null;
            }

            @Override
            public void onSuccess(@NonNull Object o) {
                Log.d(TAG, "Logged in as " + ParticleCloudSDK.getCloud().getLoggedInUsername());
            }

            @Override
            public void onFailure(@NonNull ParticleCloudException exception) {
                Log.wtf(TAG, "Failed to log in");
            }
        });



        fab = (FloatingActionButton) findViewById(R.id.fab);


        deviceListFragment = new DeviceListFragment();
        variableListFragment = new VariableListFragment();
        outputListFragment = new OutputListFragment();
        inputListFragment = new InputListFragment();
        globalListFragment = new GlobalListFragment();
        globalConfigListFragment = new GlobalConfigListFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_devices));
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
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    PhotonController.getInstance().configure();
                }
            };
            new Thread(runnable).start();
            return true;
        }
        else if (id == R.id.action_save) {
            PhotonController.getInstance().save(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        fab.show();
        switch (id) {
            case nav_config:
                fragment = globalConfigListFragment;
                fab.hide();
                break;
            case nav_devices:
                fragment = deviceListFragment;
                fab.hide();
                break;
            case nav_variables:
                fragment = variableListFragment;
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CreateVariableDialogFragment().show(getFragmentManager(),"");
                    }
                });
                break;
            case nav_inputs:
                fragment = inputListFragment;
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CreateInputDialogFragment().show(getFragmentManager(),"");
                    }
                });
                break;
            case nav_outputs:
                fragment = outputListFragment;
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CreateOutputDialogFragment().show(getFragmentManager(),"");
                    }
                });
                break;
            case nav_globals:
                fragment = globalListFragment;
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CreateGlobalDialogFragment().show(getFragmentManager(),"");
                    }
                });
        }

        if(fragment!=null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction().replace(R.id.container, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
