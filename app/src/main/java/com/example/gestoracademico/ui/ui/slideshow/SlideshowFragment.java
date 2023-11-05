package com.example.gestoracademico.ui.ui.slideshow;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.gestoracademico.ui.ui.slideshow.templates.AcademicTemplateFragment;
import com.example.gestoracademico.ui.ui.slideshow.templates.GenericTemplateFragment;
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



    private EditText subject;

    private Button generateButton;

    private RadioGroup templateSelection;

    private FrameLayout fragmentContainer;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fragmentContainer = root.findViewById(R.id.templateContainer);
        templateSelection = root.findViewById(R.id.templateSelection);

        templateSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.radioGenericTemplate:
                        fragment = new GenericTemplateFragment();
                        break;

                    case R.id.radioAcademicTemplate:
                        fragment = new AcademicTemplateFragment();
                        break;
                }
                if (fragment != null) {
                    // Reemplazar el fragmento actual en el contenedor
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(fragmentContainer.getId(), fragment);
                    transaction.commit();
                }


            }
        });

        templateSelection.check(R.id.radioGenericTemplate);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}