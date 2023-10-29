package com.example.gestoracademico.ui.ui.slideshow;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.gestoracademico.R;
import com.example.gestoracademico.databinding.FragmentSlideshowBinding;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    private EditText title;

    private EditText content;

    private Button generateButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        title = root.findViewById(R.id.editTextTitle);

        content = root.findViewById(R.id.editTextContent);

        generateButton = root.findViewById(R.id.buttonGeneratePDF);

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

                    // Agregar contenido al documento
                    Paragraph contentParagraph = new Paragraph(content.getText().toString());
                    document.add(contentParagraph);

                    document.close();
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}