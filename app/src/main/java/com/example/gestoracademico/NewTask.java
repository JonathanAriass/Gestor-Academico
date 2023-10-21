package com.example.gestoracademico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gestoracademico.modelo.Task;

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
        task = new Task(editDescripcion.getText().toString(),editFecha.getText().toString());

        Intent intentResultado = new Intent();
        intentResultado.putExtra(TaskRecycler.TAREA_CREADA,task);
        setResult(RESULT_OK,intentResultado);
        finish();

    }
}
