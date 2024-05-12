package com.example.e_voting.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voting.Activities.LogInActivity;
import com.example.e_voting.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;


public class ProfileTab extends Fragment {

    FragmentManager fragmentManager;

    TextView tvSaveChanges, tvEditProfile;
    EditText etFirstName, etLastName, etEmail, etOldPassword, etNewPassword;
    Button btnLogout;

    View view;

    Fragment editUserFrag;
    View editUserView;

    String firstName, lastName, email, oldPassword;
    Context context;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        init();

        fragmentManager.beginTransaction().hide(editUserFrag).commit();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Confirmation")
                        .setTitle("Do you want to logout?")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogoutUser();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();

            }
        });

        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().show(editUserFrag).commit();
                showOldInformation();

            }
        });

        tvSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String oldPassword = etOldPassword.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();

                if(firstName.isEmpty() || lastName.isEmpty() ||email.isEmpty() ||oldPassword.isEmpty() ||newPassword.isEmpty()){
                    Toast.makeText(context, "Can't leave anything empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot data : snapshot.getChildren())
                                {
                                    if (Boolean.TRUE.equals(data.child("isLogin").getValue(Boolean.class)))
                                    {
                                        String userKey = data.getKey();
                                        HashMap<String, Object> updateUser = new HashMap<>();
                                        updateUser.put("FirstName", firstName);
                                        updateUser.put("LastName", lastName);
                                        updateUser.put("Email", email);
                                        updateUser.put("Password", newPassword);

                                        if(!oldPassword.equals(data.child("Password").getValue(String.class)))
                                        {
                                            Toast.makeText(context, "Your old password is wrong!", Toast.LENGTH_SHORT).show();
                                            return;

                                        }

                                        FirebaseDatabase.getInstance().getReference().child("Users").child(userKey).updateChildren(updateUser)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
                                                        clearText();
                                                        fragmentManager.beginTransaction().hide(editUserFrag).commit();

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });


                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


            }
        });

        return view;
    }

    private void init(){

        fragmentManager = getChildFragmentManager();

        // Instantiate editUserFrag if it's null
        if (editUserFrag == null) {
            editUserFrag = new EditUserDetailsFragment();
        }

        editUserFrag = fragmentManager.findFragmentById(R.id.editUserFrag);

        editUserView = editUserFrag.requireView();


        tvSaveChanges = view.findViewById(R.id.tvSaveChanges);
        tvEditProfile = view.findViewById(R.id.tvEditProfile);

        etFirstName = editUserView.findViewById(R.id.etFirstNameProfile);
        etLastName = editUserView.findViewById(R.id.etLastNameProfile);
        etEmail = editUserView.findViewById(R.id.etEmailProfile);
        etOldPassword = editUserView.findViewById(R.id.etOldPasswordProfile);
        etNewPassword = editUserView.findViewById(R.id.etNewPasswordProfile);

        btnLogout = view.findViewById(R.id.btnLogout_Profile);
        context = getContext();
    }


    private void showOldInformation() {
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        boolean found = false;
                        for (DataSnapshot data : snapshot.getChildren()) {
                            if (Boolean.TRUE.equals(data.child("isLogin").getValue(Boolean.class)))
                            {
                                firstName = data.child("FirstName").getValue(String.class);
                                lastName = data.child("LastName").getValue(String.class);
                                email = data.child("Email").getValue(String.class);

                                etFirstName.setText(firstName);
                                etLastName.setText(lastName);
                                etEmail.setText(email);

                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void LogoutUser(){
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot data : snapshot.getChildren())
                        {
                            if (Boolean.TRUE.equals(data.child("isLogin").getValue(Boolean.class)))
                            {
                                String userKey = data.getKey();
                                HashMap<String, Object> updateLoginStatus = new HashMap<>();
                                updateLoginStatus.put("isLogin", false);

                                FirebaseDatabase.getInstance().getReference().child("Users").child(userKey).updateChildren(updateLoginStatus)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                startActivity(new Intent(getActivity(), LogInActivity.class));
                                                getActivity().finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void clearText(){
        etFirstName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etOldPassword.setText("");
        etNewPassword.setText("");
    }
}

