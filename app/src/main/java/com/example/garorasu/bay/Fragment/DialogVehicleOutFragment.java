package com.example.garorasu.bay.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.R;


public class DialogVehicleOutFragment extends DialogFragment {
    private  Vehicle vid;

    private OnDialogVehicleOutFragmentInteractionListener mListener;

    public DialogVehicleOutFragment(Vehicle vid) {
        this.vid = vid;
    }


    // TODO: Rename and change types and number of parameters
    public static DialogVehicleOutFragment newInstance(Vehicle vid) {
        DialogVehicleOutFragment fragment = new DialogVehicleOutFragment(vid);
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
        View rootView = inflater.inflate(R.layout.fragment_dialog_vehicle_out, container, false);
        TextView vehicleNumber = (TextView) rootView.findViewById(R.id.text_number_plate);
        TextView vehicleInTime = (TextView) rootView.findViewById(R.id.text_dialog_payment_vehicle_inTime);
        TextView vehicleOutTime = (TextView) rootView.findViewById(R.id.text_dialog_payment_vehicle_outTime);
        TextView vehicleType = (TextView) rootView.findViewById(R.id.text_dialog_payment_vehicle_type);
        TextView vehicleFee = (TextView) rootView.findViewById(R.id.text_dialog_payment_vehicle_fee);
        vehicleNumber.setText(vid.getVid());
        vehicleInTime.setText(vid.getInTime());
        vehicleOutTime.setText(vid.getOutTime());
        vehicleType.setText(vid.getType());
        vehicleFee.setText(vid.getFee());
        Button skip = (Button) rootView.findViewById(R.id.button_skip_payment_dialog);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mListener.setDashboard();
            }
        });

        Button print = (Button) rootView.findViewById(R.id.button_print_payment_dialog);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPrint(vid);
            }
        });
        setCancelable(false);
        return rootView;
    }

    public void onPrint(Vehicle vehicle) {
        if (mListener != null) {
            dismiss();
            mListener.setDashboard();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogVehicleOutFragmentInteractionListener) {
            mListener = (OnDialogVehicleOutFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogVehicleOutFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnDialogVehicleOutFragmentInteractionListener {
        // TODO: Update argument type and name
        //void print(Vehicle vehicle);
        void setDashboard();
    }
}
