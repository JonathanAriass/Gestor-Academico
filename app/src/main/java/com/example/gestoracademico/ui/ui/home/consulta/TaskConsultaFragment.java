package com.example.gestoracademico.ui.ui.home.consulta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gestoracademico.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskConsultaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskConsultaFragment extends Fragment {

    private static final String ARG_TITULO = "titulo";
    private static final String ARG_DATE = "fecha";

    private String titulo;
    private String fecha;

    public TaskConsultaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titulo = getArguments().getString(ARG_TITULO);
            fecha = getArguments().getString(ARG_DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_consulta, container, false);
        TextView tituloText = view.findViewById(R.id.textView);
        tituloText.setText(titulo);
        adaptDate(view);
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Metodo que cambiar el color de la fecha dependiendo de la prioridad
     * que tenga asignada:
     *  - Rojo: alta
     *  - Amarillo: media
     *  - Verde: baja
     */
    private void setPriorityColor() {

    }

    /**
     * Metodo que adapta la fecha para mostrarlo en el visor de la fecha
     * el cual muestra la informacion con el siguiente formato:
     *  - Dia
     *  - Mes
     *
     */
    private void adaptDate(View view) {
        Log.i("FECHAS", fecha);
        String[] dateParts = fecha.split("/");
        String[] mesesAbreviados = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};

        String dia = dateParts[0];
        String mes = mesesAbreviados[Integer.parseInt(dateParts[1])-1];
        TextView diaText = view.findViewById(R.id.diaTxt);
        diaText.setText(dia);
        TextView mesText = view.findViewById(R.id.mesTxt);
        mesText.setText(mes);
    }
}