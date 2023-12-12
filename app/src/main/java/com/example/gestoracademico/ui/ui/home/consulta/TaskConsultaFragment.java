package com.example.gestoracademico.ui.ui.home.consulta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestoracademico.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskConsultaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskConsultaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskConsultaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("titulo");
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_consulta, container, false);
//        String titulo = savedInstanceState.getString("titulo");
        Log.i("TITULOTAREA", mParam1);
        // Inflate the layout for this fragment
        return view;
    }
}