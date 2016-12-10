package com.example.garorasu.bay.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.garorasu.bay.Helper.FeeCalculator;
import com.example.garorasu.bay.Helper.PreferencesHelper;
import com.example.garorasu.bay.Model.Fare;
import com.example.garorasu.bay.R;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.Persistance.DbHelper;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class OutFragment extends Fragment {

    private submitButtonFragmentListener mListener;

    private DbHelper database;
    private AutoCompleteTextView vehicleid;
    private ArrayList<String> parkedVehicles;
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
        vehicleid = (AutoCompleteTextView) view.findViewById(R.id.out_vehicleNo);
        loadAdapter();
        Button submitButton = (Button) view.findViewById(R.id.out_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.hideKeyboard();
                vehicleOut(vehicleid.getText().toString());
            }
        });
        return view;
    }

    public void vehicleOut(String v){
        DbHelper database = DbHelper.getInstance(getContext());
        List<Vehicle> result = database.getVehicleByVidForExit(v);
        if(result.size()==1){
            Vehicle vehicle = result.get(0);
            vehicleOutConfirmationDialog(vehicle);
            //mListener.setDashboard();
        }
        else if(result.size()>1){
            mulitpleVehicleFound(v);
        }
        else
        {
            vehicleNotFoundDialog(v);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }
    public interface submitButtonFragmentListener{
        void setDashboard();
        void hideKeyboard();
        void showVehicleOutPaymentDialog(Vehicle vehicle);
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
    public void loadAdapter(){
        database = DbHelper.getInstance(getContext());
        ArrayList<String> parkedVehicles = new ArrayList<String>();
        for(Vehicle x: database.getParkedVehicles()){
            parkedVehicles.add(x.getVid());
        }
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(getContext(), R.layout.number_plate_card,R.id.text_number_plate, parkedVehicles);
        vehicleid.setAdapter(adapter);
    }
    public void vehicleOutConfirmationDialog(Vehicle v){
        final Vehicle vehicle = v;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getResources().getString(R.string.vehicle_out_confirmation)+" "+vehicle.getVid())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Date date = Calendar.getInstance().getTime();
                        Fare f = PreferencesHelper.getFare(getContext());
                        FeeCalculator feeCalculator = new FeeCalculator();
                        int vehicleFee = feeCalculator.vehicleFee(f,vehicle,date);
                        vehicle.vehicleOut(date,vehicleFee,false);
                        database.exitVehicle(vehicle);
                        mListener.setDashboard();
                        mListener.showVehicleOutPaymentDialog(vehicle);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create();
        builder.show();
    }
    public void vehicleNotFoundDialog(String v){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(v+" "+getResources().getString(R.string.vehicle_not_found))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.setDashboard();
                    }
                });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }
    public void mulitpleVehicleFound(String v){
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getResources().getString(R.string.multiple_vehicle)+" "+v)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mListener.setDashboard();
                        }
                    });
            builder.setCancelable(false);
            builder.create();
            builder.show();
    }
}
