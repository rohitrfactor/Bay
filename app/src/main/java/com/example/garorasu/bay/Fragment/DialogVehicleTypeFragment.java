package com.example.garorasu.bay.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.garorasu.bay.R;


public class DialogVehicleTypeFragment extends DialogFragment {
    private  String vid;

    private OnDialogVehicleTypeFragmentInteractionListener mListener;

    public DialogVehicleTypeFragment(String vid) {
        this.vid = vid;
    }


    // TODO: Rename and change types and number of parameters
    public static DialogVehicleTypeFragment newInstance(String vid) {
        DialogVehicleTypeFragment fragment = new DialogVehicleTypeFragment(vid);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dialog_vehicle_type, container, false);
        Button twoWheeler = (Button) rootView.findViewById(R.id.button_twoWheeler_type);
        Button fourWheeler = (Button) rootView.findViewById(R.id.button_fourWheeler_type);
        twoWheeler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onTypeSelected(2);
            }
        });
        fourWheeler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onTypeSelected(4);
            }
        });
        return rootView;
    }

    public void onTypeSelected(int type) {
        if (mListener != null) {
            dismiss();
            mListener.vehicleIn(vid,type);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogVehicleTypeFragmentInteractionListener) {
            mListener = (OnDialogVehicleTypeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogVehicleTypeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnDialogVehicleTypeFragmentInteractionListener {
        // TODO: Update argument type and name
        void vehicleIn(String vid,int type);
    }
}
