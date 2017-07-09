package android.matija.com.audisrbija;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentAudiModel extends Fragment{

    private TextView mTextViewModel;
    private TextView mTextViewHorsePower;
    private TextView mTextViewFuel;
    private TextView mTextViewColor;
    private TextView mTextViewEquipement;

    private EditText mEditTextName;
    private EditText mEditTextSurname;
    private EditText mEditTextEmail;

    private Spinner mSpinnerPaymentMethod;

    private TextView mTextViewMonthlyPayment;
    private Spinner mSpinnerMonthlyPayment;

    private TextView mTextViewPrice;
    private Audi mAudi;
    private Price mPrice;
    private Customer mCustomer;
    private Button mButtonDisplayCar;

    private Button mButtonPriceDetails;

    private Button mButtonFinalSend;

    private void getAudi() {
        Bundle bundle = getArguments();
        mAudi = (Audi) bundle.getSerializable(OrderActivityP1.AUDI_MODEL);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAudi();
        mPrice = new Price();
        mCustomer = new Customer();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup conatiner, Bundle savedInstances) {
        View v = inflater.inflate(R.layout.activity_fragment_audi_model, conatiner, false);

        initTextViewsSpecs(v);
        initEditTexts(v);
        initSpinner(v);
        initDisplayCar(v);
        initTextViewPrice(v);
        initMonthlyPayment(v);
        initPriceDetals(v);

        addListenerToViewCar(v);
        addListenerOnSpinnerPaymentMethodSelection();
        addListenerOnSpinnerMonthlyPaymentSelection();

        mButtonFinalSend = (Button) v.findViewById(R.id.send_final_button);
        mButtonFinalSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomer.areFieldsValid()) {
                    Intent i = WelcomeActivity.newIntent(getActivity());
                    String text = "Poštovani gospodine " + mCustomer.getLastName() + " uskoro će vam stići automatizovana poruka na " + mCustomer.geteMail();
                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                    startActivity(i);
                }
                else {
                    Toast.makeText(getActivity(), "Polja nisu validno popunjena", Toast.LENGTH_SHORT).show();
                }
            }

            private void finalPopup(View parentView) {
                LayoutInflater inflater = FragmentAudiModel.this.getLayoutInflater(Bundle.EMPTY);
                final View mView= inflater.inflate(R.layout.final_popup, null);                                //was ActionBar.LayoutParams
                final PopupWindow mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
                mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                TextView forwarding = (TextView) mView.findViewById(R.id.final_popup_forwarding);
                TextView timer = (TextView) mView.findViewById(R.id.final_popup_timer);
                timer.setVisibility(View.INVISIBLE);

                mPopupWindow.showAtLocation(mView, Gravity.CENTER, 40, 0);
                parentView.setVisibility(View.INVISIBLE);
                for (int seconds=3; seconds>0; seconds--) {
                    timer.setVisibility(View.VISIBLE);
                    timer.setText(R.string.in + " " + seconds + R.string.dots);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                mPopupWindow.dismiss();
                parentView.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    private void initTextViewsSpecs(View v) {
        mTextViewModel = (TextView) v.findViewById(R.id.you_picked_model_text_view);
        mTextViewModel.append(mAudi.getAudiModel().toString());

        mTextViewHorsePower = (TextView) v.findViewById(R.id.you_picked_hs_text_view);
        mTextViewHorsePower.append(mAudi.getHorsePower());

        mTextViewFuel = (TextView) v.findViewById(R.id.you_picked_fuel_text_view);
        mTextViewFuel.append(mAudi.getFuelType().toString());

        mTextViewColor = (TextView) v.findViewById(R.id.you_picked_color_text_view);
        mTextViewColor.append(mAudi.getColor().toString());

        mTextViewEquipement = (TextView) v.findViewById(R.id.you_picked_additional_equipement_text_view);
        mTextViewEquipement.append(parseAdditionalEquips(mAudi.getAdditionalEquipements()));
    }

    private String parseAdditionalEquips(List<Audi.AdditionalEquipement> additionalEquipements) {
        StringBuilder sb = new StringBuilder();
        int counter=0;
        for (Audi.AdditionalEquipement equip : additionalEquipements) {
            if (counter==additionalEquipements.size()-1) {
                sb.append(equip.toString());
            }
            else {
                sb.append(equip.toString() + ", ");
            }
            counter++;
        }
        return sb.toString();
    }

    private void initEditTexts(View v) {
        mEditTextName = (EditText) v.findViewById(R.id.your_name);
        mEditTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomer.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTextSurname = (EditText) v.findViewById(R.id.your_surname);
        mEditTextSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomer.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTextEmail = (EditText) v.findViewById(R.id.your_email);
        mEditTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCustomer.seteMail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initSpinner(View v) {
        mSpinnerPaymentMethod = (Spinner) v.findViewById(R.id.spinner_payment);
        List<String> payments = new ArrayList<>(Arrays.asList(Price.PaymentMethod.CASH.toString(), Price.PaymentMethod.MONTHLY.toString()));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, payments);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        mSpinnerPaymentMethod.setAdapter(dataAdapter);
    }

    private void initDisplayCar(View v) {
        mButtonDisplayCar = (Button) v.findViewById(R.id.view_car_button);
    }

    private void addListenerToViewCar(View v) {
        final View parentView = v;
        mButtonDisplayCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(parentView);
            }
        });
    }

    private void showPopup(final View parentView) {
        LayoutInflater inflater = this.getLayoutInflater(Bundle.EMPTY);
        final View mView= inflater.inflate(R.layout.popup_viewcar, null);                                //was ActionBar.LayoutParams
        final PopupWindow mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        final ImageView IV = (ImageView) mView.findViewById(R.id.view_car_image);
        // TableLayout L1 = (TableLayout)findViewById(R.id.tblntarialview);
                                                      //45
        mPopupWindow.showAtLocation(IV, Gravity.CENTER, 40, 0);
        popupActive(parentView);

        final int[] modelImages = AudiImagePicker.getImgIdsByModel(mAudi.getAudiModel(),getActivity());
        final MyMutableInt currentImageIndex = new MyMutableInt(0);
        IV.setImageResource(modelImages[currentImageIndex.getInteger()]);
        Button prev = (Button) mView.findViewById(R.id.previous_button);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImageIndex.getInteger()==0) {
                    currentImageIndex.setInteger(modelImages.length-1);
                }
                else {
                    currentImageIndex.decrement();
                }
                IV.setImageResource(modelImages[currentImageIndex.getInteger()]);
            }
        });
        Button next = (Button) mView.findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageIndex.setInteger((currentImageIndex.getInteger()+1)%modelImages.length);
                IV.setImageResource(modelImages[currentImageIndex.getInteger()]);
            }
        });
        Button back = (Button) mView.findViewById(R.id.buton_close);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                parentView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void popupActive(View parentView) {
        parentView.setVisibility(View.INVISIBLE);
    }

    private void initTextViewPrice(View v) {
        mTextViewPrice = (TextView) v.findViewById(R.id.price_text_view);
        mTextViewPrice.setVisibility(View.INVISIBLE);
    }

    private void initMonthlyPayment(View v) {
        mTextViewMonthlyPayment = (TextView) v.findViewById(R.id.monthly_payment_text_view);

        mSpinnerMonthlyPayment = (Spinner) v.findViewById(R.id.spinner_payment_monthly);
        List<String> payments = new ArrayList<>(Arrays.asList(Price.MonthlyPayment.valuesAsString()));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, payments);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        mSpinnerMonthlyPayment.setAdapter(dataAdapter);


        setMonthlyPaymentVisibility(View.INVISIBLE);
        mTextViewPrice.setVisibility(View.INVISIBLE);
    }

    private void setMonthlyPaymentVisibility(int id) {
        mTextViewMonthlyPayment.setVisibility(id);
        mSpinnerMonthlyPayment.setVisibility(id);
    }


    public void addListenerOnSpinnerPaymentMethodSelection() {
        mSpinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Price.PaymentMethod payment = Price.PaymentMethod.get(position);
                if (payment == Price.PaymentMethod.MONTHLY) {
                    setMonthlyPaymentVisibility(View.VISIBLE);
                    mSpinnerMonthlyPayment.setSelection(0);
                }
                else {
                    setMonthlyPaymentVisibility(View.INVISIBLE);
                }
                mPrice.setPaymentMethod(payment);
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSpinnerPaymentMethod.setSelection(0);
                updatePrice();
            }
        });
    }

    private void addListenerOnSpinnerMonthlyPaymentSelection() {
        mSpinnerMonthlyPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Price.MonthlyPayment payment = Price.MonthlyPayment.get(position);
                mPrice.setMonthlyPayment(payment);
                mTextViewPrice.setVisibility(View.VISIBLE);
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTextViewPrice.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void updatePrice() {
        Price.PriceBuilder priceBuilder = mPrice.new PriceBuilder();
        int price = priceBuilder
                .setModel(mAudi.getAudiModel())
                .setFuel(mAudi.getFuelType())
                .setHorsePower(mAudi.getHS())
                .setEquipements(mAudi.getAdditionalEquipements())
                .setPaymentMethod(mPrice.getPaymentMethod())
                .setMonthlyPayment(mPrice.getMonthlyPayment())
                .build();
        mTextViewPrice.setText("Cena je " + price);
    }

    private void initPriceDetals(View v) {
        mButtonPriceDetails = (Button) v.findViewById(R.id.more_details_price_button);
        mButtonPriceDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupPriceDetails(v);
            }

            private void showPopupPriceDetails(View v) {
                LayoutInflater inflater = FragmentAudiModel.this.getLayoutInflater(Bundle.EMPTY);
                final View mView= inflater.inflate(R.layout.price_details_popup, null);                                //was ActionBar.LayoutParams
                final PopupWindow mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
                mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                mPopupWindow.showAtLocation(mView, Gravity.CENTER, 40, 0);
                v.setVisibility(View.INVISIBLE);
                PriceDetailPopupController controller = new PriceDetailPopupController(v, mView, mPopupWindow, mAudi, mPrice);
                controller.updateView();
                controller.addListenerToBackButton();
            }
        });
    }



}
