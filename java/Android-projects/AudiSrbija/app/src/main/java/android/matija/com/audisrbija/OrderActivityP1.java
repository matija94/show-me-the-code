package android.matija.com.audisrbija;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderActivityP1 extends AppCompatActivity {

    private RadioGroup mRadioGroupModel1;
    private RadioGroup mRadioGroupModel2;
    private RadioGroup mRadioGroupModel3;


    private RadioGroup mRadioGroupFuel;
    private MultiChoiceSpinner mSpinnerEquipements;

    private RadioGroup mRadioGroupColor1;
    private RadioGroup mRadioGroupColor2;

    private RadioGroup mRadioGroupHorsePower1;
    private RadioGroup mRadioGroupHorsePower2;

    private TextView mEquipementTextView;

    private Button mButtonForwardToOrderP2;

    private boolean mClearLastCheck=false;
    private int mClearingStage=0;
    private Audi mAudi;

    public static final String AUDI_MODEL = "android.matija.com.audisrbija.OrderActivityP1.audi_model";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mAudi = (Audi) getIntent().getSerializableExtra(AUDI_MODEL);
        initModels();
        initFuels();
        initColors();
        initTextView();
        initSpinner();
        initHorsePowers();


        addListenersToRadioButtonModels();
        addListenersToRadioButtonFuels();
        addListenersToRadioButtonColors();
        addListenersToRadioButtonHorsePowers();
        mButtonForwardToOrderP2 = (Button) findViewById(R.id.forward_to_order_p2_button);
        mButtonForwardToOrderP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAudi.checkForNullFields()) {
                    Toast.makeText(OrderActivityP1.this, R.string.must_select_fields, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = AudiModelFragmentActivity.newIntent(OrderActivityP1.this, AudiModelFragmentActivity.class);
                i.putExtra(AUDI_MODEL, mAudi);
                startActivity(i);
            }
        });
    }

    private void initTextView() {
        mEquipementTextView = (TextView) findViewById(R.id.additionalEquipement_text_view);
        if (mAudi.getAudiSeries() == Audi.AudiSeries.RS) {
            mEquipementTextView.setText(R.string.rs_equipement);
            mAudi.addAllAdditionalEquipements(Arrays.asList(Audi.AdditionalEquipement.values()));
        }
    }

    private void addListenersToRadioButtonColors() {
        final Map<RadioGroup, Integer> lastCheckedGrpId = new HashMap<>();

        mRadioGroupColor1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // setCheck first call to listener. If the returned id is same as previous checked from this group return to caller immediately
                if (lastCheckedGrpId.get(mRadioGroupColor1) != null && checkedId == lastCheckedGrpId.get(mRadioGroupColor1)) {
                    lastCheckedGrpId.remove(mRadioGroupColor1);
                    return;
                }

                //setCheck second call to listener. Second call is always passing -1 so just return to caller, nothing to evaluate
                if (checkedId == -1){
                    return;
                }

                if (lastCheckedGrpId.containsKey(mRadioGroupColor2)) {
                    RadioButton button = (RadioButton) findViewById(lastCheckedGrpId.get(mRadioGroupColor2));
                    button.setChecked(false);
                }

                RadioButton button = (RadioButton) findViewById(checkedId);
                mAudi.setColor(button.getText().toString());
                lastCheckedGrpId.put(mRadioGroupColor1, checkedId);
            }
        });

        mRadioGroupColor2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // setCheck first call to listener. If the returned id is same as previous checked from this group return to caller immediately
                if (lastCheckedGrpId.get(mRadioGroupColor2) != null && checkedId == lastCheckedGrpId.get(mRadioGroupColor2)){
                    lastCheckedGrpId.remove(mRadioGroupColor2);
                    return;
                }
                //setCheck second call to listener. Second call is always passing -1 so just return to caller, nothing to evaluate
                if(checkedId==-1){
                    return;
                }

                if (lastCheckedGrpId.containsKey(mRadioGroupColor1)) {
                    RadioButton button = (RadioButton) findViewById(lastCheckedGrpId.get(mRadioGroupColor1));
                    button.setChecked(false);
                }
                RadioButton button = (RadioButton) findViewById(checkedId);
                mAudi.setColor(button.getText().toString());
                lastCheckedGrpId.put(mRadioGroupColor2, checkedId);
            }
        });
    }

    private void initColors() {
        mRadioGroupColor1 = (RadioGroup) findViewById(R.id.radio_group_color_1);
        mRadioGroupColor2 = (RadioGroup) findViewById(R.id.radio_group_color_2);

        List<String> colors = mAudi.getColors();
        int activeButton=1;
        RadioButton button;
        boolean finishedCycle=false;
        for (int i=0,j=0; i<colors.size(); i++) {
            if (activeButton==1) {
                button = (RadioButton) mRadioGroupColor1.getChildAt(j);
                button.setText(colors.get(i));
                finishedCycle=false;
                activeButton=2;
            }
            else if(activeButton==2) {
                button = (RadioButton) mRadioGroupColor2.getChildAt(j);
                button.setText(colors.get(i));
                activeButton=1;
                finishedCycle=true;
            }

            if(finishedCycle) {
                j++;
            }
        }

    }

    public void initSpinner() {
        mSpinnerEquipements = (MultiChoiceSpinner) findViewById(R.id.spinner_additional_equipement);
        if (mAudi.getAudiSeries() == Audi.AudiSeries.RS) {
            mSpinnerEquipements.setVisibility(View.INVISIBLE);
            return;
        }
        List<String> equipements = new ArrayList<>();
        for (Audi.AdditionalEquipement equip : Audi.AdditionalEquipement.values()) {
            equipements.add(equip.toString());
        }
        mSpinnerEquipements.setItems(equipements, new MultiChoiceSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                for (int i =0; i<selected.length; i++) {
                    if (selected[i]) {
                        mAudi.addAdditionalEquipements(mAudi.getAdditionalEquipement(i).toString());
                    }
                    else {
                        mAudi.removeAdditionalEquipements(mAudi.getAdditionalEquipement(i).toString());
                    }
                }
            }
        });
    }

    private void initHorsePowers() {
        mRadioGroupHorsePower1 = (RadioGroup) findViewById(R.id.horse_power_radio_group_1);
        mRadioGroupHorsePower2 = (RadioGroup) findViewById(R.id.horse_power_radio_group_2);
        updateHorsePowers();
    }

    private void updateHorsePowers() {
        List<String> horsePowers = mAudi.getHorsePowers();
        int activeButton=1;
        RadioButton button;
        boolean finishedCycle=false;
        for (int i=0,j=0; i<horsePowers.size(); i++) {
            if (activeButton==1) {
                button = (RadioButton) mRadioGroupHorsePower1.getChildAt(j);
                button.setText(horsePowers.get(i));
                finishedCycle=false;
                activeButton=2;
            }
            else if(activeButton==2) {
                button = (RadioButton) mRadioGroupHorsePower2.getChildAt(j);
                button.setText(horsePowers.get(i));
                activeButton=1;
                finishedCycle=true;
            }

            if(finishedCycle) {
                j++;
            }
        }
    }

    private void addListenersToRadioButtonFuels() {
        mRadioGroupFuel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton) findViewById(checkedId);
                mAudi.setFuelType(button.getText().toString());
            }
        });
    }


    private class CustomOnCheckChangedListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton button = (RadioButton) findViewById(checkedId);
            mAudi.setAudiModel(button.getText().toString());
            initModels();
        }
    }



    private void addListenersToRadioButtonModels() {
        final Map<RadioGroup, Integer> lastCheckedGrpId = new HashMap<>();
        lastCheckedGrpId.put(mRadioGroupModel1, mRadioGroupModel1.getCheckedRadioButtonId());

        mRadioGroupModel1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // setCheck first call to listener. If the returned id is same as previous checked from this group return to caller immediately
                if (lastCheckedGrpId.get(mRadioGroupModel1) != null && checkedId == lastCheckedGrpId.get(mRadioGroupModel1)) {
                    lastCheckedGrpId.remove(mRadioGroupModel1);
                    return;
                }
                //setCheck second call to listener. Second call is always passing -1 so just return to caller, nothing to evaluate
                if (checkedId ==-1) {
                    return;
                }
                if (lastCheckedGrpId.containsKey(mRadioGroupModel2)) {
                    RadioButton button = (RadioButton) findViewById(lastCheckedGrpId.get(mRadioGroupModel2));
                    button.setChecked(false);
                }

                if (lastCheckedGrpId.containsKey(mRadioGroupModel3)) {
                    RadioButton button = (RadioButton) findViewById(lastCheckedGrpId.get(mRadioGroupModel3));
                    button.setChecked(false);
                }

                RadioButton button = (RadioButton) findViewById(checkedId);
                mAudi.setAudiModel(button.getText().toString());
                updateHorsePowers();
                lastCheckedGrpId.put(mRadioGroupModel1, checkedId);
            }
        });

        mRadioGroupModel2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // setCheck first call to listener. If the returned id is same as previous checked from this group return to caller immediately
               if (lastCheckedGrpId.get(mRadioGroupModel2) != null && checkedId == lastCheckedGrpId.get(mRadioGroupModel2)) {
                   lastCheckedGrpId.remove(mRadioGroupModel2);
                   return;
               }
                //setCheck second call to listener. Second call is always passing -1 so just return to caller, nothing to evaluate
                if (checkedId == -1) {
                    return;
                }

                if (lastCheckedGrpId.containsKey(mRadioGroupModel1)) {
                    RadioButton button = (RadioButton) findViewById(lastCheckedGrpId.get(mRadioGroupModel1));
                    button.setChecked(false);
                }

                if (lastCheckedGrpId.containsKey(mRadioGroupModel3)) {
                    RadioButton button = (RadioButton) findViewById(lastCheckedGrpId.get(mRadioGroupModel3));
                    button.setChecked(false);
                }
                RadioButton button = (RadioButton) findViewById(checkedId);
                mAudi.setAudiModel(button.getText().toString());
                updateHorsePowers();
                lastCheckedGrpId.put(mRadioGroupModel2, checkedId);
            }
        });

        mRadioGroupModel3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // setCheck first call to listener. If the returned id is same as previous checked from this group return to caller immediately
                if (lastCheckedGrpId.get(mRadioGroupModel3) != null && checkedId == lastCheckedGrpId.get(mRadioGroupModel3)) {
                    lastCheckedGrpId.remove(mRadioGroupModel3);
                    return;
                }
                //setCheck second call to listener. Second call is always passing -1 so just return to caller, nothing to evaluate
                if (checkedId == -1) {
                    return;
                }

                if (lastCheckedGrpId.containsKey(mRadioGroupModel1)) {
                    RadioButton button = (RadioButton) findViewById(lastCheckedGrpId.get(mRadioGroupModel1));
                    button.setChecked(false);
                }

                if (lastCheckedGrpId.containsKey(mRadioGroupModel2)) {
                    RadioButton button = (RadioButton) findViewById(lastCheckedGrpId.get(mRadioGroupModel2));
                    button.setChecked(false);
                }

                RadioButton button = (RadioButton) findViewById(checkedId);
                mAudi.setAudiModel(button.getText().toString());
                updateHorsePowers();
                lastCheckedGrpId.put(mRadioGroupModel3, checkedId);
            }
        });
    }

    private void addListenersToRadioButtonHorsePowers() {
        final Set<RadioGroup> selected = new HashSet<>();
        mRadioGroupHorsePower1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==-1) {
                    return;
                }
                RadioButton button = (RadioButton) findViewById(checkedId);
                mAudi.setHorsePower(button.getText().toString());
                if (selected.contains(mRadioGroupHorsePower2)) {
                    selected.remove(mRadioGroupHorsePower2);
                    mRadioGroupHorsePower2.clearCheck();
                }
                selected.add(mRadioGroupHorsePower1);

            }
        });

        mRadioGroupHorsePower2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==-1) {
                    return;
                }
                RadioButton button = (RadioButton) findViewById(checkedId);
                mAudi.setHorsePower(button.getText().toString());
                if (selected.contains(mRadioGroupHorsePower1)) {
                    selected.remove(mRadioGroupHorsePower1);
                    mRadioGroupHorsePower1.clearCheck();
                }
                selected.add(mRadioGroupHorsePower2);
            }
        });
    }

    private void initModels() {
        mRadioGroupModel1 = (RadioGroup) findViewById(R.id.model_radio_group);
        mRadioGroupModel2 = (RadioGroup) findViewById(R.id.model_radio_group_1);
        mRadioGroupModel3 = (RadioGroup) findViewById(R.id.model_radio_group_2);

        Audi.AudiModel[] models = Audi.AudiModel.values();
        Audi.AudiSeries pickedSeries = mAudi.getAudiSeries();
        if (pickedSeries== Audi.AudiSeries.RS) {
            setMargins(mRadioGroupModel2, 170,0,0,0);
            setMargins(mRadioGroupModel3,190,0,0,0);
        }
        String defaultModel = mAudi.getAudiModel().toString();
        int activeRadioGroup=1;
        boolean circleFinished=false;
        for (int i =0,j=0; i<models.length;i++) {
            if (models[i].name().startsWith(pickedSeries.name())) {
                if (activeRadioGroup==1) {
                    initRadioButtonModel((RadioButton) mRadioGroupModel1.getChildAt(j), models[i], defaultModel);
                    activeRadioGroup=2;
                    circleFinished=false;
                }
                else if (activeRadioGroup==2) {
                    initRadioButtonModel((RadioButton) mRadioGroupModel2.getChildAt(j), models[i], defaultModel);
                    activeRadioGroup=3;
                }
                else if (activeRadioGroup==3) {
                    initRadioButtonModel((RadioButton) mRadioGroupModel3.getChildAt(j), models[i], defaultModel);
                    activeRadioGroup=1;
                    circleFinished=true;
                }
                if(circleFinished) {
                    j++;
                }
            }
        }
        removeIfTextNotSet(mRadioGroupModel1);
        removeIfTextNotSet(mRadioGroupModel2);
        removeIfTextNotSet(mRadioGroupModel3);
    }

    private void removeIfTextNotSet(RadioGroup radioGroupModel2) {
        RadioButton button;
        for(int i=0; i<radioGroupModel2.getChildCount();i++) {
            button = (RadioButton) radioGroupModel2.getChildAt(i);
            if (button.getText().toString().isEmpty()) {
                button.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initRadioButtonModel(RadioButton button, Audi.AudiModel model, String defaultModel) {
        String audimodel = model.toString();
        if (audimodel.equals(defaultModel)) {
            button.setChecked(true);
        }
        button.setText(audimodel);
    }

    private void initFuels() {
        Audi.FuelType[] fuels = Audi.FuelType.values();
        if (fuels.length < 2) {
            throw new IllegalStateException("Not enough fuel types declared, need at least two");
        }
        mRadioGroupFuel = (RadioGroup) findViewById(R.id.fuel_radio_group);
        RadioButton button;
        String defaultFuel = mAudi.getFuelType().toString();
        for (int i=0; i<fuels.length; i++) {
            button = (RadioButton) mRadioGroupFuel.getChildAt(i);
            String fuel = fuels[i].toString();
            if (fuel.equals(defaultFuel)) {
                button.setChecked(true);
            }
            button.setText(fuel);
        }
    }

    public static Intent newIntent(Activity welcomeActivity) {
        return new Intent(welcomeActivity, OrderActivityP1.class);
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
