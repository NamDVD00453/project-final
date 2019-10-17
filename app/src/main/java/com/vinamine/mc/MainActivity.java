package com.vinamine.mc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.vinamine.mc.config.AvatarDownload;
import com.vinamine.mc.config.Config;
import com.vinamine.mc.config.ItemLoad;
import com.vinamine.mc.rest.RetroController;
import com.vinamine.mc.rest.Voucher;
import com.vinamine.mc.rest.VoucherForView;
import com.vinamine.mc.rest.login.LoginResponse;
import com.vinamine.mc.util.Util;
import com.vinamine.mc.util.VoucherForViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static VoucherForViewAdapter voucherForViewAdapter;
    Intent intent;
    NavigationView navigationView;
    View headerView;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    private static Context context;
    List<Voucher> listVouchers;
    RetroController retroController;

    public static List<VoucherForView> voucherForViewList = new ArrayList<>();
    public static RecyclerView rvVoucher;

    public static void loadAllRv(List<Voucher> listVouchers) {

        for (int i = listVouchers.size()-1; i >= 0; i--) {
            Voucher v = listVouchers.get(i);
            ItemLoad itemLoad = new ItemLoad(v);
            itemLoad.execute();
        }
    }

    public static Context getAppContext(){
        return MainActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        voucherForViewList.clear();
        Util.mainContext = getApplicationContext();
        retroController = new RetroController();
        retroController.getAllVouchers();
        System.out.println("Util: Main context null? " + Util.mainContext == null);
        MainActivity.context = getApplicationContext();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
        }

        retroController = new RetroController();
        listVouchers = retroController.listVouchers;

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

        if (Config.ISLOGGED) {
            LoginResponse loginResponse = (LoginResponse) intent.getSerializableExtra("loginUser");
            new AvatarDownload((ImageView) headerView.findViewById(R.id.imageView))
                    .execute(loginResponse.getData().getAccount().getAvatar());
            TextView tvUsername = headerView.findViewById(R.id.tvUsername);
            tvUsername.setText(loginResponse.getData().getAccount().getFullName());
        } else {
            TextView tvUsername = headerView.findViewById(R.id.tvUsername);
            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            });
        }
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
        rvVoucher = findViewById(R.id.rvVoucher);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvVoucher.setLayoutManager(layoutManager);
        rvVoucher.setAdapter(voucherForViewAdapter);



        final SwipeRefreshLayout swLayout = findViewById(R.id.swLayout);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(context, "Homepage refresh!", Toast.LENGTH_SHORT).show();
                voucherForViewList.clear();

                retroController.getAllVouchers();
                swLayout.setRefreshing(false);
            }
        });
    }

    public static void reloadRv(Context c){
        System.out.println("List voucher for view count: " + voucherForViewList.size());
        System.out.println(c == null);
        voucherForViewAdapter = new VoucherForViewAdapter(c, voucherForViewList);
        rvVoucher.setAdapter(voucherForViewAdapter);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            System.exit(1);
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
        if (id == R.id.nav_logout){
            if (Config.ISLOGGED){
                Config.ISLOGGED = false;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                startActivity(getIntent());
            }
        } else if (id == R.id.nav_order) {
            if (Config.ISLOGGED) {
                Intent intent = new Intent(getAppContext(), OrderList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getAppContext().startActivity(intent);
            } else {
                Snackbar.make(headerView, "You need to login!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else {
            Snackbar.make(headerView, "Under construction!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void handlQuit(MenuItem item) {
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

//    @Override
//    public void processFinish(Bitmap output) {
//        ImageView imageView = findViewById(R.id.imageView);
//        imageView.setImageBitmap(output);
//    }

//    private void setContent() {
//        TextView tvUsername = headerView.findViewById(R.id.tvUsername);
//        tvUsername.setText("0982676254");
//
//        TextView tvEmail = headerView.findViewById(R.id.tvEmail);
//        tvEmail.setText("dnzakmin@gmail.com");
//    }
}
