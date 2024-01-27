package com.example.gestoracademico;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.modelo.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>  {

    private List<Task> listaTareas;
    private final OnItemClickListener listener;


    public TaskListAdapter(List<Task> tareas, OnItemClickListener listener) {
        this.listaTareas = tareas;
        this.listener = listener != null ? listener : null;
    }

    public TaskListAdapter(List<Task> tareas) {
        this.listaTareas = tareas;
        listener = null;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_recycler_view_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task tareaActual= listaTareas.get(position);
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
        private ImageView imagen;

        public TaskViewHolder(View itemView) {
            super(itemView);
            descripcion= (TextView)itemView.findViewById(R.id.descripcionTarea);
            fecha= (TextView)itemView.findViewById(R.id.fechaTarea);
            imagen= (ImageView)itemView.findViewById(R.id.imagen);
        }



        public void asignarValoresComponentes(Task tarea, OnItemClickListener listener) {
            descripcion.setText(tarea.getDescripcion());

            fecha.setText(tarea.getFecha().toString());

            //Picasso.get().load(tarea.getImagen()).into(imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // Se debera de abrir el modo consulta
                    Bundle args = new Bundle();

                    args.putInt("id", tarea.getId());
                    args.putString("titulo", tarea.getDescripcion());
                    args.putString("fecha", tarea.getFecha());
                    args.putInt("fileID", tarea.getFk_pdf());
                    args.putInt("prioridad", tarea.getPrioridad());
                    args.putString("nota", tarea.getNota());
                    //Recupero la navegación y especifico la acción (la definida en el paso anterior) pasándole el bundle.
                    Navigation.findNavController(v).navigate(R.id.action_home_to_consultaTask, args);
                }
            });

            if (tarea.getPrioridad() >= 0 && tarea.getPrioridad() < 3) {
                int newColor = Color.parseColor("#ffffff");
                switch (tarea.getPrioridad()) {
                    case 0:
                        newColor = Color.parseColor("#7484cf");
                        break;
                    case 1:
                        newColor = Color.parseColor("#d1db86");
                        break;
                    case 2:
                        newColor = Color.parseColor("#d68181");
                        break;
                }
                imagen.setColorFilter(newColor, PorterDuff.Mode.SRC_IN);
            } else {
                imagen.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
            }
        }
    }
}
