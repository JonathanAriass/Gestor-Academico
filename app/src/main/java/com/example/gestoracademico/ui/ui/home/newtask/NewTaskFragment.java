package com.example.gestoracademico.ui.ui.home.newtask;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestoracademico.R;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        btSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);
                EditText editTextDate2 = view.findViewById(R.id.editTextDate2);
                // Comprobar datos correctos
                // Guardar en la base de datos
                AppDatabase db = AppDatabase.getDatabase(getContext());
                Task task = new Task(db.getTaskDAO().getLastId() + 1, editTextTextMultiLine.getText().toString(), editTextDate2.getText().toString());

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