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


public class GenericTemplateFragment extends Fragment {

    private Button generateButton;

    private EditText title;

    private EditText content;

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
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = title.getText().toString() + ".pdf";
                Document document = new Document();

                try {
                    String carpeta = "/pdf";
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + carpeta;

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
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}