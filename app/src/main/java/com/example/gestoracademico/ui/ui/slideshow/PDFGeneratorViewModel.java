package com.example.gestoracademico.ui.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PDFGeneratorViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PDFGeneratorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}