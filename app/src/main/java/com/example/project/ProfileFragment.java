package com.example.project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

    EditText inOldPassword, inNewPassword;
    Button btnEditName;

    Button btnEditPassword;

    Button btnEditEmail;

    private Dialog customDialog;
    private TextView txtInputEditUser;
    private Button btnOKEditUser, btnOKEditPassword;



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
        TokenManager JWT = new TokenManager(requireContext());
        String token = JWT.getToken();



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


        btnEditName = view.findViewById(R.id.change_name);
        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCustomDialogChangeName(user, token);
                customDialog.show();
            }
        });

        btnEditEmail = view.findViewById(R.id.change_email);
        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCustomDialogChangeEmail(user, token);
                customDialog.show();
            }
        });

        btnEditPassword = view.findViewById(R.id.change_password);
        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCustomDialogChangePassword(token);
                customDialog.show();
            }
        });

        return view;
    }



    private void initCustomDialogChangeName(UserModels user, String token) {
        customDialog = new Dialog(requireContext());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_edit_user);
        customDialog.setCancelable(true);

        TextView txtTitle = customDialog.findViewById(R.id.title_change_name);
        txtTitle.setText("change name");
        TextView textSubTitle = customDialog.findViewById(R.id.txt_SubTitle);
        textSubTitle.setText("New name : ");

        txtInputEditUser = customDialog.findViewById(R.id.txt_input_new_name);
        txtInputEditUser.setHint("name");
        btnOKEditUser = customDialog.findViewById(R.id.btn_input_new_name);
        btnOKEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtInputEditUser.getText().toString();
                user.setName(name);
                UpdateUserTask task = new UpdateUserTask(requireContext(), token, user);
                task.execute();
                customDialog.dismiss();
            }
        });

    }

    private void initCustomDialogChangeEmail(UserModels user, String token) {
        customDialog = new Dialog(requireContext());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_edit_user);
        customDialog.setCancelable(true);

        TextView txtTitle = customDialog.findViewById(R.id.title_change_name);
        txtTitle.setText("change email");
        TextView textSubTitle = customDialog.findViewById(R.id.txt_SubTitle);
        textSubTitle.setText("New email : ");

        txtInputEditUser = customDialog.findViewById(R.id.txt_input_new_name);
        txtInputEditUser.setHint("email");
        btnOKEditUser = customDialog.findViewById(R.id.btn_input_new_name);
        btnOKEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtInputEditUser.getText().toString();
                user.setEmail(email);

                UpdateUserTask task = new UpdateUserTask(requireContext(), token, user);
                task.execute();

                customDialog.dismiss();
            }
        });

    }

    private void initCustomDialogChangePassword(String token) {
        customDialog = new Dialog(requireContext());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_edit_password);
        customDialog.setCancelable(true);

        inOldPassword = customDialog.findViewById(R.id.txtOldPassword);
        inNewPassword = customDialog.findViewById(R.id.txtNewPassword);


        btnOKEditPassword = customDialog.findViewById(R.id.btnChangePassword);
        btnOKEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = inOldPassword.getText().toString();
                String newPassword = inNewPassword.getText().toString();

                UpdataPasswordTask task = new UpdataPasswordTask(requireContext(), token, oldPassword, newPassword);
                task.execute();

                customDialog.dismiss();
            }
        });

    }

}