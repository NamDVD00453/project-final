package com.vinamine.mc;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vinamine.mc.config.AsyncDownload;
import com.vinamine.mc.config.Config;
import com.vinamine.mc.config.DownloadBitmapInterface;
import com.vinamine.mc.data.CustomPagerAdapter;
import com.vinamine.mc.rest.RetroController;
import com.vinamine.mc.rest.Voucher;
import com.vinamine.mc.util.VoucherAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DownloadBitmapInterface {
    Intent intent;
    NavigationView navigationView;
    View headerView;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    final Context context = this;
    List<Voucher> listVouchers;
    RetroController retroController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        retroController = new RetroController();
        listVouchers = retroController.getAllVouchers();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        ImageView tvThumb = findViewById(R.id.tvThumb);
//        tvThumb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                watchYoutubeVideo(MainActivity.this, "vtTUKLE_SWE");
//            }
//        });
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);

        intent = this.getIntent();

        if (Config.ISLOGGED){
            setContent();
        } else {
            TextView tvUsername = headerView.findViewById(R.id.tvUsername);
            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }


        RecyclerView rvVoucher = findViewById(R.id.rvVoucher);
        VoucherAdapter voucherAdapter = new VoucherAdapter(this, listVouchers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvVoucher.setLayoutManager(layoutManager);
//        rvVoucher.setItemAnimator(new DefaultItemAnimator());
        rvVoucher.setAdapter(voucherAdapter);
        System.out.println("Item count: " + voucherAdapter.getItemCount());

//        final ViewPager viewPager = findViewById(R.id.vpTop);
//        CustomPagerAdapter adapter = new CustomPagerAdapter(this);
//        viewPager.setAdapter(adapter);
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == 3) {
//                    currentPage = 0;
//                }
//                viewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//        timer = new Timer(); // This will create a new Thread
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, DELAY_MS, PERIOD_MS);

        final SwipeRefreshLayout swLayout = findViewById(R.id.swLayout);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(context, "Homepage refresh!", Toast.LENGTH_SHORT).show();
                listVouchers = retroController.getAllVouchers();
                RecyclerView rvVoucher = findViewById(R.id.rvVoucher);
                VoucherAdapter voucherAdapter = new VoucherAdapter(getApplicationContext(), listVouchers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvVoucher.setLayoutManager(layoutManager);
//        rvVoucher.setItemAnimator(new DefaultItemAnimator());
                rvVoucher.setAdapter(voucherAdapter);
                System.out.println("Item count: " + voucherAdapter.getItemCount());
                swLayout.setRefreshing(false);
            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//            Intent intent = new Intent(MainActivity.this, ServerChat.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void processFinish(Bitmap output) {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(output);
    }

    private void setContent(){

        AsyncDownload asyncDownload = new AsyncDownload();
        asyncDownload.delegate = this;
        asyncDownload.execute("https://i.imgur.com/SsQx3LA.png");



        TextView tvUsername = headerView.findViewById(R.id.tvUsername);
        tvUsername.setText("0982676254");

        TextView tvEmail = headerView.findViewById(R.id.tvEmail);
        tvEmail.setText("dnzakmin@gmail.com");

    }
}
