package com.example.mert.booksandhobbies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mert on 17.02.2017.
 */

public class TimerDialog extends DialogFragment {

    int startTime = 0;
    int endTime = 0;
    int seconds = -1;
    boolean isRunning=false;
    int index = 0;
    TextView thetime;
    TextView lastUpdate;
    TextView timeAverage;
    TextView pagesAverage;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timer_dialog,null);
        TextView closeButton = (TextView) dialogView.findViewById(R.id.closeButton);
        final Button reset = (Button) dialogView.findViewById(R.id.resButton);
        final Button pause = (Button) dialogView.findViewById(R.id.pauseButton);
        final Button start = (Button) dialogView.findViewById(R.id.startButton);
        final Button record = (Button) dialogView.findViewById(R.id.recButton);
        final Chronometer chrono = (Chronometer) dialogView.findViewById(R.id.chronometer3);
        final TextView recorded = (TextView) dialogView.findViewById(R.id.recorded);

        chrono.setText(MyMethods.seconds2TimeString(0));

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                seconds++;
                cArg.setText(MyMethods.seconds2TimeString((seconds)));
            }
        });




        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seconds=0;
                chrono.setText(MyMethods.seconds2TimeString(seconds));
                chrono.stop();
                isRunning=false;
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.stop();
                isRunning = false;
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chrono.start();
                isRunning = true;
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMethods.things.get(index).setTimeSpent(MyMethods.things.get(index).getTimeSpent() + seconds/60);

                recorded.setText(getString(R.string.numminhobby) + " " + seconds/60);
                recorded.setVisibility(View.VISIBLE);
                seconds=0;
                chrono.setText(MyMethods.seconds2TimeString(seconds));
                chrono.stop();
                isRunning=false;

                thetime.setText(MyMethods.minutes2TimeString(MyMethods.things.get(index).getTimeSpent()));
                lastUpdate.setText("Last Update: " + MyMethods.getDate());
                MyMethods.things.get(index).setLastUpdate("Last Update: " + MyMethods.getDate());
                MyMethods.things.get(index).setTimeOfLastUpdate(System.currentTimeMillis());

                if(MyMethods.things.get(index) instanceof Hobby) {
                    MyMethods.updateTheAveragesHobby(timeAverage, index);
                }else {
                    MyMethods.updateTheAveragesBook(timeAverage,pagesAverage,index);
                }

            }
        });




        builder.setView(dialogView);








        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                dismiss();
            }
        });





        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);


        return dialog;
    }

    public void onResume() {
        super.onResume();
        if(startTime!=0)
        endTime = (int) System.currentTimeMillis()/1000;
        seconds += endTime-startTime;
        startTime=0;
        endTime=0;
    }

    public void onStop() {
        super.onStop();
        startTime = (int)System.currentTimeMillis()/1000;
    }

}
