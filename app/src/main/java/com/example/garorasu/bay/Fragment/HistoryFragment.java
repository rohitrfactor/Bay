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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HistoryFragment extends Fragment{

    private  historyFragmentInteractionListener mListener;
    private RecyclerView mNumberPlateRecycler;
    private NumberPlateAdapter numberPlateAdapter;
    private DbHelper database;
    private LinearLayoutManager mLayoutManager;
    private Date to,from;

    public HistoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mNumberPlateRecycler = (RecyclerView) view.findViewById(R.id.recycler_number_plate_history);
        database = DbHelper.getInstance(getContext());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            from = fmt.parse("2016-12-07 00:00:00.000");
            to = fmt.parse("2016-12-07 23:59:59.999");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        numberPlateAdapter = new NumberPlateAdapter(database.getVehiclesByInDate(from,to));
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
        if (context instanceof historyFragmentInteractionListener) {
            mListener = (historyFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement historyFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface historyFragmentInteractionListener {
        // TODO: Update argument type and name
        void setVehicleDetailFragment(int uid);
    }
}
