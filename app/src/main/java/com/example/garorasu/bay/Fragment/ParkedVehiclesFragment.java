package com.example.garorasu.bay.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.garorasu.bay.Adapter.NumberPlateAdapter;
import com.example.garorasu.bay.Persistance.DbHelper;
import com.example.garorasu.bay.R;


public class ParkedVehiclesFragment extends Fragment{

    private listParkedVehiclesFragmentInteractionListener mListener;
    private RecyclerView mNumberPlateRecycler;
    private NumberPlateAdapter numberPlateAdapter;
    private DbHelper database;
    private LinearLayoutManager mLayoutManager;

    public ParkedVehiclesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ParkedVehiclesFragment newInstance() {
        ParkedVehiclesFragment fragment = new ParkedVehiclesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parked_vehicles, container, false);
        mNumberPlateRecycler = (RecyclerView) view.findViewById(R.id.recycler_number_plate_parked);
        database = DbHelper.getInstance(getContext());
        numberPlateAdapter = new NumberPlateAdapter(database.getParkedVehicles());
        mNumberPlateRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mNumberPlateRecycler.setLayoutManager(mLayoutManager);
        mNumberPlateRecycler.setAdapter(numberPlateAdapter);
        numberPlateAdapter.setOnItemClickListener(new NumberPlateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.setVehicleDetailFragment(position);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof listParkedVehiclesFragmentInteractionListener) {
            mListener = (listParkedVehiclesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement listParkedVehiclesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface listParkedVehiclesFragmentInteractionListener {
        // TODO: Update argument type and name
        void setVehicleDetailFragment(int uid);
    }
}
