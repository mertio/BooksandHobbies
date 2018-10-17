package com.example.mert.booksandhobbies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mert on 19.02.2017.
 */


public class AddTimeDialog extends DialogFragment {
    int index = 0;
    TextView thetime;
    TextView update;
    TextView timeAverage;
    TextView pagesAverage;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_time_dialog,null);


        TextView okButton = (TextView) dialogView.findViewById(R.id.addTimeOk);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.addPagesCancel);
        final EditText addTime = (EditText) dialogView.findViewById(R.id.addPagesEdit);


        builder.setView(dialogView);

        Dialog dialog = builder.create();
        addTime.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    int minsToAdd = Integer.parseInt(addTime.getText().toString());
                    MyMethods.things.get(index).setTimeSpent(MyMethods.things.get(index).getTimeSpent() + minsToAdd);
                    thetime.setText(MyMethods.minutes2TimeString(MyMethods.things.get(index).getTimeSpent()));
                    update.setText("Last Update: " + MyMethods.getDate());
                    MyMethods.things.get(index).setLastUpdate("Last Update: " + MyMethods.getDate());
                    MyMethods.things.get(index).setTimeOfLastUpdate(System.currentTimeMillis());

                    if(MyMethods.things.get(index) instanceof Hobby) {
                        MyMethods.updateTheAveragesHobby(timeAverage, index);
                    }else {
                        MyMethods.updateTheAveragesBook(timeAverage,pagesAverage,index);
                    }

                }catch (NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please write how many minutes to add.", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

}
