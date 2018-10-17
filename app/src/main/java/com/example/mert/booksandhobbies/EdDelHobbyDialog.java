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
 * Created by mert on 21.02.2017.
 */

public class EdDelHobbyDialog extends DialogFragment {

    int index = 0;
    TextView name;
    MainActivity.ThingAdapter mThingAdapter;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_edit_hobby,null);

        final EditText editHobbyName = (EditText) dialogView.findViewById(R.id.editHobby);
        final Button editButton = (Button) dialogView.findViewById(R.id.hobbyEditButton);
        final Button deleteButton = (Button) dialogView.findViewById(R.id.hobbyDeleteButton);

        editHobbyName.setText(name.getText());

        builder.setView(dialogView);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                MyMethods.things.get(index).setName(editHobbyName.getText().toString());
                name.setText(MyMethods.things.get(index).getName());
                dismiss();

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