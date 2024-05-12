package com.example.e_voting.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Activities.VoteCandidateActivity;


import com.example.e_voting.Classes.Candidate;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CandidateAdapterVote extends FirebaseRecyclerAdapter<Candidate, CandidateAdapterVote.ViewHolder> {
    Context context;

    public CandidateAdapterVote(@NonNull FirebaseRecyclerOptions options, Context c) {
        super(options);
        context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_candidate_view,parent,false);
        return new CandidateAdapterVote.ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Candidate candidate) {
        viewHolder.tvCandidateParty.setText(candidate.getPartyName());
        viewHolder.tvCandidateName.setText(candidate.getName());
        viewHolder.tvNumberOfVotes.setText(candidate.getTotalVotes()+"");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VoteCandidateActivity.class);
                intent.putExtra("Name", candidate.getName());
                intent.putExtra("PartyName", candidate.getPartyName());
                intent.putExtra("Type", candidate.getType());
                context.startActivity(intent);

            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvCandidateName, tvCandidateParty, tvNumberOfVotes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCandidateName = itemView.findViewById(R.id.tvCandidateName);
            tvCandidateParty = itemView.findViewById(R.id.tvCandidateParty);
            tvNumberOfVotes = itemView.findViewById(R.id.tvNumberOfVotes);
        }
    }
}
