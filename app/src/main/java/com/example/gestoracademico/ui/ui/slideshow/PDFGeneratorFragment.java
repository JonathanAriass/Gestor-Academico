package com.example.gestoracademico.ui.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gestoracademico.ui.ui.slideshow.templates.AcademicTemplateFragment;
import com.example.gestoracademico.ui.ui.slideshow.templates.GenericTemplateFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.gestoracademico.R;
import com.example.gestoracademico.databinding.FragmentSlideshowBinding;

public class PDFGeneratorFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private EditText subject;

    private Button generateButton;

    private RadioGroup templateSelection;

    private FrameLayout fragmentContainer;

    private FloatingActionButton fab;

    private int templateChecked;

    private  Fragment fragment = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PDFGeneratorViewModel slideshowViewModel =
                new ViewModelProvider(this).get(PDFGeneratorViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fragmentContainer = root.findViewById(R.id.templateContainer);
        templateSelection = root.findViewById(R.id.templateSelection);

        templateSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (R.id.radioGenericTemplate == checkedId) {
                    fragment = new GenericTemplateFragment();
                    templateChecked = checkedId;
                } else if (R.id.radioAcademicTemplate == checkedId) {
                    fragment = new AcademicTemplateFragment();
                    templateChecked = checkedId;
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

        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(templateChecked == R.id.radioGenericTemplate){
                        Log.i("prueba","GENERICA");
                    ((GenericTemplateFragment) fragment).generatePDF(v);

                }else if(templateChecked == R.id.radioAcademicTemplate){
                    Log.i("prueba","ACADEMICA");
                    ((AcademicTemplateFragment) fragment).generatePDF(v);
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