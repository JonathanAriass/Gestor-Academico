package com.example.gestoracademico.ui.ui.pdf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestoracademico.R;
import com.example.gestoracademico.databinding.FragmentPdfBinding;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;


/**
 * Fragmento para el visor de PDFs
 */
public class PdfFragment extends Fragment {

    private FragmentPdfBinding binding;

    private PDFView pdfView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        PdfViewModel pdfViewModel =
                new ViewModelProvider(this).get(PdfViewModel.class);

        binding = FragmentPdfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pdfView = root.findViewById(R.id.pdfView);

        //Recupero el bundle con getArguments()
        Bundle bundle = getArguments();

        String path = Environment.getExternalStorageDirectory().getPath();

        if (bundle != null)
        {
            path = getArguments().getString("path", path);
            File file = new File(path);
            pdfView.fromFile(file).load();
        }


        return root;
    }

}