package android.matija.com.audisrbija;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class AudiModelFragmentActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        FragmentAudiModel fragmentAudiModel = new FragmentAudiModel();
        Intent i = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderActivityP1.AUDI_MODEL, i.getSerializableExtra(OrderActivityP1.AUDI_MODEL));
        fragmentAudiModel.setArguments(bundle);
        return fragmentAudiModel;
    }

    public static Intent newIntent(Activity activity, Class clazz) {
        return new Intent(activity, clazz);
    }

}
