package com.example.gestoracademico.ui.ui.home;


import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.MainActivity;
import com.example.gestoracademico.R;
import com.example.gestoracademico.TaskListAdapter;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeToDeleteCallback.SwipeToDeleteListener {

    public static final String TAREA_SELECCIONADA = "tarea_seleccionada";
    public static final String TAREA_CREADA = "tarea_creada";
    private static final int GESTION_ACTIVITY = 1;
    List<Task> listaTareas =  new ArrayList<Task>();
    RecyclerView listaTareaView;
    private AppDatabase appDatabase;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_home, container, false);

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        // Obtenemos la instancia de la base de datos
        appDatabase = AppDatabase.getDatabase(getContext());

        listaTareaView = root.findViewById(R.id.reciclerView);
        listaTareaView.setHasFixedSize(true);

        listaTareaView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapter para la lista de tareas
        TaskListAdapter tlAdapter= new TaskListAdapter(listaTareas,
                new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task tarea) {
                clickonItem(tarea);
            }
        });
        listaTareaView.setAdapter(tlAdapter);
        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
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

    @Override
    public void onResume() {
        super.onResume();

        listaTareas = appDatabase.getTaskDAO().getAllOrderByPriority();

        TaskListAdapter tlAdapter= new TaskListAdapter(listaTareas,
                new TaskListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Task tarea) {
                        clickonItem(tarea);
                    }
                });
        listaTareaView.setAdapter(tlAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        itemTouchHelper.attachToRecyclerView(listaTareaView);
    }

    /**
     * Metodo que lanza la activity main para que se visualice el contenido en el fragment de consulta
     * de tarea
     * @param tarea Tarea que debera de ser leida
     */
    public void clickonItem (Task tarea){
        Intent intent=new Intent (getActivity(), MainActivity.class);
        intent.putExtra(TAREA_SELECCIONADA, tarea);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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