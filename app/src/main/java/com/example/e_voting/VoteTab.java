package com.example.e_voting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

public class VoteTab extends Fragment {

    TextView tvNumberCandidates;
    SearchView scPlaceVote;
    RecyclerView rvCandidates;
    Button btnSearchPlaceVote;

    public VoteTab() {
        // Required empty public constructor
    }

public class VoteTab extends Fragment {

    public VoteTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vote_tab, container, false);

        tvNumberCandidates = view.findViewById(R.id.tvNumberCandidates);
        scPlaceVote = view.findViewById(R.id.scPlaceVote);
        rvCandidates = view.findViewById(R.id.rvCandidates);
        btnSearchPlaceVote = view.findViewById(R.id.btnSearchPlaceVote);


        btnSearchPlaceVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        scPlaceVote.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }
}