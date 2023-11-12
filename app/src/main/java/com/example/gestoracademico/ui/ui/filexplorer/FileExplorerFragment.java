package com.example.gestoracademico.ui.ui.filexplorer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestoracademico.MyAdapter;
import com.example.gestoracademico.R;
import com.example.gestoracademico.databinding.FragmentFileExplorerBinding;

import java.io.File;


public class FileExplorerFragment extends Fragment {

    private FragmentFileExplorerBinding binding;

    private RecyclerView fileList;
    private TextView noFilesText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FileExplorerViewModel fileExplorerViewModel =
                new ViewModelProvider(this).get(FileExplorerViewModel.class);

        binding = FragmentFileExplorerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fileList = root.findViewById(R.id.files_recycler_view);
        noFilesText = root.findViewById(R.id.nofiles_textview);

        String path = Environment.getExternalStorageDirectory().getPath();

        File direction = new File(path);
        File[] filesAndFolders = direction.listFiles();

        if(filesAndFolders==null || filesAndFolders.length ==0){
            noFilesText.setVisibility(View.VISIBLE);
            return root;
        }else{
            noFilesText.setVisibility(View.INVISIBLE);

            fileList.setLayoutManager(new LinearLayoutManager(getContext()));
            fileList.setAdapter(new MyAdapter(getContext(),filesAndFolders));
            return root;
        }

//        if(checkPermission()){
//            //permission allowed
//            Toast.makeText(getContext(),"Con permisos",Toast.LENGTH_SHORT).show();
//        }else{
//            //permission not allowed
//            requestPermission();
//            Toast.makeText(getContext(),"Sin permisos",Toast.LENGTH_SHORT).show();
//
//        }

    }


//    private boolean checkPermission(){
//        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(result == PackageManager.PERMISSION_GRANTED){
//            return true;
//        }else
//            return false;
//    }
//
//    private void requestPermission(){
//        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//            Toast.makeText(getContext(),"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
//        }else
//            ActivityCompat.requestPermissions((Activity) getContext(),new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
//    }


    public RecyclerView getFileList() {
        return fileList;
    }

    public void setFileList(RecyclerView fileList) {
        this.fileList = fileList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}