package com.example.mert.booksandhobbies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mert on 17.02.2017.
 */

public class AddHobbyDialog extends DialogFragment {

    MainActivity.ThingAdapter mThingAdapter;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_hobby_dialog,null);

        final EditText addHobbyName = (EditText) dialogView.findViewById(R.id.addhobbyName);
        TextView okButton = (TextView) dialogView.findViewById(R.id.addhobbyOk);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.addhobbyCancel);

        builder.setView(dialogView);

        Dialog dialog = builder.create();
        addHobbyName.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hobby newHobby = new Hobby();
                newHobby.setName(addHobbyName.getText().toString());
                MyMethods.things.add(newHobby);
                mThingAdapter.thingsList.add(newHobby);
                mThingAdapter.notifyDataSetChanged();

                MyMethods.things.get(MyMethods.things.size()-1).setDateStarted(MyMethods.getDate());
                final long tm = System.currentTimeMillis();
                MyMethods.things.get(MyMethods.things.size()-1).setTimeStarted(tm);

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
