package com.example.garorasu.bay.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garorasu.bay.MainActivity;
import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.Persistance.DbHelper;
import com.example.garorasu.bay.R;

import java.util.ArrayList;
import java.util.List;


public class Dashboard extends Fragment {
    private TextView parkedCars,allCars;
    private clickFragmentListener mListener;

    public Dashboard() {
        // Required empty public constructor
    }


    public static Dashboard newInstance() {
        Dashboard fragment = new Dashboard();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // Inflate the layout for this fragment
        FloatingActionButton in = (FloatingActionButton) view.findViewById(R.id.fab_in);
        FloatingActionButton out = (FloatingActionButton) view.findViewById(R.id.fab_out);
        parkedCars = (TextView) view.findViewById(R.id.parked_cars);
        allCars = (TextView) view.findViewById(R.id.all_cars);
        DbHelper database = DbHelper.getInstance(getContext());
        List<Vehicle> x = database.getParkedVehicles();
        List<Vehicle> y = database.getAllVehicles();
        parkedCars.setText(String.valueOf(x.size()));
        allCars.setText(String.valueOf(y.size()));
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "In Button Pressed", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                // Let's first dynamically add a fragment into a frame container
                mListener.setInFragment();

            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Out Button Pressed", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                mListener.setOutFragment();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }
    public interface clickFragmentListener{
        void setInFragment();
        void setOutFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof clickFragmentListener) {
            mListener = (clickFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement inButtonFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
