package com.example.e_voting;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomeTabAdapter extends FragmentStateAdapter {

    public HomeTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
            {
                return new OnGoingTab();
            }
            case 1:
            {
                return new UpComingTab();
            }
            default:
            {
                return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
