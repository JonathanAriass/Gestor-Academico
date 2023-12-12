package com.example.gestoracademico.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task implements Parcelable {

    @PrimaryKey @NonNull
    public int id;


    private String descripcion;
    private String fecha;

    private String imagen;

//    public Task(int id, String descripcion, String fecha, String imagen) {
//        this.id = id;
//        this.descripcion = descripcion;
//        this.fecha = fecha;
//        this.imagen = imagen;
//    }

    public Task(int id, String descripcion, String fecha) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Task() {

    }

    protected Task(Parcel in) {
        descripcion = in.readString();
        fecha = in.readString();
        imagen = in.readString();
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(descripcion);
        dest.writeString(fecha);
        dest.writeString(imagen);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
