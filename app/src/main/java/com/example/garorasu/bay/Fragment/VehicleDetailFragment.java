package com.example.garorasu.bay.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garorasu.bay.Helper.FeeCalculator;
import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.Persistance.DbHelper;
import com.example.garorasu.bay.R;


public class VehicleDetailFragment extends Fragment {

    private static final String ARG_VEHICLE_ID = "uid";

    private int mUID;

    //private OnFragmentInteractionListener mListener;

    public VehicleDetailFragment() {
    }


    public static VehicleDetailFragment newInstance(int uid) {
        VehicleDetailFragment fragment = new VehicleDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VEHICLE_ID, uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUID = getArguments().getInt(ARG_VEHICLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_detail, container, false);
        DbHelper dbHelper = DbHelper.getInstance(getContext());
        Vehicle vehicle = dbHelper.getVehicleByUid(mUID);
        TextView vid = (TextView) view.findViewById(R.id.text_number_plate);
        TextView inTime = (TextView) view.findViewById(R.id.text_detail_inTime);
        TextView outTime = (TextView) view.findViewById(R.id.text_detail_outTime);
        TextView type = (TextView) view.findViewById(R.id.text_detail_type);
        //TextView duration = (TextView) view.findViewById(R.id.text_detail_duration);
        TextView fee = (TextView) view.findViewById(R.id.text_detail_fee);
        vid.setText(vehicle.getVid());
        inTime.setText(vehicle.getInTime());
        outTime.setText(vehicle.getOutTime());
        type.setText(vehicle.getType());
        //duration.setText(String.valueOf(
        //        new FeeCalculator().hourBetween(vehicle.getinDate(),vehicle.getOutDate())));
        fee.setText(vehicle.getFee());
        return view;
    }
    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    */
}
