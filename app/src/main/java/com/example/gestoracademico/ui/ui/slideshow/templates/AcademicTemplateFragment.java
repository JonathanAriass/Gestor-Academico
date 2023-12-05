package com.example.gestoracademico.ui.ui.slideshow.templates;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
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
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }
}