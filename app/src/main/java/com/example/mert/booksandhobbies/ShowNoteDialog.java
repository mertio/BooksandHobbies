package com.example.mert.booksandhobbies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mert on 19.02.2017.
 */

public class ShowNoteDialog extends DialogFragment {

    int index = 0;
    String str = "";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.show_note_dialog,null);

        TextView theNote = (TextView) dialogView.findViewById(R.id.theNote);
        theNote.setText(str);

        builder.setView(dialogView);





        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }
}
