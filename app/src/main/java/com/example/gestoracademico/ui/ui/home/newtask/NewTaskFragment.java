package com.example.gestoracademico.ui.ui.home.newtask;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestoracademico.R;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;
import com.example.gestoracademico.ui.ui.home.newtask.dialog.DatePickerFragment;

import java.util.Optional;

public class NewTaskFragment extends Fragment {

    public NewTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);
        // Inflate the layout for this fragment
        Button btSave = view.findViewById(R.id.btGuardar);
        Log.i("BtSave", btSave.toString());

        Spinner dpPrioridad = view.findViewById(R.id.dpPrioridad);

        // Spinner para seleccionar la prioridad de la tarea
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(getContext(), R.array.prioridades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        dpPrioridad.setAdapter(adapter);

        // EditText siendo este el selector de la fecha de la tarea
        EditText etPlannedDate = view.findViewById(R.id.editTextDate2);
        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        final String selectedDate = day + "/" + (month+1) + "/" + year;
                        etPlannedDate.setText(selectedDate);
                    }
                });

                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView txViewTitle = view.findViewById(R.id.txTitulo);
                EditText editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);
                TextView txViewDate = view.findViewById(R.id.txTitulo2);
                EditText editTextDate2 = view.findViewById(R.id.editTextDate2);
                Spinner dpPrioridad = view.findViewById(R.id.dpPrioridad);
                EditText editTextNota = view.findViewById(R.id.editTextTextMultiLine2);
                // Comprobar datos correctos
                if (isValidData(editTextTextMultiLine.getText().toString(), editTextDate2.getText().toString())) {
                    // Guardar en la base de datos
                    AppDatabase db = AppDatabase.getDatabase(getContext());
                    Task task = new Task(db.getTaskDAO().getLastId() + 1, editTextTextMultiLine.getText().toString(), editTextDate2.getText().toString(), getPrioridad(dpPrioridad), 0, Optional.of(String.valueOf(editTextNota.getText())));

                    db.getTaskDAO().add(task);
                    // Mostar snackbar con mensaje de creacion de tarea correcta
                    showToast("Tarea creada correctamente");
                    // Volver a la vista de home
                    Bundle args = new Bundle();
                    //Recupero la navegación y especifico la acción (la definida en el paso anterior) pasándole el bundle.
                    Navigation.findNavController(view).navigate(R.id.action_newTask_to_home, args);
                } else {
                    showToast("Datos invalidos, pruebe de nuevo.");
                    highlightInvalidFields(editTextTextMultiLine, txViewTitle, editTextDate2, txViewDate);
                }
            }
        });
        return view;
    }

    private boolean isValidData(String title, String date) {
        return !title.isEmpty() && !date.isEmpty();
    }

    private void highlightInvalidFields(EditText editTextTextMultiLine, TextView title, EditText editTextDate2, TextView date) {
        int red = android.R.color.holo_red_light;
        int black = android.R.color.black;
        if (TextUtils.isEmpty(editTextTextMultiLine.getText().toString())) {
            highlightEditText(editTextTextMultiLine, title, red);
        } else {
            highlightEditText(editTextTextMultiLine, title, black);
        }

        if (TextUtils.isEmpty(editTextDate2.getText().toString())) {
            highlightEditText(editTextDate2, date, red);
        } else {
            highlightEditText(editTextDate2, date, black);
        }

    }

    /**
     * Metodo para cambiar el color del conjunto de input (EditText y TextView)
     * @param editText
     * @param color
     */
    private void highlightEditText(EditText editText, TextView textView, int color) {
        int highlightColor = ContextCompat.getColor(getContext(), color);
        editText.getBackground().setColorFilter(highlightColor, PorterDuff.Mode.SRC_ATOP);
//        textView.getBackground().setColorFilter(highlightColor, PorterDuff.Mode.SRC_ATOP);
        textView.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    /**
     * Metodo que obtiene la prioridad de la tarea a crear
     * @param dpPrioridad Objeto Spinner del que obtener la informacion de la prioridad
     * @return Numero de la prioridad (0: baja, 1: media, 2: alta) (0 por defecto)
     */
    private int getPrioridad(Spinner dpPrioridad) {
        return dpPrioridad.getSelectedItemPosition();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}