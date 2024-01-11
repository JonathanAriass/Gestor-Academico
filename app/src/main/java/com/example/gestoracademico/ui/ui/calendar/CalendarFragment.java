package com.example.gestoracademico.ui.ui.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.CalendarTaskListAdapter;
import com.example.gestoracademico.R;
import com.example.gestoracademico.databinding.FragmentCalendarBinding;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Fragmento del calendario
 */
public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    private CalendarView calendarView;

    private String selectedDate;


    RecyclerView dayTasksView;
    private AppDatabase appDatabase;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
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
        LocalDate localDate;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDate = LocalDate.now();
            int day = localDate.getDayOfMonth();
            int month = localDate.getMonthValue();
            int year =  localDate.getYear();
            selectedDate =  Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
            readTasks(root);
        }


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