package com.matija.spendless.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.matija.spendless.R;
import com.matija.spendless.preferences.SpendLessPreferences;
import com.matija.spendless.ui.dialogs.NewTransactionDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText remainingMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createTabs();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Log.d("MainActivity", "FaB clicked!");
            NewTransactionDialogFragment transactionDialogFragment = new NewTransactionDialogFragment();
            transactionDialogFragment.show(getSupportFragmentManager(), "transactionDialogFragment");
        });

        remainingMoney = (EditText) findViewById(R.id.remainingMoney);
        remainingMoney.setText(Integer.toString(SpendLessPreferences.getRemainingDailySpendings(this)));
        remainingMoney.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i = null;
        switch (id) {
            case R.id.action_spendings:
                i = new Intent(this, SpendingsActivity.class);
                break;
            case R.id.action_categories:
                i = new Intent(this, CategoriesActivity.class);
                break;
            case R.id.action_transactions:
                i = new Intent(this,TransactionsActivity.class);
                break;
        }
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    private void createTabs() {
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.spendings_daily)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.spendings_weekly)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.spendings_monthly)));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CharSequence content = tab.getText();
                Integer remainingDailySpendings = SpendLessPreferences.getRemainingDailySpendings(MainActivity.this);
                if (content.equals(getResources().getText(R.string.spendings_daily))) {
                    remainingMoney.setText(Integer.toString(remainingDailySpendings));
                }
                else if (content.equals(getResources().getText(R.string.spendings_weekly))) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -1 ;
                    dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;
                    int remainingWeekly = (7-dayOfWeek)  * SpendLessPreferences.getDailySpendings(MainActivity.this) + remainingDailySpendings;
                    remainingMoney.setText(Integer.toString(remainingWeekly));
                }
                else if (content.equals(getResources().getText(R.string.spendings_monthly))) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int remainingMonthly = (maxDays-dayOfMonth) * SpendLessPreferences.getDailySpendings(MainActivity.this) + remainingDailySpendings;
                    remainingMoney.setText(Integer.toString(remainingMonthly));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
