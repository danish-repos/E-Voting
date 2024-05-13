package com.example.e_voting.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voting.Adapters.HomeTabAdapter;
import com.example.e_voting.R;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HomeTab extends Fragment {


    TextView tvCountdown;


    TabLayout tabLayoutHome;
    ViewPager2 viewPagerHome;

    private static final long ONE_SECOND = 1000;
    private static final long TARGET_DATE_MILLIS = getTargetDateMillis();

    public HomeTab() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);


        tabLayoutHome = view.findViewById(R.id.tabLayoutHome);

        viewPagerHome = view.findViewById(R.id.viewPagerHome);
        viewPagerHome.setAdapter(new HomeTabAdapter(requireActivity()));

        tvCountdown = view.findViewById(R.id.tvCountdown);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long remainingTimeMillis = TARGET_DATE_MILLIS - System.currentTimeMillis();
                if (remainingTimeMillis > 0) {
                    long days = TimeUnit.MILLISECONDS.toDays(remainingTimeMillis);
                    long hours = TimeUnit.MILLISECONDS.toHours(remainingTimeMillis) % 24;
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTimeMillis) % 60;
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTimeMillis) % 60;

                    String time = String.format("%d days, %d hours, %d minutes", days, hours, minutes);

                    tvCountdown.setText(time);
                } else {
                    timer.cancel();
                }

            }
        }, 0, ONE_SECOND);


//        scPlace.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

//        btnSearchPlace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(getContext(), btnSearchPlace);
//
//                popup.getMenuInflater().inflate(R.menu.dropdown_menu, popup.getMenu());
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//
//                        int itemId = item.getItemId();
//
//                        if (itemId == R.id.option1) {
//
//                            return true;
//                        } else if (itemId == R.id.option2) {
//
//                            return true;
//                        } else if (itemId == R.id.option3) {
//
//                            return true;
//                        } else {
//                            return false;
//                        }
//
//                    }
//                });
//
//                popup.show();
//            }
//
//        });

        tabLayoutHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerHome.setCurrentItem(tab.getPosition(), true);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPagerHome.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayoutHome.selectTab(tabLayoutHome.getTabAt(position));
            }
        });

        return view;
    }

    private static long getTargetDateMillis() {
        String targetDateStr = "2024-05-20"; // Set your target date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date targetDate = dateFormat.parse(targetDateStr);
            return targetDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}