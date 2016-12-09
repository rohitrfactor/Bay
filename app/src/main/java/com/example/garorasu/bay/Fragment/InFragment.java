package com.example.garorasu.bay.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.Persistance.DbHelper;
import com.example.garorasu.bay.R;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;


public class InFragment extends Fragment {
    private EditText vehicleid;
    private submitButtonFragmentListener mListener;

    public InFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static InFragment newInstance() {
        InFragment fragment = new InFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in, container, false);
        vehicleid = (EditText) view.findViewById(R.id.in_vehicleNo);
        Button submitButton = (Button) view.findViewById(R.id.in_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.hideKeyboard();
                checkDuplicate(vehicleid.getText().toString());
            }
        });
        return view;
    }

    public void vehicleIn(String v,int type){
        DbHelper database = DbHelper.getInstance(getContext());
        Date date = Calendar.getInstance().getTime();
        Vehicle vehicle = new Vehicle();
        vehicle.vehicleIn(v,date,type,false);
        database.addVehicle(vehicle);
        mListener.setDashboard();
    }
    public void checkDuplicate(String v){
        DbHelper database = DbHelper.getInstance(getContext());
        if(database.getParkedVehicleByVid(v).size()>0){
            alreadyParkedDialog(v);
        }else{
            mListener.showVehicleTypeDialog(v);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }
    public interface submitButtonFragmentListener{
        void setDashboard();
        void hideKeyboard();
        void showVehicleTypeDialog(String vid);
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
    public void alreadyParkedDialog(String v){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(v+" "+getResources().getString(R.string.already_parked))
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
