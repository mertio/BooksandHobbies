package com.example.mert.booksandhobbies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mert on 21.02.2017.
 */

public class AddPagesDialog extends DialogFragment {
    int index = 0;
    TextView pagesRead;
    ProgressBar pb;
    TextView lastUpdate;
    TextView timeAverage;
    TextView pageAverage;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_pages_dialog,null);


        TextView okButton = (TextView) dialogView.findViewById(R.id.addPagesOk);
        TextView cancelButton = (TextView) dialogView.findViewById(R.id.addPagesCancel);
        final EditText addPages = (EditText) dialogView.findViewById(R.id.addPagesEdit);



        builder.setView(dialogView);

        Dialog dialog = builder.create();
        addPages.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Book book = (Book) MyMethods.things.get(index);
                    int pagesToAdd = Integer.parseInt(addPages.getText().toString());
                    book.setPagesRead(book.getPagesRead() + pagesToAdd);
                    if (book.getPagesRead() <= book.getNumOfPages()) {
                        pagesRead.setText(book.getPagesRead() + "/" + book.getNumOfPages());
                        pb.setProgress(book.getPagesRead());
                    } else {
                        pagesRead.setText(book.getNumOfPages() + "/" + book.getNumOfPages());
                        pb.setProgress(book.getNumOfPages());
                    }
                    lastUpdate.setText("Last Update: " + MyMethods.getDate());
                    MyMethods.things.get(index).setLastUpdate("Last Update: " + MyMethods.getDate());
                    MyMethods.things.get(index).setTimeOfLastUpdate(System.currentTimeMillis());

                    MyMethods.updateTheAveragesBook(timeAverage, pageAverage, index);

                }catch (NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please write how many pages to add.", Toast.LENGTH_SHORT).show();
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
