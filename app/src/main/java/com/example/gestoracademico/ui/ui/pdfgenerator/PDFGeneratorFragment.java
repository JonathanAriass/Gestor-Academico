package com.example.gestoracademico.ui.ui.pdfgenerator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gestoracademico.ui.ui.pdfgenerator.templates.AcademicTemplateFragment;
import com.example.gestoracademico.ui.ui.pdfgenerator.templates.GenericTemplateFragment;
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

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide the keyboard
                hideKeyboard(root);
            }
        });

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
                    ((GenericTemplateFragment) fragment).generatePDF(v);

                }else if(templateChecked == R.id.radioAcademicTemplate){
                    ((AcademicTemplateFragment) fragment).generatePDF(v);
                }


            }
        });

        return root;
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}