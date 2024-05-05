package com.example.e_voting.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_voting.Adapters.PostAdapter;
import com.example.e_voting.Classes.Post;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NewsTab extends Fragment {

    RecyclerView rvPostsNT;
    PostAdapter myAdapter;

    public NewsTab() {
        // Required empty public constructor
    }

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

        View view =  inflater.inflate(R.layout.fragment_news_tab, container, false);

        rvPostsNT = view.findViewById(R.id.rvPostsNT);

        rvPostsNT.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPostsNT.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().getReference().child("Posts");

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();


        myAdapter = new PostAdapter(options, getContext());
        rvPostsNT.setAdapter(myAdapter);


        return view;
    }
}