package com.example.gestoracademico.ui.ui.home.consulta;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestoracademico.R;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskConsultaFragment} factory method to
 * create an instance of this fragment.
 */
public class TaskConsultaFragment extends Fragment {

    private static final String ARG_ID = "id";
    private static final String ARG_TITULO = "titulo";
    private static final String ARG_DATE = "fecha";

    private static final String ARG_FILEID = "fileID";

    private static final String ARG_PRIORIDAD = "prioridad";
    private static final String ARG_NOTA = "nota";

    private int id;
    private String titulo;
    private String fecha;
    private int documentID;
    private int prioridad;
    private String nota;

    private @DrawableRes int roundedSquareBackground = R.drawable.box;


    public TaskConsultaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenemos los argumentos del bundle
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
            titulo = getArguments().getString(ARG_TITULO);
            fecha = getArguments().getString(ARG_DATE);
            documentID = getArguments().getInt(ARG_FILEID);
            prioridad = getArguments().getInt(ARG_PRIORIDAD);
            nota = getArguments().getString(ARG_NOTA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_consulta, container, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide the keyboard
                hideKeyboard(view);
            }
        });

        TextView tituloText = view.findViewById(R.id.textView);
        tituloText.setText(titulo);

        // Comprueba si el documento esta conectado
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

        Button btSave = view.findViewById(R.id.btSaveNote);

        EditText edTextNota = view.findViewById(R.id.edTextNota);
        edTextNota.setText(nota);
        edTextNota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Comprobamos si el texto cambia respecto a la version anterior y cambiamos el
                // estado del boton enabled dependiendo
                btSave.setEnabled(hasNewValue(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDatabase(getContext());
                int updated = db.getTaskDAO().updateTaskNote(edTextNota.getText().toString(), id);
                if (updated > 0) {
                    Toast.makeText(getContext(), "Nota actualizada correctamente.", Toast.LENGTH_SHORT).show();
                    btSave.setEnabled(false);
                    nota = edTextNota.getText().toString();
                } else {
                    Toast.makeText(getContext(), "No se ha actualizado la nota, pruebe de nuevo.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Metodo que comprueba si el string de entrada coincide con el almacenado en la base de datos
     * @param string String a comparar con la base de datos
     * @return true si cambia el valor, false si es el mismo
     */
    private boolean hasNewValue(String string) {
        return !nota.equals(string);
    }

    /**
     * Metodo que adapta la fecha para mostrarlo en el visor de la fecha
     * el cual muestra la informacion con el siguiente formato:
     *  - Dia
     *  - Mes
     *
     */
    private void adaptDate(View view) {
        String[] dateParts = fecha.split("/");
        String[] mesesAbreviados = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};

        String dia = dateParts[0];
        String mes = mesesAbreviados[Integer.parseInt(dateParts[1])-1];
        TextView diaText = view.findViewById(R.id.diaTxt);
        diaText.setText(dia);
        TextView mesText = view.findViewById(R.id.mesTxt);
        mesText.setText(mes);

        TextView backgroundTextView = view.findViewById(R.id.textView4);

        int newColor = Color.parseColor("#ffffff");
        switch (prioridad) {
            case 0:
                newColor = Color.parseColor("#7484cf");
                diaText.setTextColor(Color.WHITE);
                mesText.setTextColor(Color.WHITE);
                break;
            case 1:
                newColor = Color.parseColor("#d1db86");
                break;
            case 2:
                newColor = Color.parseColor("#d68181");
                break;
            case 9:
                newColor = Color.parseColor("#6750A4");
                diaText.setTextColor(Color.WHITE);
                mesText.setTextColor(Color.WHITE);
                break;
        }

        int strokeColor = Color.parseColor("#000000");

        Drawable roundedDrawable = getRoundedDrawable(newColor);
        backgroundTextView.setBackground(roundedDrawable);

        mesText.bringToFront();
        diaText.bringToFront();
    }

    /**
     * Metodo para el cambio de color del drawable del dia y mes
     * @param color Color seleccionado
     * @return Instancia de un objeto Drawable
     */
    private Drawable getRoundedDrawable(int color) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), roundedSquareBackground);
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC);

        }
        return drawable;
    }

}