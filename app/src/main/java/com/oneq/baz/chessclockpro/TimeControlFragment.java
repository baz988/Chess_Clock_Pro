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

    private Button positiveButton;
    private Button negativeButton;
    private RadioGroup radioGroup;
    private int bullet_ID;
    private int blitz_ID;
    private int game30_ID;
    private int game60_ID;
    private int selected_ID;
    private long mNewTimeControl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timecontrol, container);

        positiveButton = (Button) view.findViewById(R.id.pos);
        negativeButton = (Button) view.findViewById(R.id.neg);
        getDialog().setTitle("Time Control");

        radioGroup = (RadioGroup) view.findViewById(R.id.TimeControlSettings);
        /*
        bulletRadio = (RadioButton) view.findViewById(R.id.bulletButton);
        blitzRadio = (RadioButton) view.findViewById(R.id.blitzButton);
        game30Radio = (RadioButton) view.findViewById(R.id.game30Button);
        game60Radio = (RadioButton) view.findViewById(R.id.game60Button);
        */

        bullet_ID = R.id.bulletButton;
        blitz_ID = R.id.blitzButton;
        game30_ID = R.id.game30Button;
        game60_ID = R.id.game60Button;


        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

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

    private void returnNewTimeControl(int timeInMilli) {
        mNewTimeControl = timeInMilli;
        mCallback.onTimeControlSelected(mNewTimeControl);
        getDialog().dismiss();
    }

    OnTimeControlSelectedListener mCallback;

    public interface OnTimeControlSelectedListener{
        public void onTimeControlSelected(long timeInMilli);
    }

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
