package com.example.gestoracademico.ui.ui.filexplorer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FileExplorerViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FileExplorerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is file explorer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}