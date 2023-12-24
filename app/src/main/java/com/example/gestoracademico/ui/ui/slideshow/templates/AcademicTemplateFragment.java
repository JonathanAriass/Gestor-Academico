package com.example.gestoracademico.ui.ui.slideshow.templates;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gestoracademico.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
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


                try {
                    String carpeta = "/pdf";
                    String path = getContext().getExternalFilesDir("") + carpeta +"/"+ userFolder;
                    // Directorio interno donde se guardará el archivo
                    File dir = new File(path);
                    if(!dir.exists()){
                        dir.mkdirs();
                    }

                    String pathCompleto;

                    // Ruta completa del archivo
                    File file = new File(dir, fileName);
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

                    showToast("Archivo creado correctamente en: " + file.getAbsolutePath());
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                    showToast("Error al crear el archivo PDF");
                }

            }
        });

        return view;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}