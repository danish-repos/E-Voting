package com.example.e_voting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class VoteTab extends Fragment {

    TextView tvNumberCandidates;
    SearchView scPlaceVote;
    RecyclerView rvCandidates;
    Button btnSearchPlaceVote;

    CandidateAdapter myAdapter;

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
        rvCandidates = view.findViewById(R.id.rvCandidates);

        rvCandidates.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCandidates.setHasFixedSize(true);

        btnSearchPlaceVote = view.findViewById(R.id.btnSearchPlaceVote);

        Query query = FirebaseDatabase.getInstance().getReference().child("Candidates");

        FirebaseRecyclerOptions<Candidate> options =
                new FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate.class).build();


        myAdapter = new CandidateAdapter(options, getContext());
        rvCandidates.setAdapter(myAdapter);


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
