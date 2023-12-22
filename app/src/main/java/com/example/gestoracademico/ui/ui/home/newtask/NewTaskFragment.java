package com.example.gestoracademico.ui.ui.home.newtask;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestoracademico.R;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;
import com.example.gestoracademico.ui.ui.home.newtask.dialog.DatePickerFragment;

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

                EditText editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);
                EditText editTextDate2 = view.findViewById(R.id.editTextDate2);
                // Comprobar datos correctos
                // Guardar en la base de datos
                AppDatabase db = AppDatabase.getDatabase(getContext());
                Task task = new Task(db.getTaskDAO().getLastId() + 1, editTextTextMultiLine.getText().toString(), editTextDate2.getText().toString(), 0, 0);

                db.getTaskDAO().add(task);
                // Mostar snackbar con mensaje de creacion de tarea correcta
                showToast("Tarea creada correctamente");
                // Volver a la vista de home
                Bundle args = new Bundle();
                //Recupero la navegación y especifico la acción (la definida en el paso anterior) pasándole el bundle.
                Navigation.findNavController(view).navigate(R.id.action_newTask_to_home, args);
            }
        });
        return view;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}