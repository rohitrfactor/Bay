package com.example.garorasu.bay.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.garorasu.bay.Helper.PreferencesHelper;
import com.example.garorasu.bay.Model.Fare;
import com.example.garorasu.bay.R;


public class SetFareFragment extends DialogFragment {

    private EditText twoWheelerBaseFare,twoWheelerBaseHour,twoWheelerRatePerHour,
    fourWheelerBaseFare,fourWheelerBaseHour,fourWheelerRatePerHour;
    private OnSetFareFragmentInteractionListener mListener;

    public SetFareFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SetFareFragment newInstance() {
        SetFareFragment fragment = new SetFareFragment();
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
        View view = inflater.inflate(R.layout.fragment_set_fare, container, false);
        twoWheelerBaseFare = (EditText) view.findViewById(R.id.edit_two_wheeler_base_fare);
        twoWheelerBaseHour = (EditText) view.findViewById(R.id.edit_two_wheeler_base_hour);
        twoWheelerRatePerHour = (EditText) view.findViewById(R.id.edit_two_wheeler_rate_per_hour);

        fourWheelerBaseFare = (EditText) view.findViewById(R.id.edit_four_wheeler_base_fare);
        fourWheelerBaseHour = (EditText) view.findViewById(R.id.edit_four_wheeler_base_hour);
        fourWheelerRatePerHour = (EditText) view.findViewById(R.id.edit_four_wheeler_rate_per_hour);

        Button reset = (Button) view.findViewById(R.id.button_reset_rate);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetValues();
            }
        });
        Button setRate = (Button) view.findViewById(R.id.button_set_rate);
        setRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fare fare = new Fare(Integer.parseInt(twoWheelerBaseFare.getText().toString()),
                        Integer.parseInt(twoWheelerBaseHour.getText().toString()),
                        Integer.parseInt(twoWheelerRatePerHour.getText().toString()),
                        Integer.parseInt(fourWheelerBaseFare.getText().toString()),
                        Integer.parseInt(fourWheelerBaseHour.getText().toString()),
                        Integer.parseInt(fourWheelerRatePerHour.getText().toString()));
                PreferencesHelper.writeToPreferences(getContext(),fare);
                dismiss();
                mListener.setDashboard();

            }
        });
        if(PreferencesHelper.isFareSet(getContext())){
            loadValues();
        }else{
            resetValues();
        }



        setCancelable(false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSetFareFragmentInteractionListener) {
            mListener = (OnSetFareFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSetFareFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSetFareFragmentInteractionListener {
        // TODO: Update argument type and name
        void setDashboard();
    }

    public void loadValues(){
        if(PreferencesHelper.isFareSet(getContext())){
            Fare fare = PreferencesHelper.getFare(getContext());
            twoWheelerBaseFare.setText(String.valueOf(fare.getTwoWheelerBaseFare()));
            twoWheelerBaseHour.setText(String.valueOf(fare.getTwoWheelerBaseHour()));
            twoWheelerRatePerHour.setText(String.valueOf(fare.getTwoWheelerRatePerHour()));

            fourWheelerBaseFare.setText(String.valueOf(fare.getFourWheelerBaseFare()));
            fourWheelerBaseHour.setText(String.valueOf(fare.getFourWheelerBaseHour()));
            fourWheelerRatePerHour.setText(String.valueOf(fare.getFourWheelerRatePerHour()));
        }
    }
    public void resetValues(){
        twoWheelerBaseFare.setText("20");
        twoWheelerBaseHour.setText("1");
        twoWheelerRatePerHour.setText("10");

        fourWheelerBaseFare.setText("40");
        fourWheelerBaseHour.setText("1");
        fourWheelerRatePerHour.setText("20");
    }
}
