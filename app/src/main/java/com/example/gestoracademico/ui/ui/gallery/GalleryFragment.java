package com.example.gestoracademico.ui.ui.gallery;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.CalendarTaskListAdapter;
import com.example.gestoracademico.R;
import com.example.gestoracademico.databinding.FragmentGalleryBinding;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;
import com.example.gestoracademico.ui.SQLiteHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    private SQLiteHandler dbHandler;

    private EditText eventTitle;


    private CalendarView calendarView;

    private String selectedDate;

    private SQLiteDatabase sqLiteDatabase;

    RecyclerView dayTasksView;
    private AppDatabase appDatabase;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        calendarView = root.findViewById(R.id.calendarView);

        dayTasksView = root.findViewById(R.id.recyclerCalendar);
        dayTasksView.setHasFixedSize(true);
        dayTasksView.setLayoutManager(new LinearLayoutManager(getContext()));

        appDatabase = AppDatabase.getDatabase(getContext());

        updateCurrentDayTasks(root);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(dayOfMonth) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);

                readTasks(root);
            }
        });


        return root;
    }


    /**
     * Actualizará el recycler de tareas la
     * primera vez que se entre al calendario
     * @param root
     */
    private void updateCurrentDayTasks(View root) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String actualDate = String.valueOf(formatter.format(date));
        selectedDate = actualDate;
        readTasks(root);
    }


    /**
     * Buscará si hay alguna tarea para el día
     * elegido en el calendario
     */
    private void readTasks(View root) {

        List<Task> listaActualTareas = new ArrayList<>();

        try{
            listaActualTareas = appDatabase.getTaskDAO().getByDate(selectedDate);
            CalendarTaskListAdapter tlAdapter= new CalendarTaskListAdapter(listaActualTareas,
                    new CalendarTaskListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Task tarea) {

                            Log.i("Click en tarea", "Click");
                        }
                    });
            dayTasksView.setAdapter(tlAdapter);


        }catch(Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}