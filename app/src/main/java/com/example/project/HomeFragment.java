package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private TextView textName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<DestinationModels> data;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        data = new ArrayList<>();
        DatabaseDestinationHandler dbDest = new DatabaseDestinationHandler(getContext());

        List<DestinationModels> listData = dbDest.getAllRecord();


        for (int i = 0 ; i < listData.size() ; i ++ ) {
            data.add(listData.get(i));
        }


        DatabaseUserHandler db = new DatabaseUserHandler(getActivity().getBaseContext());

        UserModels user = db.getUser();

        textName = view.findViewById(R.id.hei_name);
        textName.setText("hi, "+user.getName());

        if (data.size()>3){
            ImageView img1 = view.findViewById(R.id.image_destination);
            String imageUrl = data.get(0).getImage();
            Picasso.get().load(imageUrl).resize(180, 220 ).placeholder(R.mipmap.ic_launcher).into(img1);

            TextView name1 = view.findViewById(R.id.nameDest);
            name1.setText(data.get(0).getName());

            TextView loc1 = view.findViewById(R.id.lokasi);
            loc1.setText(data.get(0).getLocation());

            ImageView img2 = view.findViewById(R.id.image_destination2);
            String imageUr2 = data.get(1).getImage();
            Picasso.get().load(imageUr2).resize(180, 220 ).placeholder(R.mipmap.ic_launcher).into(img2);

            TextView name2 = view.findViewById(R.id.nameDest2);
            name2.setText(data.get(1).getName());

            TextView loc2 = view.findViewById(R.id.lokasi2);
            loc2.setText(data.get(1).getLocation());

            ImageView img3 = view.findViewById(R.id.image_destination3);
            String imageUrl3 = data.get(2).getImage();
            Picasso.get().load(imageUrl3).resize(180, 220 ).placeholder(R.mipmap.ic_launcher).into(img3);

            TextView name3 = view.findViewById(R.id.nameDest3);
            name3.setText(data.get(2).getName());

            TextView loc3 = view.findViewById(R.id.lokasi3);
            loc3.setText(data.get(2).getLocation());
        }



        return view;
    }
}