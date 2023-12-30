package com.example.gestoracademico.ui.ui.home;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.MainActivity;
import com.example.gestoracademico.NewTask;
import com.example.gestoracademico.R;
import com.example.gestoracademico.TaskListAdapter;
import com.example.gestoracademico.TaskRecycler;
import com.example.gestoracademico.databinding.FragmentHomeBinding;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeToDeleteCallback.SwipeToDeleteListener {

//    private FragmentHomeBinding binding;

    public static final String TAREA_SELECCIONADA = "tarea_seleccionada";

    public static final String TAREA_CREADA = "tarea_creada";

    private static final int GESTION_ACTIVITY = 1;
    List<Task> listaTareas =  new ArrayList<Task>();
    Task tarea;
    RecyclerView listaTareaView;

    private AppDatabase appDatabase;


    public static HomeFragment newInstance(String caratula, String estreno, String duracion) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
//        Log.e("PRUEBA", caratula);
//        args.putString(CARATULA_PELI, caratula);
//        args.putString(ESTRENO_PELI, estreno);
//        args.putString(DURACION_PELI, duracion);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        View root  = inflater.inflate(R.layout.fragment_home, container, false);

        appDatabase = AppDatabase.getDatabase(getContext());
//        appDatabase.getTaskDAO().deleteAll();
//        loadTasks();

//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        listaTareaView = root.findViewById(R.id.reciclerView);
        listaTareaView.setHasFixedSize(true);

        listaTareaView.setLayoutManager(new LinearLayoutManager(getContext()));

//        Task t1= new Task(1, "Hacer tarea de SI","26/8/2020");
//        Task t2= new Task(2, "Comprar patatas, huevos, leche, cereales y manzanas","26/8/2020");
//
//        listaTareas.add( t1);
//        listaTareas.add( t2);

        TaskListAdapter tlAdapter= new TaskListAdapter(listaTareas,
                new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task tarea) {

                Log.i("Click en tarea", "Click");
                clickonItem(tarea);
            }
        });
        listaTareaView.setAdapter(tlAdapter);
        return root;
    }

    protected void loadTasks() {
        Task task = null;
        listaTareas = new ArrayList<>();
        InputStream file;
        InputStreamReader reader;
        BufferedReader bufferedReader = null;

        try {
            file = getContext().getAssets().open("tasks.csv");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data != null) {
                    if (data.length==3) {
                        task = new Task(Integer.parseInt(data[0]), data[1], data[2], 0, 0);
                        appDatabase.getTaskDAO().add(task);
                        listaTareas.add(task);
                    }
                    Log.d("loadTasks()", task.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("PRUEBA", "Entra en onActivityResult");
        if(requestCode == GESTION_ACTIVITY){

            if (resultCode == RESULT_OK){
                Task tarea = data.getParcelableExtra(TAREA_CREADA);

                listaTareas.add(tarea);

                TaskListAdapter tlAdapter = new TaskListAdapter(listaTareas,
                        new TaskListAdapter.OnItemClickListener(){
                            @Override
                            public void onItemClick(Task item) {
                                clickonItem(item);
                            }
                        });

                listaTareaView.setAdapter(tlAdapter);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("ENTRA EN ONRESUME", "ejemplo");

        listaTareas = appDatabase.getTaskDAO().getAllOrderByPriority();

        Log.i("VALOR LISTA SIZE", String.valueOf(listaTareas.size()));


        TaskListAdapter tlAdapter= new TaskListAdapter(listaTareas,
                new TaskListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Task tarea) {

                        Log.i("Click en tarea", "Click");
                        clickonItem(tarea);
                    }
                });
        listaTareaView.setAdapter(tlAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        itemTouchHelper.attachToRecyclerView(listaTareaView);
    }

    public void clickonItem (Task tarea){

        Intent intent=new Intent (getActivity(), MainActivity.class);
        intent.putExtra(TAREA_SELECCIONADA, tarea);
        startActivity(intent);
    }

    public void crearNuevaTarea(View view) {
        Intent intent=new Intent(getActivity(), NewTask.class);
        startActivityForResult(intent, GESTION_ACTIVITY);
    }


    public List<Task> getListaTareas() {
        return listaTareas;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

    @Override
    public void onSwipe(int position) {
        // Obtener el adaptador del RecyclerView
        RecyclerView.Adapter adapter = listaTareaView.getAdapter();

        // Asegurarse de que el adaptador no sea nulo y que la posición sea válida
        if (adapter != null && position != RecyclerView.NO_POSITION) {
            Task tarea = listaTareas.get(position);
            // Eliminar la tarea del adaptador
            adapter.notifyItemRemoved(position);
            //Eliminar la tarea de la lista
            listaTareas.remove(position);

            //Eliminar la tarea en la BD
            AppDatabase db = AppDatabase.getDatabase(listaTareaView.getRootView().getContext());
            if(db.getTaskDAO().getTaskWithDocument().contains(tarea)){
                db.getFileDAO().deleteByID(tarea.getFk_pdf());
            }
            db.getTaskDAO().delete(tarea);
            Toast.makeText(listaTareaView.getRootView().getContext(), "Tarea eliminada correctamente", Toast.LENGTH_SHORT).show();



        }
    }
}