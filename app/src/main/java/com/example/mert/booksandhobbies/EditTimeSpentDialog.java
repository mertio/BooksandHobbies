package com.example.mert.booksandhobbies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mert on 25.02.2017.
 */

public class EditTimeSpentDialog extends DialogFragment {

    int index = 0;
    TextView time;

    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_time,null);

        final EditText editTime = (EditText) dialogView.findViewById(R.id.editTimeSpent);
        Button setButton = (Button) dialogView.findViewById(R.id.timeSetButton);
        Button resetButton = (Button) dialogView.findViewById(R.id.timeResetButton);

        builder.setView(dialogView);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                try {
                    MyMethods.things.get(index).setTimeSpent(Integer.parseInt(editTime.getText().toString()));
                    time.setText(MyMethods.minutes2TimeString(MyMethods.things.get(index).getTimeSpent()));
                } catch(NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Number of minutes to set is empty.", Toast.LENGTH_SHORT).show();
                }


                dismiss();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                MyMethods.things.get(index).setTimeSpent(0);
                time.setText(MyMethods.minutes2TimeString(0));

                dismiss();
            }
        });


        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

}
