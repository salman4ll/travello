package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MounthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MounthFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recycleViewLayoutManager;
    ArrayList<DestinationModels> data;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MounthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MounthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MounthFragment newInstance(String param1, String param2) {
        MounthFragment fragment = new MounthFragment();
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


        View view = inflater.inflate(R.layout.fragment_mounth, container, false);
        recyclerView = view.findViewById(R.id.recyc);
        recyclerView.setHasFixedSize(true);

        recycleViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recycleViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        data = new ArrayList<>();


        DatabaseDestinationHandler db = new DatabaseDestinationHandler(getContext());

        List<DestinationModels> listData = db.getByCategoryRecord("mountain");


        for (int i = 0 ; i < listData.size() ; i ++ ) {
            data.add(listData.get(i));
            System.out.println("INDEX : "+i);
        }


        recyclerViewAdapter = new AdapterRecycleView(getContext(), data);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}