package com.example.gestoracademico.ui.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.MainActivity;
import com.example.gestoracademico.R;
import com.example.gestoracademico.TaskListAdapter;
import com.example.gestoracademico.TaskRecycler;
import com.example.gestoracademico.databinding.FragmentHomeBinding;
import com.example.gestoracademico.modelo.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

//    private FragmentHomeBinding binding;

    public static final String TAREA_SELECCIONADA = "tarea_seleccionada";

    public static final String TAREA_CREADA = "tarea_creada";

    private static final int GESTION_ACTIVITY = 1;
    List<Task> listaTareas;
    Task tarea;
    RecyclerView listaTareaView;

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

//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ArrayList<Task> listaTareas =  new ArrayList<Task>();
        Task t1= new Task("Hacer tarea de SI","26/8/2020","");
        Task t2= new Task("Comprar patatas, huevos, leche, cereales y manzanas","26/8/2020","");

        listaTareas.add( t1);
        listaTareas.add( t2);
        TaskListAdapter tlAdapter= new TaskListAdapter(listaTareas, new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task tarea) {

                Log.i("Click en tarea", "Click");
                clickonItem(tarea);
            }
        });
        recyclerView.setAdapter(tlAdapter);
        tlAdapter.notifyDataSetChanged();
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void clickonItem (Task peli){

        Intent intent=new Intent (getActivity(), MainActivity.class);
        intent.putExtra(TAREA_SELECCIONADA, peli);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}