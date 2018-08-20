package com.hvr.music.vaanimusicplayer;

import android.Manifest;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hvr.music.vaanimusicplayer.adapter.FragmentAdapter;
import com.hvr.music.vaanimusicplayer.handler.BgImageHandler;

import java.io.FileNotFoundException;
import java.io.InputStream;

import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends AppCompatActivity {

    private FragmentAdapter mFragmentAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private GifImageView mGifImageView;
    private ActionBar mActionBar;
    private TypedArray TabIcons;
    private String[] TabNames;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private String mBgimagePath;
    private Uri mBgImageUri;
    private InputStream mBgInputStream;
    private static final int REQUEST_CODE_EXTERNAL_STORAGE = 101;
    private static boolean FIRST_TIME = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermission();
        }
        else
        {
            Log.i("App", "permission already granted");
            makeView();
        }
    }

    private void makeView()
    {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mActionBar = getSupportActionBar();
        mActionBar.setTitle(R.string.home_activity_name);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);

        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.tabL);
        mDrawerLayout = findViewById(R.id.DrawerRoot);
        mNavigationView = findViewById(R.id.nav_view);

        mGifImageView = findViewById(R.id.bg_image_view);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);

        TabIcons = getResources().obtainTypedArray(R.array.HomeTabIcon);
        TabNames = getResources().getStringArray(R.array.HomeScreen);

        for (int No=0; No<=TabIcons.length() - 1 ; No++)
        {
            mTabLayout.addTab(mTabLayout.newTab().setIcon(TabIcons.getResourceId(No,-1)));
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.settings:
                        Intent intent = SettingsActivity.newIntent(HomeActivity.this);
                        mDrawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });*/
    }

    private void requestPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            Log.i("App", "In request permission rationale");
            Snackbar.make(findViewById(android.R.id.content), R.string.permission_error_message,Snackbar.LENGTH_INDEFINITE).setAction(R.string.permission_enabling_message, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL_STORAGE);
                }
            }).show();
        }
        else
        {
            if(FIRST_TIME)
            {
                Log.i("App", "First Time In request permission rationale else part");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL_STORAGE);
                FIRST_TIME = false;
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle(R.string.error_dialog_title);
                builder.setMessage(R.string.error_msg_dialog);
                builder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CODE_EXTERNAL_STORAGE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.i("App", "Permission is granted by the user now");
                    makeView();
                }
                else
                {
                    requestPermission();
                    Log.i("App", "User denied the Permission");
                }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_button).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        mBgimagePath = BgImageHandler.getInstance().getImageUri();
        if(mBgimagePath == null || mBgimagePath.trim().equals(""))
        {
            mGifImageView.setBackgroundResource(R.drawable.bg_track);
        }
        else
        {
            mBgImageUri = Uri.parse(mBgimagePath);
            try {
                mBgInputStream = getContentResolver().openInputStream(mBgImageUri);
                mGifImageView.setBackground(Drawable.createFromStream(mBgInputStream, mBgImageUri.toString()));
            }
            catch(FileNotFoundException fnfe)
            {

            }
        }
        super.onResume();
    }
}
