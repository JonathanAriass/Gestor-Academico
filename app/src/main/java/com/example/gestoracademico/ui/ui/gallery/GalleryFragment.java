package com.example.gestoracademico.ui.ui.gallery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.R;
import com.example.gestoracademico.TaskListAdapter;
import com.example.gestoracademico.databinding.FragmentGalleryBinding;
import com.example.gestoracademico.modelo.Task;
import com.example.gestoracademico.ui.SQLiteHandler;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    private SQLiteHandler dbHandler;

    private EditText eventTitle;


    private CalendarView calendarView;

    private String selectedDate;

    private SQLiteDatabase sqLiteDatabase;

    RecyclerView dayTasksView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //eventTitle = root.findViewById(R.id.eventTitle);
       calendarView = root.findViewById(R.id.calendarView);

        try{

            dbHandler = new SQLiteHandler(getContext(), "CalendarDatabase", null,1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalendar(Date TEXT, Event TEXT)");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(dayOfMonth);
                readTask(root);
                //readDatabase(view);
            }
        });


        //Button button = (Button) root.findViewById(R.id.modifyButton);
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                insertDatabase(v);
//            }
//        });

        return root;
    }


    /**
     * Buscará si hay alguna tarea para el día
     * elegido en el calendario
     */
    private void readTask(View root) {

        dayTasksView = root.findViewById(R.id.recyclerCalendar);
        dayTasksView.setHasFixedSize(true);

        dayTasksView.setLayoutManager(new LinearLayoutManager(getContext()));

        Task t1= new Task("Hacer tarea de SI","26/8/2020","");
        Task t2= new Task("Comprar patatas, huevos, leche, cereales y manzanas","26/8/2020","");
        Task t3= new Task("Comprar patatas, huevos, leche, cereales y manzanas","28/8/2020","");
        Task t4= new Task("Comprar patatas, huevos, leche, cereales y manzanas","28/8/2020","");

        List<Task> listaTareas =  new ArrayList<Task>();

        listaTareas.add( t1);
        listaTareas.add( t2);
        listaTareas.add( t3);
        listaTareas.add( t4);

        TaskListAdapter tlAdapter= new TaskListAdapter(listaTareas,
                new TaskListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Task tarea) {

                        Log.i("Click en tarea", "Click");
                       // clickonItem(tarea);
                    }
                });
        dayTasksView.setAdapter(tlAdapter);

    }

    public void insertDatabase(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date",selectedDate);
        contentValues.put("Event", eventTitle.getText().toString());
        sqLiteDatabase.insert("EventCalendar", null, contentValues);

    }



    public void readDatabase(View view){
        String query = "Select Event from EventCalendar where Date = " + selectedDate;
        try{
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            eventTitle.setText(cursor.getString(0));
            Toast.makeText(getContext(),cursor.getString(0),Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
            eventTitle.setText("");
        }
    }

//    private void showCalendar() {
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                //String date = dayOfMonth + "/" + month + "/" + year;
//
//            }
//        }, year, month, day);
//        dpd.show();
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}