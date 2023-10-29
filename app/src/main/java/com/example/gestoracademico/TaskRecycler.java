package com.example.gestoracademico;

import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.gestoracademico.modelo.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRecycler extends AppCompatActivity {

    public static final String TAREA_SELECCIONADA = "tarea_seleccionada";

    public static final String TAREA_INDEX_SELECCIONADA = "tarea-index-seleccionada";

    public static final String TAREA_CREADA = "tarea_creada";

    public static final String TASK_MODIFIED = "task-modified";

    public static final String TASK_MODIFIED_INDEX = "task-modified-index";

    private static final int GESTION_ACTIVITY = 1;

    private static final int MODIFIED_ACTIVITY = 2;

    List<Task> listaTareas;
    Task tarea;
    RecyclerView listaTareaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_recycler);

        rellenarListaTareas(); //rellena la lista de tareas

        listaTareaView = (RecyclerView)findViewById(R.id.reciclerView);
        listaTareaView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaTareaView.setLayoutManager(layoutManager);

        Log.i("PRUEBA", "Entra en onCreate");

        TaskListAdapter tlAdapter= new TaskListAdapter(listaTareas,
                new TaskListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Task tarea) {
                        clickonItem(tarea, getIndexOfTask(tarea));
                    }
                });
        listaTareaView.setAdapter(tlAdapter);
    }

    private int getIndexOfTask(Task task) {
        int i = 0;
        for (Task taskAux : listaTareas) {
            if (task.getDescripcion().equals(taskAux.getDescripcion())) {
                // TODO: esta condicion es pochisima porque si hay dos con el mismo nombre jodimos
                return i;
            }
            i++;
        }
        return -1;
    }

    public void clickonItem (Task task, int index){

        Intent intent=new Intent (TaskRecycler.this, MainActivity.class);
        intent.putExtra(TAREA_SELECCIONADA, task);
        intent.putExtra(TAREA_INDEX_SELECCIONADA, index);
        startActivityForResult(intent, GESTION_ACTIVITY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("REQUEST", requestCode + " | " + resultCode);
        Log.i("PRUEBA", "Entra en onActivityResult");

        if(requestCode == GESTION_ACTIVITY){

            if (resultCode == RESULT_OK){
                Task tarea = data.getParcelableExtra(TAREA_CREADA);

                listaTareas.add(tarea);

                TaskListAdapter tlAdapter = new TaskListAdapter(listaTareas,
                        new TaskListAdapter.OnItemClickListener(){
                            @Override
                            public void onItemClick(Task item) {
                                clickonItem(item, getIndexOfTask(item));
                            }
                        });

                listaTareaView.setAdapter(tlAdapter);
            } else if (resultCode == 2) {
                Task tarea = data.getParcelableExtra(TASK_MODIFIED);
                int taskIndex = data.getIntExtra(TASK_MODIFIED_INDEX, 0);

                listaTareas.get(taskIndex).setDescripcion(tarea.getDescripcion());
                listaTareas.get(taskIndex).setFecha(tarea.getFecha());

                Log.i("TASK", listaTareas.get(taskIndex).toString());
                TaskListAdapter tlAdapter = new TaskListAdapter(listaTareas,
                        new TaskListAdapter.OnItemClickListener(){
                            @Override
                            public void onItemClick(Task item) {
                                clickonItem(item, getIndexOfTask(item));
                            }
                        });

                listaTareaView.setAdapter(tlAdapter);
            }
        }
    }


    /**
     * Rellenará la lista de tareas
     * (HASTA EL MOMENTO HARDCODEADO)
     */
    private void rellenarListaTareas(){
        listaTareas = new ArrayList<Task>();
        Task t1= new Task("Hacer tarea de SI","26/8/2020","");
        Task t2= new Task("Comprar patatas, huevos, leche, cereales y manzanas","26/8/2020","");

        System.out.println(tarea);
        listaTareas.add( t1);
        listaTareas.add( t2);
    }

    public void crearNuevaTarea(View view) {
        Intent intent=new Intent(TaskRecycler.this, NewTask.class);
        startActivityForResult(intent, GESTION_ACTIVITY);
    }

    public void editTask(View view) {
        Intent intent = new Intent(TaskRecycler.this, MainActivity.class);
        startActivityForResult(intent, GESTION_ACTIVITY);
    }

}