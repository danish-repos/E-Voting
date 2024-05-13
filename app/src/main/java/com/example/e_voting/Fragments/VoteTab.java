package com.example.e_voting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voting.Adapters.CandidateAdapterVote;
import com.example.e_voting.Classes.Candidate;
import com.example.e_voting.Classes.LinearLayoutManagerWrapper;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VoteTab extends Fragment {

    TextView tvNumberCandidates;
    SearchView scCandidate;
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
        scCandidate = view.findViewById(R.id.scCandidate);
        rvCandidatesVT = view.findViewById(R.id.rvCandidatesVT);


        rvCandidatesVT.setLayoutManager(new LinearLayoutManagerWrapper(getContext(),LinearLayoutManager.VERTICAL,false));
        rvCandidatesVT.setHasFixedSize(true);

        FirebaseDatabase.getInstance().getReference().child("Candidates")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tvNumberCandidates.setText(snapshot.getChildrenCount()+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btnSearchPlaceVote = view.findViewById(R.id.btnSearchPlaceVote);

        Query query = FirebaseDatabase.getInstance().getReference().child("Candidates");
           

        FirebaseRecyclerOptions<Candidate> options =
                new FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate.class).build();


        myAdapter = new CandidateAdapterVote(options, getContext());
        rvCandidatesVT.setAdapter(myAdapter);

        btnSearchPlaceVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), btnSearchPlaceVote);

                popup.getMenuInflater().inflate(R.menu.types_of_votes, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int itemId = item.getItemId();

                        if (itemId == R.id.option1) {

                            Query query = FirebaseDatabase.getInstance().getReference()
                                    .child("Candidates")
                                    .orderByChild("type")
                                    .equalTo("National Assembly");

                            FirebaseRecyclerOptions<Candidate> options =
                                    new FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate.class).build();

                            myAdapter.updateOptions(options);
                            myAdapter.startListening();
                            return true;

                        } else if (itemId == R.id.option2) {

                            Query query = FirebaseDatabase.getInstance().getReference()
                                    .child("Candidates")
                                    .orderByChild("type")
                                    .equalTo("Provincial Assembly");

                            FirebaseRecyclerOptions<Candidate> options =
                                    new FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate.class).build();

                            myAdapter.updateOptions(options);
                            myAdapter.startListening();
                            return true;

                        }else if(itemId == R.id.option3){

                            Query query = FirebaseDatabase.getInstance().getReference()
                                    .child("Candidates")
                                    .orderByChild("type")
                                    .equalTo("Local body");

                            FirebaseRecyclerOptions<Candidate> options =
                                    new FirebaseRecyclerOptions.Builder<Candidate>().setQuery(query, Candidate.class).build();

                            myAdapter.updateOptions(options);
                            myAdapter.startListening();
                            return true;

                        }
                        else {
                            return false;
                        }

                    }
                });

                popup.show();
            }

        });

        scCandidate.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // When the text in the search view changes, update the query to filter candidates
                Query filteredQuery = FirebaseDatabase.getInstance().getReference()
                        .child("Candidates")
                        .orderByChild("name")
                        .startAt(newText)
                        .endAt(newText + "\uf8ff");

                FirebaseRecyclerOptions<Candidate> filteredOptions =
                        new FirebaseRecyclerOptions.Builder<Candidate>().setQuery(filteredQuery, Candidate.class).build();

                myAdapter.updateOptions(filteredOptions);
                myAdapter.startListening();

                return true;
            }
        });

        return view;
    }


}
