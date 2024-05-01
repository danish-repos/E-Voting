package com.example.e_voting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ProfileTab extends Fragment {

    TextView tvSave_Changes, tvEdit_Profile;
    EditText etFirstName, etLastName, etEmail, etOldPassword, etNewPassword;
    Button btnLogout;

    View view;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        return view;
    }

    private void init(){
        tvSave_Changes = view.findViewById(R.id.tvSave_Changes);
        tvEdit_Profile = view.findViewById(R.id.tvEditProfile);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etOldPassword = view.findViewById(R.id.etOldPassword_Profile);
        etNewPassword = view.findViewById(R.id.etNewPassword_Profile);

        btnLogout = view.findViewById(R.id.btnLogout_Profile);
    }
}