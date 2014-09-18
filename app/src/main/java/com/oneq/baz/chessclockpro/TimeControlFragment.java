package com.oneq.baz.chessclockpro;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by shahbaz.sheikh on 9/1/14.
 */
public class TimeControlFragment extends DialogFragment {

    private EditText mWhiteTime;
    private EditText mBlackTime;
    private Button positiveButton;
    private Button negativeButton;

    public TimeControlFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timecontrol, container);

        positiveButton = (Button) view.findViewById(R.id.pos);
        negativeButton = (Button) view.findViewById(R.id.neg);
        getDialog().setTitle("Time Control");



        return view;
    }



}
