package com.zk.sample.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.zk.baselibrary.app.BaseFra;
import com.zk.baselibrary.util.ClassUtil;
import com.zk.baselibrary.util.GsonUtil;
import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.NetUtil;
import com.zk.baselibrary.util.SystemUtil;
import com.zk.baselibrary.util.UIUtil;
import com.zk.sample.R;
import com.zk.sample.data.DataManager;
import com.zk.sample.databinding.ActivityMainBinding;
import com.zk.sample.ui.base.BaseActivity;
import com.zk.sample.ui.base.BaseFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final String CURRENT_PAGE = "CURRENT_FRAGMENT";
    private BaseFra mCurrentFragment;

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

        switchFragment(null);
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
        Class clazz = null;
        if (id == R.id.nav_home) {
            clazz = HomeFragment.class;
        } else if (id == R.id.nav_theme) {
            clazz = ThemeFragment.class;
        } else if (id == R.id.nav_me) {
            clazz = WebFragment.class;
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            SystemUtil.shareText(this, "https://github.com/zhaokai1033");
        } else if (id == R.id.nav_send) {
//            LogUtil.d(TAG, NetUtil.getWifiAddress(this));
//            NetUtil.showNetSetDialog(this);
            LogUtil.d(TAG, GsonUtil.toJson(DataManager.getSkin("black")));
        }

        switchFragment(clazz);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换选项
     *
     * @param clazz 指定页面的类文件
     */
    private void switchFragment(Class clazz) {
        BaseFra baseFragment;
        //默认为首页
        if (clazz == null) {
            clazz = HomeFragment.class;
        }
        //不重复切换
        if (mCurrentFragment != null && clazz.equals(mCurrentFragment.getClass())) {
            return;
        }
        baseFragment = ((BaseFragment) getSupportFragmentManager().findFragmentByTag(clazz.getName()));
        if (baseFragment == null) {
            baseFragment = ((BaseFragment) ClassUtil.createInstance(clazz));
        }
        mCurrentFragment = changeFragment(mCurrentFragment, baseFragment, R.id.frame_content, false, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT_PAGE, mCurrentFragment.getClass());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        switchFragment(((Class) savedInstanceState.getSerializable(CURRENT_PAGE)));
    }
}
