package com.example.gestoracademico.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

@Entity(tableName = "tasks")
public class Task implements Parcelable {

    private String descripcion;
    private String fecha;
    private int prioridad;

    private String nota;

    @Nullable
    private int fk_pdf;


    @PrimaryKey @NonNull
    public int id;



    public Task(int id, String descripcion, String fecha, int prioridad, int fk_pdf, Optional<String> nota) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;

        this.prioridad = prioridad;
        this.fk_pdf = fk_pdf;
        this.nota = nota.orElse("");
    }

    public Task() {

    }

    protected Task(Parcel in) {
        descripcion = in.readString();
        fecha = in.readString();
        prioridad = in.readInt();
        fk_pdf = in.readInt();
        nota = in.readString();
    }


    public int getId() {
        return id;
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

    public int getFk_pdf() {
        return fk_pdf;
    }

    public void setFk_pdf(int fk_pdf) {
        this.fk_pdf = fk_pdf;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(descripcion);
        dest.writeString(fecha);
        dest.writeInt(prioridad);
        dest.writeValue(fk_pdf);
        dest.writeValue(nota);
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
                "descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", prioridad=" + prioridad +
                ", nota='" + nota + '\'' +
                ", fk_pdf=" + fk_pdf +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return prioridad == task.prioridad && fk_pdf == task.fk_pdf && id == task.id && Objects.equals(descripcion, task.descripcion) && Objects.equals(fecha, task.fecha);
    }


}
