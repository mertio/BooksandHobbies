package com.example.mert.booksandhobbies;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ThingAdapter mThingAdapter = new ThingAdapter();
    String currentDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hobby_book_list);

        try {
            MyMethods.things = loadArrayListFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        final Intent toBook = new Intent(this, BookActivity.class);
        final Intent toHobby = new Intent(this, HobbyActivity.class);

        ImageView addBook = (ImageView) findViewById(R.id.addBook);
        ImageView addHobby = (ImageView) findViewById(R.id.addHobby);

        ListView listOfThings = (ListView) findViewById(R.id.bookHobbyList);
        listOfThings.setAdapter(mThingAdapter);



        if(!MyMethods.things.isEmpty()) {
            for(int i=0; i<MyMethods.things.size(); i++) {
                mThingAdapter.thingsList.add(MyMethods.things.get(i));
            }
            mThingAdapter.notifyDataSetChanged();
        }

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                AddBookDialog dialog = new AddBookDialog();
                dialog.mThingAdapter=mThingAdapter;
                dialog.show(getFragmentManager(), "123");

            }
        });

        addHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                AddHobbyDialog dialog = new AddHobbyDialog();
                dialog.mThingAdapter=mThingAdapter;
                dialog.show(getFragmentManager(), "123");
            }
        });

        listOfThings.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long id) {
                if(MyMethods.things.get(i) instanceof Book) {

                    MyMethods.things.add(0, MyMethods.things.get(i));
                    MyMethods.things.remove(i+1);
                    mThingAdapter.thingsList.add(0, mThingAdapter.thingsList.get(i));
                    mThingAdapter.thingsList.remove(i+1);
                    i=0;


                    toBook.putExtra("index", i);
                    MyMethods.things.get(i).setDateStarted("Date Started: " + MyMethods.getDate());
                    startActivity(toBook);
                }
                else {

                    MyMethods.things.add(0, MyMethods.things.get(i));
                    MyMethods.things.remove(i+1);
                    mThingAdapter.thingsList.add(0, mThingAdapter.thingsList.get(i));
                    mThingAdapter.thingsList.remove(i+1);
                    i=0;


                    toHobby.putExtra("index", i);
                    MyMethods.things.get(i).setDateStarted("Date Started: " + MyMethods.getDate());
                    startActivity(toHobby);
                }


            } });



        listOfThings.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(MyMethods.things.get(i) instanceof Book) {

                    EdDelBookDialog dialog = new EdDelBookDialog();
                    dialog.index=i;
                    dialog.mThingAdapter=mThingAdapter;
                    dialog.name = (TextView) view.findViewById(R.id.book_name);

                    dialog.show(getFragmentManager(),"123");

                }
                else {

                    EdDelHobbyDialog dialog = new EdDelHobbyDialog();
                    dialog.index=i;
                    dialog.mThingAdapter = mThingAdapter;
                    dialog.name = (TextView) view.findViewById(R.id.hobby33);
                    dialog.show(getFragmentManager(),"123");

                }



                return true;
            }
        });






    }


    public class ThingAdapter extends BaseAdapter {

        ArrayList<Thing> thingsList = new ArrayList<Thing>();

        @Override
        public int getCount() {
            return thingsList.size();
        }

        @Override
        public Object getItem(int i) {
            return thingsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {






            if(view==null) {
                if(MyMethods.things.get(i) instanceof Hobby) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.hobby_list_item, viewGroup, false);



                }
                else if (MyMethods.things.get(i) instanceof Book) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.book_list_item, viewGroup, false);
                }

                else {
                    System.exit(0);
                }



            }

            if(MyMethods.things.get(i) instanceof Hobby) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.hobby_list_item, viewGroup, false);
                TextView hobbyName = (TextView) view.findViewById(R.id.hobby33);
                TextView timeSpentHobby = (TextView) view.findViewById(R.id.timeSpentHobby33);

                Hobby tmp = (Hobby) MyMethods.things.get(i);
                hobbyName.setText(tmp.getName().toString());
                String time = MyMethods.minutes2TimeString(tmp.getTimeSpent());
                timeSpentHobby.setText(time);

            }
            else {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.book_list_item, viewGroup, false);
                TextView bookName = (TextView) view.findViewById(R.id.book_name);
                TextView timeSpentBook = (TextView) view.findViewById(R.id.timeSpentBook2);
                ProgressBar listProgressBar = (ProgressBar) view.findViewById(R.id.listProgressBar);

                bookName.setText(MyMethods.things.get(i).getName());
                String time = MyMethods.minutes2TimeString(MyMethods.things.get(i).getTimeSpent());

                timeSpentBook.setText(time);
                Book book = (Book) MyMethods.things.get(i);

                listProgressBar.setMax(book.getNumOfPages());
                listProgressBar.setProgress(book.getPagesRead());

            }



            return view;
        }

        public void addThing(Thing thing){
            thingsList.add(thing);
            notifyDataSetChanged();
        }

        public void addBook(Book book){
            thingsList.add(book);

        }

        public void addHobby(Hobby hobby){
            thingsList.add(hobby);

        }


    }


    public void createNewHobby(Hobby hb) {
        mThingAdapter.addThing(hb);
    }

    public void createNewBook(Book bk) {
        mThingAdapter.addThing(bk);
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



    public ArrayList<Thing> loadArrayListFromFile() throws IOException {
        ObjectInputStream ois = new ObjectInputStream(openFileInput("data.txt"));
        try {
            return (ArrayList<Thing>) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            ois.close();
        }
        return null;
    }




    public void onResume() {
        super.onResume();
        mThingAdapter.notifyDataSetChanged();

        try {
            saveArrayListToFile(MyMethods.things);
        } catch (IOException e) {
            e.printStackTrace();
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
