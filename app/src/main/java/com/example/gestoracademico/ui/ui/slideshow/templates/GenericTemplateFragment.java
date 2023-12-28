package com.example.gestoracademico.ui.ui.slideshow.templates;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gestoracademico.R;
import com.example.gestoracademico.datos.AppDatabase;
import com.example.gestoracademico.modelo.Task;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class GenericTemplateFragment extends Fragment {

    private Button generateButton;

    private EditText title;

    private EditText content;

    private EditText category;

    private Switch createTaskSwitch;
    private EditText dateTask;
    private EditText titleTask;

    public GenericTemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_generic, container, false);

        generateButton = view.findViewById(R.id.buttonGeneratePDF);

        title = view.findViewById(R.id.editTextTitle);

        content = view.findViewById(R.id.editTextContent);

        category = view.findViewById(R.id.editTextCategoria);

        createTaskSwitch = view.findViewById(R.id.createTaskWithDocumentGeneric);

        dateTask = view.findViewById(R.id.dateTaskfromDocumentGeneric);
        titleTask = view.findViewById(R.id.titleTaskfromDocumentGeneric);


        createTaskSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dateTask.setVisibility(View.VISIBLE);
                    titleTask.setVisibility(View.VISIBLE);
                }else{
                    dateTask.setVisibility(View.INVISIBLE);
                    dateTask.setText("");
                    titleTask.setVisibility(View.INVISIBLE);
                    titleTask.setText("");
                }
            }
        });


        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validaciones inputs
                if(title.getText().toString().isEmpty()){
                    showToast("El campo del titulo no puede ser vacio");
                    return;
                }
                if(content.getText().toString().isEmpty()){
                    showToast("El campo del contenido no puede ser vacio");
                    return;
                }

                String fileName = title.getText().toString() + ".pdf";
                Document document = new Document();

                String userFolder = category.getText().toString().toUpperCase();;


                if(checkInputs()){
                    try {
                        String carpeta = "/pdf";
                        String path = getContext().getExternalFilesDir("") + carpeta +"/"+ userFolder;

                        // Directorio interno donde se guardará el archivo
                        File dir = new File(path);
                        if(!dir.exists()){
                            dir.mkdirs();
                        }

                        // Ruta completa del archivo
                        File file = new File(dir, fileName);
                        PdfWriter.getInstance(document, new FileOutputStream(file));
                        document.open();

                        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD | Font.UNDERLINE);
                        Paragraph titleParagraph = new Paragraph(title.getText().toString(), titleFont);
                        document.add(titleParagraph);

                        // Agregar contenido al documento
                        Paragraph contentParagraph = new Paragraph(content.getText().toString());
                        document.add(contentParagraph);

                        document.close();

                        checkCreationTask(file.getAbsolutePath());


                        showToast("Archivo creado correctamente en: " + file.getAbsolutePath());

                        //Al crear el pdf se abrirá directamente
                        Bundle args = new Bundle();
                        args.putString("path", file.getAbsolutePath());
                        args.putString("files", "yes");

                        Navigation.findNavController(v).navigate(R.id.action_Exportar_to_pdfViewer, args);

                    } catch (DocumentException | FileNotFoundException e) {
                        e.printStackTrace();
                        showToast("Error al crear el archivo PDF");
                    }
                }

            }
        });
        return view;
    }

    /**
     * Creará una tarea si la opción del switch está activada
     */
    private void checkCreationTask(String absolutePath) {
        if(createTaskSwitch.isChecked()){
            AppDatabase db = AppDatabase.getDatabase(getContext());

            int fileID = db.getFileDAO().getLastId()+1;

            //Añadir documento a base de datos
            com.example.gestoracademico.modelo.File file = new com.example.gestoracademico.modelo.File(fileID, title.getText().toString(), absolutePath);
            db.getFileDAO().add(file);

            //Añadir tarea a base de datos
            Task task = new Task(db.getTaskDAO().getLastId() + 1, titleTask.getText().toString(), dateTask.getText().toString(), 0, fileID);
            db.getTaskDAO().add(task);

        }
    }

    /**
     * Comprobará que los campos no estén vacíos y que las
     * fechas sean correctas
     * @return
     */
    private boolean checkInputs() {
        if(title.getText().toString().trim().isEmpty()){
            showToast("Añada un título para el documento");
            return false;

        }else if(createTaskSwitch.isChecked()){
            if(titleTask.getText().toString().trim().isEmpty() ||
                    dateTask.getText().toString().trim().isEmpty()){
                /**
                 * TODO: Añadir validación de fecha de tarea válida
                 */
                showToast("Añada un título y fecha para la creación de la tarea");
                return false;
            }
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


}