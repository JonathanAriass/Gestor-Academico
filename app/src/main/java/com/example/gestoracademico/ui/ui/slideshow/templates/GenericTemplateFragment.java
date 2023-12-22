package com.example.gestoracademico.ui.ui.slideshow.templates;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

    private EditText category;

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

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = title.getText().toString() + ".pdf";
                Document document = new Document();

                String userFolder = category.getText().toString().toUpperCase();;

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
        });
        return view;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


}