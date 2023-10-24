package com.example.gestoracademico;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gestoracademico.modelo.Task;

public class MainActivity extends AppCompatActivity {

    private EditText descriptionInput;
    private EditText fechaInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descriptionInput = findViewById(R.id.eTDescripcion);
        fechaInput = findViewById(R.id.eTFecha);

        Intent intentTask = getIntent();
        Task task = intentTask.getParcelableExtra(TaskRecycler.TAREA_SELECCIONADA);
        int indexTask = intentTask.getIntExtra(TaskRecycler.TAREA_INDEX_SELECCIONADA, 0);
        if (task != null){
            abrirModoConsulta(task);
        }

        Button buttonSave = findViewById(R.id.btnGuardar);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveTask(indexTask);
            }
        });

    }


    public void saveTask(int indexTask){
        Log.i("VALUES:", descriptionInput.getText().toString());
        Task task = new Task(descriptionInput.getText().toString(), fechaInput.getText().toString(), "");

        Intent intentResultado= new Intent();
        intentResultado.putExtra(TaskRecycler.TASK_MODIFIED, task);
        intentResultado.putExtra(TaskRecycler.TASK_MODIFIED_INDEX, indexTask);
        setResult(2, intentResultado);
        finish();
    }


    private void abrirModoConsulta(Task task) {
        if (!task.getDescripcion().isEmpty()) { //apertura en modo consulta
            //Actualizar componentes con valores de la pelicula específica
            descriptionInput.setText(task.getDescripcion());
            fechaInput.setText(task.getFecha());
        }
    }
}