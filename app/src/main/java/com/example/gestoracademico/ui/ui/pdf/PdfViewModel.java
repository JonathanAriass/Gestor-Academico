package com.example.gestoracademico.ui.ui.pdf;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PdfViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PdfViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}