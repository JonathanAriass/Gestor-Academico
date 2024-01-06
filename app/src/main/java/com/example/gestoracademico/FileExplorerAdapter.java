package com.example.gestoracademico;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestoracademico.datos.AppDatabase;


/**
 * Adapter para el RecyclerView
 *  del explorador de archivos
 */
public class FileExplorerAdapter extends RecyclerView.Adapter<FileExplorerAdapter.ViewHolder>{

    Context context;
    File[] filesAndFolders;

    public FileExplorerAdapter(Context context, File[] filesAndFolders){
        this.context = context;
        this.filesAndFolders = filesAndFolders;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.explorer_line,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileExplorerAdapter.ViewHolder holder, int position) {

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
                    Bundle args = new Bundle();
                    //Añado la variable path al bundle. O las que hicieran falta
                    args.putString("files", "no");
                    args.putString("path", selectedFile.getAbsolutePath());
                    args.putString("first","no");
                    //Recupero la navegación y especifico la acción (la definida en el paso anterior) pasándole el bundle.
                    Navigation.findNavController(v).navigate(R.id.action_fileExporer_to_fileExporer, args);

                }else{
                    //open the file
                    try {
                        String ar = String.valueOf(Uri.parse(selectedFile.getAbsolutePath()));
                        Bundle args = new Bundle();
                        args.putString("path", selectedFile.getAbsolutePath());
                        args.putString("files", "yes");
                        args.putString("first","no");
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
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.getMenu().add("Eliminar");

                //Si lo seleccionado es un archivo
                if(!selectedFile.isDirectory()){
                    popupMenu.getMenu().add("Compartir");

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("Eliminar")){
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Eliminar archivo");
                                alert.setMessage("¿Estás seguro de que quieres eliminar el archivo?");
                                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean deleted = selectedFile.delete();
                                        if(deleted){
                                            AppDatabase db = AppDatabase.getDatabase(context);

                                            //Eliminamos de la base de datos tantos las tareas asociadas como los documentos
                                            if(db.getFileDAO().getByPath(selectedFile.getAbsolutePath()) != null){
                                                int id = db.getFileDAO().getByPath(selectedFile.getAbsolutePath()).getId();
                                                db.getTaskDAO().deleteByFileID(id);
                                                db.getFileDAO().deleteByID(id);
                                            }

                                            Toast.makeText(context.getApplicationContext(),"Documento eliminado con éxito",Toast.LENGTH_SHORT).show();
                                            v.setVisibility(View.GONE);
                                        }
                                        // close dialog
                                        dialog.cancel();
                                    }
                                });
                                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // close dialog
                                        dialog.cancel();
                                    }
                                });
                                alert.show();

                            }
                            if(item.getTitle().equals("Compartir")){
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.setType("text/plain");

                                if(intent.resolveActivity(context.getPackageManager()) != null){
                                    context.startActivity(intent);
                                }
                            }
                            return true;
                        }
                    });

                    popupMenu.show();
                    return true;

                }else{ // si lo seleccionado es un directorio
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("Eliminar")){
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Eliminar directorio");
                                alert.setMessage("¿Estás seguro de que quieres eliminar el directorio?, se eliminará todo el contenido");
                                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        //Eliminaremos los archivos el directorio
                                        // y su vez de la base de datos si los tuviera
                                        File[] files = selectedFile.listFiles();
                                        for (int i = 0; i < files.length; i++) {
                                            boolean deleted = files[i].delete();
                                            if (deleted) {
                                                AppDatabase db = AppDatabase.getDatabase(context);


                                                if (db.getFileDAO().getByPath(files[i].getAbsolutePath()) != null) {
                                                    int id = db.getFileDAO().getByPath(files[i].getAbsolutePath()).getId();
                                                    db.getTaskDAO().deleteByFileID(id);
                                                    db.getFileDAO().deleteByID(id);
                                                }

                                                Toast.makeText(context.getApplicationContext(), "Documento eliminado con éxito", Toast.LENGTH_SHORT).show();
                                                v.setVisibility(View.GONE);
                                            }
                                        }
                                        boolean deleted = selectedFile.delete();
                                        if(deleted){
                                            Toast.makeText(context.getApplicationContext(), "Directorio eliminado con éxito", Toast.LENGTH_SHORT).show();
                                            v.setVisibility(View.GONE);
                                        }

                                        // close dialog
                                        dialog.cancel();
                                    }
                                });
                                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // close dialog
                                        dialog.cancel();
                                    }
                                });
                                alert.show();

                            }
                            return true;
                        }
                    });

                    popupMenu.show();
                    return true;
                }

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
