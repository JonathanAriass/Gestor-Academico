package com.example.gestoracademico;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * Adapter para la lista de tareas del calendario
 */
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

        return new TaskViewHolder(itemView, parent.getContext());
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

    public void removeItem(int position) {
        listaTareas.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * Clase interna que contiene los componentes de la vista
     */
    public class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView descripcion;
        private TextView fecha;

        private Context context;

        public TaskViewHolder(View itemView, Context context) {
            super(itemView);

            descripcion= (TextView)itemView.findViewById(R.id.descripcionTareaCalendar);
            fecha= (TextView)itemView.findViewById(R.id.fechaTareaCalendar);
            this.context = context;
        }


        /**
         * Dará el valor de la descripción y de la fecha
         * Y controlará el eliminar una tarea
         * @param tarea
         * @param listener
         */
        public void asignarValoresComponentes(Task tarea, OnItemClickListener listener) {
            descripcion.setText(tarea.getDescripcion());

            fecha.setText(tarea.getFecha().toString());

            FloatingActionButton btnDelete = itemView.findViewById(R.id.eliminarTarea);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    AppDatabase db = AppDatabase.getDatabase(context);
                    if(db.getTaskDAO().getTaskWithDocument().contains(tarea)){
                        db.getFileDAO().deleteByID(tarea.getFk_pdf());
                    }
                    db.getTaskDAO().delete(tarea);
                    Toast.makeText(context, "Tarea eliminada correctamente", Toast.LENGTH_SHORT).show();
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        removeItem(adapterPosition);
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("CLICKITEM", tarea.toString());
                    // Se debera de abrir el modo consulta
                    Bundle args = new Bundle();

                    args.putString("titulo", tarea.getDescripcion());
                    args.putString("fecha", tarea.getFecha());
                    args.putInt("fileID", tarea.getFk_pdf());
                    args.putInt("prioridad", tarea.getPrioridad());
                    //Recupero la navegación y especifico la acción (la definida en el paso anterior) pasándole el bundle.
                    Navigation.findNavController(v).navigate(R.id.action_calendar_to_consultaTask, args);
                }
            });
        }
    }
}
