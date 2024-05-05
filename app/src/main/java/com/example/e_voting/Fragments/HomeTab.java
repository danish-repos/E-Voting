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
import android.widget.Toast;

import com.example.e_voting.Adapters.HomeTabAdapter;
import com.example.e_voting.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class HomeTab extends Fragment {

    SearchView scPlace;
    Button btnSearchPlace;


    TabLayout tabLayoutHome;
    ViewPager2 viewPagerHome;



    public HomeTab() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        scPlace = view.findViewById(R.id.scPlace);
        btnSearchPlace = view.findViewById(R.id.btnSearchPlace);
        tabLayoutHome = view.findViewById(R.id.tabLayoutHome);

        viewPagerHome = view.findViewById(R.id.viewPagerHome);
        viewPagerHome.setAdapter(new HomeTabAdapter(requireActivity()));

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
                PopupMenu popup = new PopupMenu(getContext(), btnSearchPlace);

                popup.getMenuInflater().inflate(R.menu.dropdown_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int itemId = item.getItemId();

                        if (itemId == R.id.option1) {
                            Toast.makeText(getContext(), "Option 1 selected", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.option2) {
                            Toast.makeText(getContext(), "Option 2 selected", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.option3) {
                            Toast.makeText(getContext(), "Option 3 selected", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            return false;
                        }

                    }
                });

                popup.show();
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

        return view;
    }
}