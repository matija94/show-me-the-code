package android.matija.com.audisrbija;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Button mButtonOrder;
    private Spinner mSpinner;
    private Audi mAudi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAudi = new Audi();

        mImageView = (ImageView) findViewById(R.id.audirs7_image_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.audi_website)));
                startActivity(intent);
            }
        });
        initSpinner();
        addListenerOnSpinnerSeriesSelection();
        initButtonOrder();

    }

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, WelcomeActivity.class);
    }

    public void initButtonOrder() {
        mButtonOrder = (Button) findViewById(R.id.order_button);
        mButtonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = OrderActivityP1.newIntent(WelcomeActivity.this);
                i.putExtra(OrderActivityP1.AUDI_MODEL, mAudi);
                startActivity(i);
            }
        });
    }

    public void initSpinner() {
        mSpinner = (Spinner) findViewById(R.id.spinner_audi_series);
        List<String> series = new ArrayList<>(Arrays.asList(Audi.AudiSeries.A.toString(), Audi.AudiSeries.Q.toString(), Audi.AudiSeries.RS.toString()));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, series);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        mSpinner.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerSeriesSelection() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Audi.AudiSeries pickedSeries = mAudi.getAudiSeries(position);
                mAudi.setAudiSeries(pickedSeries);
                updateImage(pickedSeries);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSpinner.setSelection(mAudi.getAudiSeriesSelectedPosition());
            }
        });
    }

    private void updateImage(Audi.AudiSeries series) {
        switch (series) {
            case A:
                mImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.audi_a6));
                break;
            case Q:
                mImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.audi_q5));
                break;
            case RS:
                mImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.welcome_audi_rs7));
                break;
        }
    }


}
