package com.matija.spendless.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.matija.spendless.R;
import com.matija.spendless.preferences.SpendLessPreferences;
import com.matija.spendless.services.SpendingsService;

/**
 * Created by matija on 7.4.18..
 */

public class SpendingsActivity extends AppCompatActivity {

    private EditText dailySpendings;
    private EditText specificDaysSpendings[];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendings);

        initViews();

        dailySpendings.addTextChangedListener(spendingsTextWatcher);
    }

    private final TextWatcher spendingsTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                int spendings = Integer.parseInt(s.toString());
                SpendLessPreferences.setDailySpendings(SpendingsActivity.this, spendings);
                if (!SpendingsService.isAlarmOn(SpendingsActivity.this)) {
                    SpendingsService.setServiceAlarm(SpendingsActivity.this);
                }
            }
        }
    };

    private void initViews() {
        this.dailySpendings = findViewById(R.id.spendings_spendings_daily_value_edit_text);
        this.dailySpendings.setText(Integer.toString(SpendLessPreferences.getDailySpendings(this)));

        int days = 7;
        this.specificDaysSpendings = new EditText[days];
        for(int i=0; i<days; i++) {
            switch (i) {
                case 0: specificDaysSpendings[i] = findViewById(R.id.spendings_spendings_monday_value_edit_text);break;
                case 1: specificDaysSpendings[i] = findViewById(R.id.spendings_spendings_tuesday_value_edit_text);break;
                case 2: specificDaysSpendings[i] = findViewById(R.id.spendings_spendings_wednesday_value_edit_text);break;
                case 3: specificDaysSpendings[i] = findViewById(R.id.spendings_spendings_thursday_value_edit_text);break;
                case 4: specificDaysSpendings[i] = findViewById(R.id.spendings_spendings_friday_value_edit_text);break;
                case 5: specificDaysSpendings[i] = findViewById(R.id.spendings_spendings_saturday_value_edit_text);break;
                case 6: specificDaysSpendings[i] = findViewById(R.id.spendings_spendings_sunday_value_edit_text);break;
            }
        }
    }
}
