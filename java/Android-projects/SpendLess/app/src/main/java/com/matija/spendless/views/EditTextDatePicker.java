package com.matija.spendless.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by matija on 18.3.18..
 */

public class EditTextDatePicker extends AppCompatEditText implements View.OnClickListener {

    private SimpleDateFormat sdf;
    private DatePickerDialog datePickerDialog;

    public EditTextDatePicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initDatePicker(context);
        setOnClickListener(this);
        setInputType(InputType.TYPE_NULL);
    }

    public void setDatePattern(String pattern) {
        sdf = new SimpleDateFormat(pattern);
    }

    @Override
    public void onClick(View view) {
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDatePicker(Context context) {
        datePickerDialog = new DatePickerDialog(context);
        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            Calendar setDate = Calendar.getInstance();
            setDate.set(year,month,dayOfMonth);
            setText(sdf.format(setDate.getTime()));
        });
    }

}
