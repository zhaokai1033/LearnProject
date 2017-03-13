package com.zk.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.zk.baselibrary.app.BaseFra;
import com.zk.sample.databinding.ActivityMainBinding;

import java.util.HashMap;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener {

    private BaseFra mCurrentFragment;
    private HashMap<String, BaseFra> fragments = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //添加要改变的控件属性
        dynamicAddView(binding.toolbar, "background", R.color.colorPrimary);
        dynamicAddView(binding.navigationView.getHeaderView(0), "background", R.color.colorPrimary);
        dynamicAddView(binding.navigationView, "navigationViewMenu", R.color.colorPrimary);

        binding.navigationView.setNavigationItemSelectedListener(this);
        binding.navigationView.getMenu().getItem(0).setChecked(true);
        replaceFragment(HomeFragment.newInstance(), R.id.frame_content, false);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        BaseFra baseFragment = null;
        if (id == R.id.nav_home) {
            if (fragments.containsKey(HomeFragment.class.getSimpleName())) {
                baseFragment = fragments.get(HomeFragment.class.getSimpleName());
            } else {
                baseFragment = HomeFragment.newInstance();
            }
        } else if (id == R.id.nav_theme) {
            if (fragments.containsKey(ThemeFragment.class.getSimpleName())) {
                baseFragment = fragments.get(ThemeFragment.class.getSimpleName());
            } else {
                baseFragment = ThemeFragment.newInstance();
            }
        } else if (id == R.id.nav_me) {
            if (fragments.containsKey(WebFragment.class.getSimpleName())) {
                baseFragment = fragments.get(WebFragment.class.getSimpleName());
            } else {
                baseFragment = WebFragment.newInstance("https://github.com/zhaokai1033");
            }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if(baseFragment!=null) {
            fragments.put(baseFragment.getClass().getSimpleName(), baseFragment);
            mCurrentFragment = changeFragment(mCurrentFragment, baseFragment, R.id.frame_content, false, false);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
