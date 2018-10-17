package com.example.mert.booksandhobbies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by mert on 18.02.2017.
 */

public class BookActivity extends AppCompatActivity {

    int index = 0;
    MainActivity.ThingAdapter mThingAdapter;

    double startTime = 0;
    double endTime = 0;
    double avgTime = 0;
    double avgPage = 0;

    String startDate = "Start Date: ";
    String lastUpdate = "Last Update: ";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_page);


        Intent i = getIntent();
        index = (int) i.getSerializableExtra("index");

        startDate = MyMethods.things.get(index).getDateStarted();
        lastUpdate = MyMethods.things.get(index).getLastUpdate();

        TextView bookName = (TextView) findViewById(R.id.bookName);

        bookName.setText(MyMethods.things.get(index).getName());

        ImageButton addMinutes = (ImageButton) findViewById(R.id.addMinutes);
        ImageButton recordTime = (ImageButton) findViewById(R.id.recordTimeBook);
        ImageButton addPages = (ImageButton) findViewById(R.id.addPages);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.bookProgressBar);
        final TextView theTime = (TextView) findViewById(R.id.bookTime);
        final TextView pagesRead = (TextView) findViewById(R.id.pagesRead);
        final Book book = (Book) MyMethods.things.get(index);

        final TextView bookTimeAverage = (TextView) findViewById(R.id.bookTimeAverage);
        final TextView bookPageAverage = (TextView) findViewById(R.id.bookPageAverage);

        TextView bookStartDate = (TextView) findViewById(R.id.bookStartDate);
        final TextView bookLastUpdate = (TextView) findViewById(R.id.bookLastUpdate);

        pagesRead.setText(book.getPagesRead()+"/"+book.getNumOfPages());

        theTime.setText(MyMethods.minutes2TimeString(MyMethods.things.get(index).getTimeSpent()));

        bookStartDate.setText(startDate);
        bookLastUpdate.setText(lastUpdate);




        addMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                AddTimeDialog dialog = new AddTimeDialog();
                dialog.index=index;
                dialog.thetime = theTime;
                dialog.update=bookLastUpdate;
                dialog.timeAverage=bookTimeAverage;
                dialog.pagesAverage=bookPageAverage;
                dialog.show(getFragmentManager(), "123");
            }
        });

        recordTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                TimerDialog dialog = new TimerDialog();
                dialog.index = index;
                dialog.thetime=theTime;
                dialog.lastUpdate=bookLastUpdate;
                dialog.timeAverage=bookTimeAverage;
                dialog.pagesAverage=bookPageAverage;
                dialog.show(getFragmentManager(), "123");
            }
        });

        addPages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                AddPagesDialog dialog = new AddPagesDialog();
                dialog.index = index;
                dialog.pagesRead = pagesRead;
                dialog.pb = progressBar;
                dialog.lastUpdate=bookLastUpdate;
                dialog.pageAverage=bookPageAverage;
                dialog.timeAverage=bookTimeAverage;
                dialog.show(getFragmentManager(),"123");
            }
        });


        theTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                EditTimeSpentDialog dialog = new EditTimeSpentDialog();
                dialog.index=index;
                dialog.time = (TextView) theTime;
                dialog.show(getFragmentManager(), "123");
                return true;
            }
        });

        pagesRead.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                EditPageReadDialog dialog = new EditPageReadDialog();
                dialog.index=index;
                dialog.pagesRead=pagesRead;
                dialog.show(getFragmentManager(),"123");
                dialog.pb=progressBar;
                return true;
            }
        });


        progressBar.setMax(book.getNumOfPages());
        progressBar.setProgress(book.getPagesRead());

        

    }


    public void saveArrayListToFile(ArrayList<Thing> students) throws IOException {
        File file = new File(getFilesDir(), "data.txt");
        if(file.exists()) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                oos.writeObject(students);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            file.createNewFile();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                oos.writeObject(students);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public void onPause() {
        super.onPause();
        try {
            saveArrayListToFile(MyMethods.things);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
