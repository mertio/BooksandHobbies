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
 * Created by mert on 22.02.2017.
 */

public class EdDelToDoDialog extends DialogFragment {

    int thingsIndex = 0;
    int todoIndex = 0;
    TextView note;
    HobbyActivity.TodoAdapter mTodoAdapter;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_edit_todo, null);

        final EditText editNote = (EditText) dialogView.findViewById(R.id.editTodo);
        final Button editButton = (Button) dialogView.findViewById(R.id.todoEditButton);
        final Button deleteButton = (Button) dialogView.findViewById(R.id.todoDeleteButton);

        editNote.setText(note.getText());

        builder.setView(dialogView);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                Hobby hb = (Hobby) MyMethods.things.get(thingsIndex);

                hb.toDo.set(todoIndex,editNote.getText().toString());
                note.setText(hb.toDo.get(todoIndex).toString());
                dismiss();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                Hobby hb = (Hobby) MyMethods.things.get(thingsIndex);
                hb.toDo.remove(todoIndex);
                mTodoAdapter.todoList.clear();
                mTodoAdapter.notifyDataSetChanged();


                if (!hb.toDo.isEmpty())
                    for (int j = 0; j < hb.toDo.size(); j++) {
                        mTodoAdapter.addTodoNote(hb.toDo.get(j));
                        mTodoAdapter.notifyDataSetChanged();
                    }


                dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }
}
