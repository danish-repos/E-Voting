package com.example.e_voting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class VoteTab extends Fragment {

    TextView tvNumberCandidates;
    SearchView scPlaceVote;
    RecyclerView rvCandidatesVT;
    Button btnSearchPlaceVote;

    CandidateAdapterVote myAdapter;

    public VoteTab() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vote_tab, container, false);

        tvNumberCandidates = view.findViewById(R.id.tvNumberCandidates);
        scPlaceVote = view.findViewById(R.id.scPlaceVote);
        rvCandidatesVT = view.findViewById(R.id.rvCandidatesVT);

        rvCandidatesVT.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCandidatesVT.setHasFixedSize(true);

        btnSearchPlaceVote = view.findViewById(R.id.btnSearchPlaceVote);

        Query query = FirebaseDatabase.getInstance().getReference().child("Candidates");

        FirebaseRecyclerOptions<Candidate> options =
                new FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate.class).build();


        myAdapter = new CandidateAdapterVote(options, getContext());
        rvCandidatesVT.setAdapter(myAdapter);


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
