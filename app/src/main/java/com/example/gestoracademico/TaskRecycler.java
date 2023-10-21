package com.example.gestoracademico;

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

    public static final String TAREA_CREADA = "tarea_creada";

    private static final int GESTION_ACTIVITY = 1;

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

        TaskListAdapter tlAdapter= new TaskListAdapter(listaTareas,
                new TaskListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Task tarea) {
                        clickonItem(tarea);
                    }
                });
        listaTareaView.setAdapter(tlAdapter);
    }

    public void clickonItem (Task peli){

        Intent intent=new Intent (TaskRecycler.this, MainActivity.class);
        intent.putExtra(TAREA_SELECCIONADA, peli);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

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
}