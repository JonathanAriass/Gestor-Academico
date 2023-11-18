package com.example.gestoracademico;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.modelo.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CalendarTaskListAdapter extends RecyclerView.Adapter<CalendarTaskListAdapter.TaskViewHolder>  {

    private List<Task> listaTareas;
    private final OnItemClickListener listener;
    private FloatingActionButton deleteButton;




    public CalendarTaskListAdapter(List<Task> tareas, OnItemClickListener listener) {
        this.listaTareas = tareas;
        this.listener = listener != null ? listener : null;
    }

    public CalendarTaskListAdapter(List<Task> tareas) {
        this.listaTareas = tareas;
        listener = null;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_recycler_calendar_task, parent, false);

        deleteButton = (FloatingActionButton) itemView.findViewById(R.id.eliminarTarea);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("holi");
                /** TODO: Como se podria obtener el id del elemento del recyclerview que esta
                /*        siendo accionado para eliminar la tarea de la base de datos.
                 */
            }
        });

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task tareaActual= listaTareas.get(position);
        Log.i("Lista","Visualiza elemento: "+tareaActual);
        holder.asignarValoresComponentes(tareaActual, listener);
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Task item);
    }



    /**
     * Clase interna que contiene los componentes de la vista
     */
    public class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView descripcion;
        private TextView fecha;
        //private ImageView imagen;

        public TaskViewHolder(View itemView) {
            super(itemView);

            descripcion= (TextView)itemView.findViewById(R.id.descripcionTareaCalendar);
            fecha= (TextView)itemView.findViewById(R.id.fechaTareaCalendar);
           // imagen= (ImageView)itemView.findViewById(R.id.imagen);
        }



        public void asignarValoresComponentes(Task tarea, OnItemClickListener listener) {
            descripcion.setText(tarea.getDescripcion());

            fecha.setText(tarea.getFecha().toString());

            //Picasso.get().load(tarea.getImagen()).into(imagen);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    Log.i("Hola", "Hola");
//                    listener.onItemClick(tarea);
//                }
//            });
        }
    }
}
