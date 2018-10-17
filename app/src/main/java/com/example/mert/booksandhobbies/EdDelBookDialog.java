package com.example.mert.booksandhobbies;

/**
 * Created by mert on 21.02.2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mert on 21.02.2017.
 */

public class EdDelBookDialog extends DialogFragment {

    int index = 0;
    TextView name;

    MainActivity.ThingAdapter mThingAdapter;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_edit_book,null);

        final EditText editNameOfBook = (EditText) dialogView.findViewById(R.id.editBookName33);
        final EditText editNumberOfPages = (EditText) dialogView.findViewById(R.id.editPageNumbers);
        final Button editButton = (Button) dialogView.findViewById(R.id.bookEditButton);
        final Button deleteButton = (Button) dialogView.findViewById(R.id.deleteBook);

        builder.setView(dialogView);

        editNameOfBook.setText(name.getText().toString());

        final Book myBook = (Book)MyMethods.things.get(index);

        editNumberOfPages.setText(myBook.getNumOfPages()+"");


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws NumberFormatException {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                try {
                    myBook.setName(editNameOfBook.getText().toString());
                    myBook.setNumOfPages(Integer.parseInt(editNumberOfPages.getText().toString()));
                    name.setText(myBook.getName());
                    dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Number of pages to set is empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                MyMethods.things.remove(index);
                mThingAdapter.thingsList.clear();
                mThingAdapter.notifyDataSetChanged();
                if(!MyMethods.things.isEmpty())
                    for(int j=0; j<MyMethods.things.size(); j++) {

                        if (MyMethods.things.get(j) instanceof Book) {
                            mThingAdapter.addBook((Book) MyMethods.things.get(j));
                            mThingAdapter.notifyDataSetChanged();
                        }
                        else {
                            mThingAdapter.addHobby((Hobby) MyMethods.things.get(j));
                            mThingAdapter.notifyDataSetChanged();
                        }
                    }



                dismiss();
            }
        });


        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);


        return dialog;
    }



}
