package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    FragmentManager fragmentManager;
    Fragment homeFrag,voteFrag, newsFrag;

    TabLayout tabLayout, tabLayoutHome;
    ViewPager2 viewPager, viewPagerHome;

    SearchView scPlace;
    Button btnSearchPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        tabLayout.setScrollPosition(0, 0, true);
        viewPager.setUserInputEnabled(false);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));

                if(position == 0)
                {
                    // error in this tab
                    //HomeTab();
                }

            }
        });




    }

    private void init(){
        fragmentManager = getSupportFragmentManager();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        viewPager.setAdapter(new MainTabAdapter(this));

    }

    private void HomeTab(){

        View view = viewPager.getChildAt(0);

        scPlace = view.findViewById(R.id.scPlace);
        btnSearchPlace = view.findViewById(R.id.btnSearchPlace);
        tabLayoutHome = view.findViewById(R.id.tabLayoutHome);
        viewPagerHome = view.findViewById(R.id.viewPagerHome);
        viewPagerHome.setAdapter(new HomeTabAdapter(this));

        scPlace.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnSearchPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

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


    }



}