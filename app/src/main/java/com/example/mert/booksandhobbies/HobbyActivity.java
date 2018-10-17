package com.example.mert.booksandhobbies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mert on 18.02.2017.
 */

public class HobbyActivity extends AppCompatActivity {

    int index=0;

    TodoAdapter mTodoAdapter = new TodoAdapter();
    DoneAdapter mDoneAdapter = new DoneAdapter();

    String startDate = "Start Date: ";
    String lastUpdate = "Last Update: ";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hobby_page);


        Intent i = getIntent();
        index = (int) i.getSerializableExtra("index");

        startDate = MyMethods.things.get(index).getDateStarted();
        lastUpdate = MyMethods.things.get(index).getLastUpdate();



        Hobby myHobby = (Hobby) MyMethods.things.get(index);

        final TextView theTime = (TextView) findViewById(R.id.theTime);
        TextView hobbyName = (TextView) findViewById(R.id.hobby_name);
        theTime.setText(MyMethods.minutes2TimeString(MyMethods.things.get(index).getTimeSpent()));
        hobbyName.setText(MyMethods.things.get(index).getName());

        ImageButton recordTime = (ImageButton) findViewById(R.id.recordTime);
        ImageView addTodo = (ImageView) findViewById(R.id.addToDo);
        ImageButton addTime = (ImageButton) findViewById(R.id.addTime);

        TextView hobbyStartDate = (TextView) findViewById(R.id.hobbyStartDate);
        final TextView hobbyLastUpdate = (TextView) findViewById(R.id.hobbyLastUpdate);

        final TextView hobbyTimeAverage = (TextView) findViewById(R.id.hobbyTimeAverage);

        hobbyStartDate.setText(startDate);
        hobbyLastUpdate.setText(lastUpdate);

        Hobby hobby = (Hobby) MyMethods.things.get(index);




        ListView toDoList = (ListView) findViewById(R.id.toDoList);
        toDoList.setAdapter(mTodoAdapter);

        ListView doneList = (ListView) findViewById(R.id.doneList);
        doneList.setAdapter(mDoneAdapter);

        if(!myHobby.toDo.isEmpty()) {
            for(int j=0; j<myHobby.toDo.size(); j++) {
                mTodoAdapter.todoList.add(myHobby.toDo.get(j));
            }
        }

        if(!myHobby.done.isEmpty()) {
            for(int j=0; j<myHobby.done.size(); j++) {
                mDoneAdapter.doneList.add(myHobby.done.get(j));
            }
        }


        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                AddTimeDialog dialog = new AddTimeDialog();
                dialog.index=index;
                dialog.thetime = theTime;
                dialog.update=hobbyLastUpdate;
                dialog.timeAverage=hobbyTimeAverage;

                dialog.show(getFragmentManager(), "123");

            }
        });


        theTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                EditTimeSpentDialog dialog = new EditTimeSpentDialog();
                dialog.index=index;
                dialog.time = theTime;
                dialog.show(getFragmentManager(), "123");
                return true;
            }
        });



        doneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hobby hobby = (Hobby)MyMethods.things.get(index);
                String str = hobby.done.get(i);
                ShowNoteDialog dialog = new ShowNoteDialog();
                dialog.str = str;
                dialog.show(getFragmentManager(), "123");
            }
        });

        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hobby hobby = (Hobby)MyMethods.things.get(index);
                String str = hobby.toDo.get(i);
                ShowNoteDialog dialog = new ShowNoteDialog();
                dialog.str = str;
                dialog.show(getFragmentManager(), "123");
            }
        });

        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                EdDelToDoDialog dialog = new EdDelToDoDialog();
                dialog.thingsIndex = index;
                dialog.todoIndex=i;
                dialog.note = (TextView) view.findViewById(R.id.todoText);
                dialog.mTodoAdapter=mTodoAdapter;
                dialog.show(getFragmentManager(), "123");

                return true;
            }
        });

        doneList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeleteDoneDialog dialog = new DeleteDoneDialog();
                dialog.mDoneAdapter = mDoneAdapter;
                dialog.thingsIndex=index;
                dialog.doneIndex=i;
                dialog.show(getFragmentManager(),"123");
                return true;
            }
        });








        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_click));
                AddNoteDialog dialog = new AddNoteDialog();
                dialog.index=index;
                dialog.lastUpdate=hobbyLastUpdate;
                dialog.timeAverage=hobbyTimeAverage;
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
                dialog.lastUpdate=hobbyLastUpdate;
                dialog.timeAverage=hobbyTimeAverage;
                dialog.show(getFragmentManager(), "123");
            }
        });

    }






    public class TodoAdapter extends BaseAdapter {

        ArrayList<String> todoList = new ArrayList<String>();

        @Override
        public int getCount() {
            return todoList.size();
        }

        @Override
        public Object getItem(int i) {
            return todoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            if(view==null) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.todo_list_item, viewGroup, false);
            }
            TextView todoText = (TextView) view.findViewById(R.id.todoText);
            final CheckBox todoCheckBox = (CheckBox) view.findViewById(R.id.todoCheckBox);
            Hobby hobby = (Hobby) MyMethods.things.get(index);

                todoText.setText(hobby.toDo.get(i));

            if(i%2==0)
                view.setBackgroundColor(Color.parseColor("#E4E4E4"));
            else {
                view.setBackgroundColor(Color.parseColor("#F6F6F6"));
            }




            todoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Hobby hobby1 = (Hobby)  MyMethods.things.get(index);
                    hobby1.done.add(hobby1.toDo.get(i));
                    createNewDone(hobby1.toDo.get(i));


                    todoCheckBox.setChecked(false);


                    hobby1.toDo.remove(i);
                    mTodoAdapter.todoList.clear();
                    if(!hobby1.toDo.isEmpty())
                        for(int j=0; j<hobby1.toDo.size(); j++) {
                            mTodoAdapter.todoList.add(hobby1.toDo.get(j));
                        }

                    mTodoAdapter.notifyDataSetChanged();




                }
            });


            return view;
        }
        public void addTodoNote(String s){
            todoList.add(s);
            notifyDataSetChanged();
        }
    }


    public class DoneAdapter extends BaseAdapter {

        ArrayList<String> doneList = new ArrayList<String>();






        @Override
        public int getCount() {
            return doneList.size();
        }

        @Override
        public Object getItem(int i) {
            return doneList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            java.util.Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;

            String strMonth = intToMonth(month);


            if(view==null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.done_list_item, viewGroup, false);
            }
            TextView textDone = (TextView) view.findViewById(R.id.doneText);
            TextView dayDone = (TextView) view.findViewById(R.id.dayDone);
            TextView monthDone = (TextView) view.findViewById(R.id.monthDone);
            dayDone.setText(day+"");
            monthDone.setText(strMonth);



                textDone.setText(doneList.get(i));

            if(i%2==0)
                view.setBackgroundColor(Color.parseColor("#E4E4E4"));
            else {
                view.setBackgroundColor(Color.parseColor("#F6F6F6"));
            }


            return view;
        }
        public void addDoneNote(String s){
            doneList.add(s);
            notifyDataSetChanged();
        }
    }



    public void createNewTodo(String s) {
        mTodoAdapter.addTodoNote(s);
    }

    public void createNewDone(String s) {
        mDoneAdapter.addDoneNote(s);
    }


    public String intToMonth (int month) {
        String strMonth = "";

        switch (month) {
            case 1:
                strMonth = getString(R.string.jan);
                break;
            case 2:
                strMonth =  getString(R.string.feb);
                break;
            case 3:
                strMonth =  getString(R.string.march);
                break;
            case 4:
                strMonth =  getString(R.string.jan);
                break;
            case 5:
                strMonth =  getString(R.string.jan);
                break;
            case 6:
                strMonth =  getString(R.string.jan);
                break;
            case 7:
                strMonth=  getString(R.string.jan);
                break;
            case 8:
                strMonth =  getString(R.string.jan);
                break;
            case 9:
                strMonth =  getString(R.string.jan);
                break;
            case 10:
                strMonth =  getString(R.string.jan);
                break;
            case 11:
                strMonth =  getString(R.string.jan);
                break;
            case 12:
                strMonth =  getString(R.string.jan);
                break;
        }
        return strMonth;
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
