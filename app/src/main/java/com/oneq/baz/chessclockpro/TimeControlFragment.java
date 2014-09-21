package com.oneq.baz.chessclockpro;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by shahbaz.sheikh on 9/1/14.
 */
public class TimeControlFragment extends DialogFragment {

    private RadioGroup radioGroup;
    private int bullet_ID;
    private int blitz_ID;
    private int game30_ID;
    private int game60_ID;
    private int selected_ID;
    OnTimeControlSelectedListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timecontrol, container);

        //local vars
        Button positiveButton = (Button) view.findViewById(R.id.pos);
        Button negativeButton = (Button) view.findViewById(R.id.neg);
        getDialog().setTitle("Time Control");
        radioGroup = (RadioGroup) view.findViewById(R.id.TimeControlSettings);
        bullet_ID = R.id.bulletButton;
        blitz_ID = R.id.blitzButton;
        game30_ID = R.id.game30Button;
        game60_ID = R.id.game60Button;

        //dismiss dialog if cancel is hit
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //after selecting new time control, call the interface to return data to activity
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_ID = radioGroup.getCheckedRadioButtonId();
                if(selected_ID == bullet_ID) {
                    returnNewTimeControl(60000);
                }
                else if(selected_ID == blitz_ID) {
                    returnNewTimeControl(300000);
                }
                else if(selected_ID == game30_ID) {
                    returnNewTimeControl(1800000);
                }
                else if(selected_ID == game60_ID) {
                    returnNewTimeControl(3600000);
                }
                else
                    Toast.makeText(getActivity(), "Please select a new time control, or press Cancel", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    //interface to communicate with calling activity
    public interface OnTimeControlSelectedListener{
        public void onTimeControlSelected(long timeInMilli);
    }

    //uses interface to pass new value back to calling activity
    private void returnNewTimeControl(int timeInMilli) {
        mCallback.onTimeControlSelected(timeInMilli);
        getDialog().dismiss();
    }

    //attaches interface to calling activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnTimeControlSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}
