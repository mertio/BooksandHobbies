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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mert on 25.02.2017.
 */

public class EditPageReadDialog extends DialogFragment {

    int index = 0;
    TextView pagesRead;
    ProgressBar pb;

    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_pages,null);

        final EditText editTime = (EditText) dialogView.findViewById(R.id.editTodo);
        Button setButton = (Button) dialogView.findViewById(R.id.todoEditButton);
        Button resetButton = (Button) dialogView.findViewById(R.id.todoDeleteButton);

        builder.setView(dialogView);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                try {
                    Book book = (Book)MyMethods.things.get(index);
                    book.setPagesRead(Integer.parseInt(editTime.getText().toString()));
                    pagesRead.setText(book.getPagesRead() + "/" + book.getNumOfPages());
                    pb.setProgress(book.getPagesRead());
                } catch(NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Pages read to set is empty.", Toast.LENGTH_SHORT).show();
                }


                dismiss();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                Book book = (Book) MyMethods.things.get(index);
                book.setPagesRead(0);
                pagesRead.setText(book.getPagesRead() + "/" + book.getNumOfPages());
                pb.setProgress(0);

                dismiss();
            }
        });


        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

}
