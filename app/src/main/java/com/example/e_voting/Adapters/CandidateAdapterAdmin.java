package com.example.e_voting.Adapters;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.Classes.Candidate;
import com.example.e_voting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class CandidateAdapterAdmin extends FirebaseRecyclerAdapter<Candidate, CandidateAdapterAdmin.ViewHolder> {
    Context context;

    public CandidateAdapterAdmin(@NonNull FirebaseRecyclerOptions<Candidate> options, Context c) {
        super(options);
        context = c;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i, @NonNull Candidate candidate) {
        viewHolder.tvCandidateParty.setText(candidate.getPartyName());
        viewHolder.tvCandidateName.setText(candidate.getName());
        viewHolder.tvNumberOfVotes.setText(candidate.getTotalVotes()+"");


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Confirmation")
                        .setTitle("Do you really want to delete this candidate?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getRef(i).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                        
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        
                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return false;
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_candidate_view,parent,false);
        return new CandidateAdapterAdmin.ViewHolder(v);
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
