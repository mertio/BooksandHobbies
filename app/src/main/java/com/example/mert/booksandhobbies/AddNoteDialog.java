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

/**
 * Created by mert on 18.02.2017.
 */

public class AddNoteDialog extends DialogFragment {
    int index = 0;
    TextView lastUpdate;
    TextView timeAverage;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_note_dialog,null);


        TextView okButton = (TextView) dialogView.findViewById(R.id.addNoteOk);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.addNoteCancel);
        final EditText addNote = (EditText) dialogView.findViewById(R.id.addNote);

        builder.setView(dialogView);

        Dialog dialog = builder.create();
        addNote.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hobby hobby = (Hobby) MyMethods.things.get(index);
                String newNote="";
                newNote = addNote.getText().toString();
                hobby.toDo.add(newNote);
                HobbyActivity callingActivity = (HobbyActivity) getActivity();
                callingActivity.createNewTodo(newNote);
                lastUpdate.setText("Last Update: " + MyMethods.getDate());
                MyMethods.things.get(index).setLastUpdate("Last Update: " + MyMethods.getDate());
                MyMethods.things.get(index).setTimeOfLastUpdate(System.currentTimeMillis());

                MyMethods.updateTheAveragesHobby(timeAverage, index);

                HobbyActivity.toDoList.smoothScrollToPosition(HobbyActivity.toDoList.getAdapter().getCount() - 1);
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