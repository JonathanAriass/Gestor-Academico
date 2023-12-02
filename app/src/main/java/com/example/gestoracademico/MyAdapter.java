package com.example.gestoracademico;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.ui.ui.filexplorer.FileExplorerFragment;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    Context context;
    File[] filesAndFolders;

    public MyAdapter(Context context, File[] filesAndFolders){
        this.context = context;
        this.filesAndFolders = filesAndFolders;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.explorer_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        File selectedFile = filesAndFolders[position];
        holder.textView.setText(selectedFile.getName());

        if(selectedFile.isDirectory()){
            holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);
        }else{
            holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedFile.isDirectory()){
//                    Intent intent = new Intent(context, FileExplorerFragment.class);
//                    FileExplorerFragment fragment = new FileExplorerFragment();
//                    Bundle bundle = new Bundle();
//                    String path = selectedFile.getAbsolutePath();
//                    intent.putExtra("path",path);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                    // TODO: como se puede hacer que cuando se haga click en una linea se abra dicha
//                    //       parte con la nueva informacion.

                    Bundle args = new Bundle();
                    //Añado la variable path al bundle. O las que hicieran falta
                    args.putString("files", "no");
                    args.putString("path", selectedFile.getAbsolutePath());
                    //Recupero la navegación y especifico la acción (la definida en el paso anterior) pasándole el bundle.
                    Navigation.findNavController(v).navigate(R.id.action_fileExporer_to_fileExporer, args);

                }else{
                    //open thte file
                    try {
//                        Intent intent = new Intent();
//                        intent.setAction(android.content.Intent.ACTION_VIEW);
//                        String type = "image/*";
//                        intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()), type);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
                        String ar = String.valueOf(Uri.parse(selectedFile.getAbsolutePath()));
                        Bundle args = new Bundle();
                        args.putString("path", selectedFile.getAbsolutePath());
                        args.putString("files", "yes");

                        Navigation.findNavController(v).navigate(R.id.action_fileExporer_to_pdfViewer, args);

                    }catch (Exception e){
                        Toast.makeText(context.getApplicationContext(),"Cannot open the file",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(!selectedFile.isDirectory()){
                    PopupMenu popupMenu = new PopupMenu(context,v);
                    popupMenu.getMenu().add("Eliminar");
                    popupMenu.getMenu().add("Compartir");

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("Eliminar")){
                                boolean deleted = selectedFile.delete();
                                if(deleted){
                                    Toast.makeText(context.getApplicationContext(),"DELETED ",Toast.LENGTH_SHORT).show();
                                    v.setVisibility(View.GONE);
                                }
                            }
                            if(item.getTitle().equals("Compartir")){
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                Toast.makeText(context.getApplicationContext(),"SHARED",Toast.LENGTH_SHORT).show();

                                if(intent.resolveActivity(context.getPackageManager()) != null){
                                    context.startActivity(intent);
                                }
                            }
                            return true;
                        }
                    });

                    popupMenu.show();
                    return true;
                }
                return true;

            }
        });


    }

    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.file_name_text_view);
            imageView = itemView.findViewById(R.id.icon_view);
        }
    }
}
