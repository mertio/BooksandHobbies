package com.example.mert.booksandhobbies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by mert on 25.02.2017.
 */

public class DeleteDoneDialog extends DialogFragment {

    HobbyActivity.DoneAdapter mDoneAdapter;
    int thingsIndex = 0;
    int doneIndex = 0;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_done, null);

        Button doneDelete = (Button) dialogView.findViewById(R.id.doneDeleteButton);

        builder.setView(dialogView);

        doneDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.image_click));
                Hobby hb = (Hobby) MyMethods.things.get(thingsIndex);
                hb.done.remove(doneIndex);
                mDoneAdapter.doneList.clear();
                mDoneAdapter.notifyDataSetChanged();


                if (!hb.done.isEmpty())
                    for (int j = 0; j < hb.done.size(); j++) {
                        mDoneAdapter.addDoneNote(hb.done.get(j));
                        mDoneAdapter.notifyDataSetChanged();
                    }

                dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

}
