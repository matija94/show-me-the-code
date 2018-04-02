package com.matija.spendless.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.matija.spendless.R;
import com.matija.spendless.preferences.SpendLessPreferences;
import com.matija.spendless.ui.dialogs.NewTransactionDialogFragment;

public class MainActivity extends AppCompatActivity {

    private EditText remainingMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Log.d("MainActivity", "FaB clicked!");
            NewTransactionDialogFragment transactionDialogFragment = new NewTransactionDialogFragment();
            transactionDialogFragment.show(getSupportFragmentManager(), "transactionDialogFragment");
        });

        remainingMoney = (EditText) findViewById(R.id.remainingMoney);
        remainingMoney.setText(Integer.toString(SpendLessPreferences.getRemainingDailySpendings(this)));
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
            case R.id.action_savings:
                // TODO: start savings activity
                break;
            case R.id.action_categories:
                i = new Intent(this, CategoriesActivity.class);
                startActivity(i);
                break;
            case R.id.action_transactions:
                i = new Intent(this,TransactionsActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
