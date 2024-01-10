package com.example.gestoracademico;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;

import java.util.Optional;

public class NewTask extends AppCompatActivity {

    private Task task;

    private EditText editDescripcion;
    private EditText editFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_new_task);
        setTitle("Crear tarea");

        editDescripcion = findViewById(R.id.eTDescripcion);
        editFecha = findViewById(R.id.eTFecha);

    }

    public void saveTask(View view) {
        AppDatabase db = AppDatabase.getDatabase(this);
        Task task = new Task(db.getTaskDAO().getLastId() + 1, editDescripcion.getText().toString(), editFecha.getText().toString(), 0, 0, Optional.empty());

        db.getTaskDAO().add(task);

        Log.i("INSERT TASK", task.toString());

        Intent intentResultado = new Intent();
        intentResultado.putExtra(TaskRecycler.TAREA_CREADA,task);
        setResult(RESULT_OK,intentResultado);
        finish();

    }
}
