package com.example.e_voting.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.e_voting.Fragments.HomeTab;
import com.example.e_voting.Fragments.NewsTab;
import com.example.e_voting.Fragments.ProfileTab;
import com.example.e_voting.Fragments.VoteTab;


public class MainTabAdapter extends FragmentStateAdapter {

    public MainTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
            {
                return new HomeTab();
            }
            case 1:
            {
                return new VoteTab();
            }
            case 2:
            {
                return new NewsTab();
            }
            case 3:
            {
                return new ProfileTab();
            }
            default:
            {
                return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
