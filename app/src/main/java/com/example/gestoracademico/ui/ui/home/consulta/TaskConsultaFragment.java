package com.example.gestoracademico.ui.ui.home.consulta;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestoracademico.R;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskConsultaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskConsultaFragment extends Fragment {

    private static final String ARG_TITULO = "titulo";
    private static final String ARG_DATE = "fecha";

    private static final String ARG_FILEID = "fileID";

    private String titulo;
    private String fecha;

    private int documentID;

    public TaskConsultaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titulo = getArguments().getString(ARG_TITULO);
            fecha = getArguments().getString(ARG_DATE);
            documentID = getArguments().getInt(ARG_FILEID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_consulta, container, false);
        TextView tituloText = view.findViewById(R.id.textView);
        tituloText.setText(titulo);

        //check if task has a file connected
        Button verDocumento = view.findViewById(R.id.fileButton);
        if(documentID != 0){
            verDocumento.setVisibility(View.VISIBLE);
        }
        adaptDate(view);

        verDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDatabase(getContext());
                File file = db.getFileDAO().getById(Integer.toString(documentID));
                String filepath = file.getRuta();

                //open the file
                try {
                    String ar = String.valueOf(Uri.parse(filepath));
                    Bundle args = new Bundle();
                    args.putString("path", filepath);
                    args.putString("files", "yes");

                    Navigation.findNavController(v).navigate(R.id.action_consultaTask_to_pdfViewer, args);

                }catch (Exception e){
                    Toast.makeText(getContext().getApplicationContext(),"Cannot open the file",Toast.LENGTH_SHORT).show();
                }
            }
        });

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