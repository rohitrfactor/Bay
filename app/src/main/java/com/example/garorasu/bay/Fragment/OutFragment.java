package com.example.garorasu.bay.Fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.garorasu.bay.R;


import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.Persistance.DbHelper;


import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class OutFragment extends Fragment {
    private EditText vehicleid;
    private submitButtonFragmentListener mListener;

    public OutFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static OutFragment newInstance() {
        OutFragment fragment = new OutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_out, containter, false);
        vehicleid = (EditText) view.findViewById(R.id.out_vehicleNo);
        Button submitButton = (Button) view.findViewById(R.id.out_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleOut(vehicleid.getText().toString());
            }
        });
        return view;
    }

    public void vehicleOut(String v){
        DbHelper database = DbHelper.getInstance(getContext());
        Date date = Calendar.getInstance().getTime();
        List<Vehicle> result = database.getVehicleByVidForExit(v);
        if(result.size()>0){
        Vehicle vehicle = result.get(0);
        vehicle.vehicleOut(date,50,false);
        database.exitVehicle(vehicle);
        }
        else
        {
            Snackbar.make(getView(), "Vehicle Not found", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
        mListener.setDashboard();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }
    public interface submitButtonFragmentListener{
        void setDashboard();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof submitButtonFragmentListener) {
            mListener = (submitButtonFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement submitButtonFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
