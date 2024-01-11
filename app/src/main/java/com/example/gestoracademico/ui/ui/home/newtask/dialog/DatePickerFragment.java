package com.example.gestoracademico.ui.ui.home.newtask.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

/**
 * Clase para desarrollar e implementar un seleccionador de fechas
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog.OnDateSetListener listener;

    /**
     * Metodo para crear una instancia del dialogo para seleccionar fechas
     * @param listener Listener para seleccionar la fecha
     * @return Instancia del fragmento
     */
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    /**
     * Metodo que asigna el calendario al dialogo
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return Instancia del dialogo
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Locale locale =  new Locale("es", "ES");
        Locale.setDefault(locale);
        final Calendar c = Calendar.getInstance(locale);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
    }

}