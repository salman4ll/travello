package com.example.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    TextView etName;
    TextView etEmail;
    Button etLogout;

    EditText inName;
    EditText inEmail;
    Button btnEditName;
    Button btnEditEmail;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        etName = view.findViewById(R.id.name_profile);
        etEmail = view.findViewById(R.id.email_profile);
        DatabaseUserHandler db = new DatabaseUserHandler(getActivity().getBaseContext());

        UserModels user = db.getUser();

        etName.setText("Name : "+ user.getName());
        etEmail.setText("Email : " + user.getEmail());

        etLogout = view.findViewById(R.id.logout);

        etLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenManager tokenManager = new TokenManager(getActivity().getBaseContext());
                tokenManager.deleteToken();
                db.deleteUser(user);

                Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //Update Name
        AlertDialog.Builder builderName = new AlertDialog.Builder(requireContext());
        builderName.setTitle("Update Name");
        builderName.setIcon(R.drawable.profile_button);
        inName = new EditText(getActivity().getBaseContext());
        builderName.setView(inName);
        TokenManager token = new TokenManager(requireContext());
        builderName.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = inName.getText().toString();
                MyUser users = new MyUser();
                user.setName(newName);
                users.updateUser(requireContext(),token.getToken(), user);
            }
        });

        builderName.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builderName.create();

        btnEditName = view.findViewById(R.id.change_name);
        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        //Update Email
        AlertDialog.Builder builderEmail = new AlertDialog.Builder(requireContext());
        builderEmail.setTitle("Update Email");
        builderEmail.setIcon(R.drawable.profile_button);
        inEmail = new EditText(getActivity().getBaseContext());
        builderEmail.setView(inEmail);
        builderEmail.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newEmail = inEmail.getText().toString();
                MyUser userUpdate = new MyUser();
                user.setEmail(newEmail);
                userUpdate.updateUser(requireContext(),token.getToken(), user);
            }
        });

        builderEmail.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialogEmail = builderEmail.create();

        btnEditName = view.findViewById(R.id.change_email);
        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogEmail.show();
            }
        });

        return view;
    }
}