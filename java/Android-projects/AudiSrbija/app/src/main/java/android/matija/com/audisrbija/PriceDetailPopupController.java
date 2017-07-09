package android.matija.com.audisrbija;

import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by matija on 3.2.17..
 */

public class PriceDetailPopupController {
    private static final String ADD = "+" ;
    private View mView;
    private View mParentView;
    private PopupWindow mPopupWindow;

    private TextView mTextViewInfoValute;

    private TextView mTextViewModel;
    private TextView mTextViewModelPrice;

    private TextView mTextViewFuel;
    private TextView mTextViewFuelPrice;

    private TextView mTextViewHS;
    private TextView mTextViewHSPrice;

    private TextView mTextViewEquip;
    private TextView mTextViewEquipPrice;
    private TextView mTextViewInfo;
    private TextView mTextViewInfoRS;

    private TextView mTextViewPayment;
    private TextView mTextViewPaymentPrice;

    private TextView mFinalPrice;

    private Button mButtonBack;
    private Audi mAudi;
    private Price mPrice;

    public PriceDetailPopupController (View parentView, View v, PopupWindow popup, Audi audi, Price price) {
        this.mView = v;
        this.mParentView = parentView;
        this.mPopupWindow = popup;
        this.mAudi = audi;
        this.mPrice = price;
    }

    public void updateView() {
        mTextViewInfoValute = (TextView) mView.findViewById(R.id.info_valute_popup);

        mTextViewModel = (TextView) mView.findViewById(R.id.model_value_price_detail_popup);
        mTextViewModel.setText(mAudi.getAudiModel().toString());
        mTextViewModelPrice  = (TextView) mView.findViewById(R.id.model_price_value_popup);
        mTextViewModelPrice.setText(ADD + Integer.toString(mPrice.getModelPrice()));

        mTextViewFuel = (TextView) mView.findViewById(R.id.fuel_value_price_detail_popup);
        mTextViewFuel.setText(mAudi.getFuelType().toString());
        mTextViewFuelPrice  = (TextView) mView.findViewById(R.id.fuel_value_price_popup);
        mTextViewFuelPrice.setText(ADD + Integer.toString(mPrice.getFuelPrice()));

        mTextViewHS = (TextView) mView.findViewById(R.id.hs_value_price_detail_popup);
        mTextViewHS.setText(mAudi.getHorsePower());
        mTextViewInfo = (TextView) mView.findViewById(R.id.info_text_popup);
        mTextViewInfoRS = (TextView) mView.findViewById(R.id.info_RStext_popup);
        if (mAudi.getAudiSeries()!= Audi.AudiSeries.RS) {
            mTextViewInfoRS.setVisibility(View.INVISIBLE);
        }

        mTextViewHSPrice  = (TextView) mView.findViewById(R.id.hs_price_value_popup);
        mTextViewHSPrice.setText(ADD + Integer.toString(mPrice.getHSPrice()));

        mTextViewEquip = (TextView) mView.findViewById(R.id.equip_value_price_detail_popup);
        mTextViewEquip.setText(ADD + Integer.toString(mAudi.getAdditionalEquipements().size()));
        mTextViewEquipPrice  = (TextView) mView.findViewById(R.id.equip_price_value_popup);
        mTextViewEquipPrice.setText(ADD + Integer.toString(mPrice.getEquipPrice()));

        mTextViewPayment = (TextView) mView.findViewById(R.id.payment_value_price_detail_popup);
        if (mPrice.getPaymentMethod()== Price.PaymentMethod.CASH) {
            mTextViewPayment.setText(mPrice.getPaymentMethod().toString());
        }

        else if (mPrice.getPaymentMethod()==Price.PaymentMethod.MONTHLY) {
            mTextViewPayment.setText(mPrice.getMonthlyPayment().toString());
        }

        mTextViewPaymentPrice  = (TextView) mView.findViewById(R.id.payment_price_value_popup);
        mTextViewPaymentPrice.setText(ADD + Integer.toString(mPrice.getPaymentMethodPrice()));


        mFinalPrice = (TextView) mView.findViewById(R.id.final_total_price_text_view_popup);
        mFinalPrice.append(Integer.toString(mPrice.getFinalPrice()));


        mButtonBack = (Button) mView.findViewById(R.id.back_button_price_popup);
    }

    public void addListenerToBackButton() {
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                mParentView.setVisibility(View.VISIBLE);
            }
        });
    }




}
