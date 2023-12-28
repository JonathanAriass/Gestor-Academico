package com.example.gestoracademico.ui.ui.slideshow.templates;

import android.os.Bundle;
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
import com.example.gestoracademico.modelo.File;
import com.example.gestoracademico.modelo.Task;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Date;


public class AcademicTemplateFragment extends Fragment {

    private Button generateButton;

    private EditText title;

    private EditText content;

    private EditText asignatura;

    private EditText tema;

    private EditText fecha;

    private EditText category;

    private Switch createTaskSwitch;
    private EditText dateTask;
    private EditText titleTask;


    public AcademicTemplateFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_academic_template, container, false);
        generateButton = view.findViewById(R.id.buttonGeneratePDF);

        title = view.findViewById(R.id.editTextTitle);

        content = view.findViewById(R.id.editTextContent);

        asignatura = view.findViewById(R.id.editTextSubject);

        tema = view.findViewById(R.id.editTextTheme);

        fecha = view.findViewById(R.id.editTextDate);

        category = view.findViewById(R.id.editTextCategoria);

        createTaskSwitch = view.findViewById(R.id.createTaskWithDocument);

        dateTask = view.findViewById(R.id.dateTaskfromDocument);
        titleTask = view.findViewById(R.id.titleTaskfromDocument);

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
                if(asignatura.getText().toString().isEmpty()){
                    showToast("El campo asignatura no puede ser vacio");
                    return;
                }
                if(tema.getText().toString().isEmpty()){
                    showToast("El campo tema no puede ser vacio");
                    return;
                }
                if(fecha.getText().toString().isEmpty()){
                    showToast("El campo fecha no puede ser vacio");
                    return;
                }
                String[] fechaArray = fecha.getText().toString().split("/");
                int dia = Integer.parseInt(fechaArray[0]);
                int mes = Integer.parseInt(fechaArray[1]);
                int año = Integer.parseInt(fechaArray[2]);
                if(dia < 1 || dia > 31){
                    showToast("Formato fecha invalido: Introduce un dia entre 1 y 31");
                    return;
                }
                if(mes < 1 || mes > 12){
                    showToast("Formato fecha invalido: Introduce un mes entre 1 y 12");
                    return;
                }
                if(año < 2023 ){
                    showToast("Formato fecha invalido: Año invalido (<2023)");
                    return;
                }
                if(content.getText().toString().isEmpty()){
                    showToast("El campo del contenido no puede ser vacio");
                    return;
                }

                String fileName = title.getText().toString() + ".pdf";
                Document document = new Document();

                String userFolder = category.getText().toString().toUpperCase();

                if(checkInputs()){
                    try {
                        //Comprobación de campos

                            String carpeta = "/pdf";
                            String path = getContext().getExternalFilesDir("") + carpeta +"/"+ userFolder;
                            // Directorio interno donde se guardará el archivo
                            java.io.File dir = new java.io.File(path);
                            if(!dir.exists()){
                                dir.mkdirs();
                            }

                            String pathCompleto;

                            // Ruta completa del archivo
                            java.io.File file = new java.io.File(dir, fileName);
                            PdfWriter.getInstance(document, new FileOutputStream(file));
                            document.open();

                            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD | Font.UNDERLINE);
                            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 15);
                            Paragraph subjectParagraph = new Paragraph("Asignatura: "+ asignatura.getText().toString(), titleFont);
                            document.add(subjectParagraph);

                            Paragraph themeParagraph = new Paragraph("Tema: "+ tema.getText().toString(), titleFont);
                            document.add(themeParagraph);

                            Paragraph dateParagraph = new Paragraph("Fecha: "+ fecha.getText().toString(), titleFont);
                            document.add(dateParagraph);

                            // Agregar contenido al documento
                            Paragraph contentParagraph = new Paragraph(content.getText().toString(),contentFont);
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
            File file = new File(fileID, title.getText().toString(), absolutePath);
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
        }else if(tema.getText().toString().trim().isEmpty()){
            showToast("Añada un tema para el documento");
            return false;
        }else if(fecha.getText().toString().trim().isEmpty()){
            /**
             * TODO: Añadir validación de fecha válida
             */
            showToast("Añada una fecha para el documento");
            return false;
        }else if(asignatura.getText().toString().trim().isEmpty()){
            showToast("Añada una asignatura para el documento");
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