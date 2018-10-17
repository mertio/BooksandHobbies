package com.example.mert.booksandhobbies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mert on 17.02.2017.
 */

public class AddBookDialog extends DialogFragment {

    MainActivity.ThingAdapter mThingAdapter;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_book_dialog,null);

        final EditText addBookName = (EditText) dialogView.findViewById(R.id.addBookName);
        final EditText numOfPages = (EditText) dialogView.findViewById(R.id.numofpages);
        TextView okButton = (TextView) dialogView.findViewById(R.id.addBookOk);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.addBookCancel);

        builder.setView(dialogView);

        Dialog dialog = builder.create();
        addBookName.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Book newBook = new Book();
                    newBook.setName(addBookName.getText().toString());
                    newBook.setNumOfPages(Integer.parseInt(numOfPages.getText().toString()));
                    MyMethods.things.add(newBook);
                    mThingAdapter.thingsList.add(newBook);
                    mThingAdapter.notifyDataSetChanged();

                    MyMethods.things.get(MyMethods.things.size()-1).setDateStarted(MyMethods.getDate());
                    final long tm = System.currentTimeMillis();
                    MyMethods.things.get(MyMethods.things.size()-1).setTimeStarted(tm);

                }catch (NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please write the number of pages.", Toast.LENGTH_SHORT).show();
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
