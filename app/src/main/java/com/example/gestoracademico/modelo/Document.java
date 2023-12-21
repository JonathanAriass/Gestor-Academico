package com.example.gestoracademico.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "document")
public class Document implements Parcelable {

    @PrimaryKey
    @NonNull
    public int id;

    private String titulo;
    private String ruta;

    public Document(int id, String titulo, String ruta) {
        this.id = id;
        this.titulo = titulo;
        this.ruta = ruta;
    }

    public Document(Parcel in) {
        titulo = in.readString();
        ruta = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(ruta);
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", ruta='" + ruta + '\'' +
                '}';
    }
}
