package android.matija.com.audisrbija;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matija on 2.2.17..
 */

public class Price {

    private PaymentMethod mPaymentMethod;
    private MonthlyPayment mMonthlyPayment;


    private int mModelPrice;
    private int mFuelPrice;
    private int mHSPrice;
    private int mEquipPrice;
    private int paymentMethodPrice;


    private int mFinalPrice;
    public int getModelPrice() {
        return mModelPrice;
    }

    public int getFuelPrice() {
        return mFuelPrice;
    }

    public int getHSPrice() {
        return mHSPrice;
    }

    public int getEquipPrice() {
        return mEquipPrice;
    }

    public int getPaymentMethodPrice() {
        return paymentMethodPrice;
    }

    public int getFinalPrice() {
        return mFinalPrice;
    }

    private class HorsePowerPriceRule{
        private List<String> horsePowers;
        private Audi.HorsePowers pickedHorsePower;

        HorsePowerPriceRule(Audi.AudiModel model, Audi.HorsePowers horsePower) {
            this.pickedHorsePower = horsePower;
            horsePowers = Audi.populateHorsePowers(model);
        }

        int getAdditionalPrice() {
            int index = horsePowers.indexOf(pickedHorsePower.toString());
            mHSPrice =index*2500;
            return mHSPrice;
        }
    }

    private class EquipementsPriceRule {
        // TODO maybe implement different prices for equipements
        private static final int EACH_EQUIPEMENT_PRICE=1000;
        private int pickedEquipements;
        private Audi.AudiModel model;

        EquipementsPriceRule(List<Audi.AdditionalEquipement> equipements, Audi.AudiModel model) {
            pickedEquipements=equipements.size();
            this.model = model;
        }

        int getAdditionalPrice() {
            int price=0;
            if (model.name().startsWith("R")) {
                // RS series got all equipements built in already
                // do nothing
            }
            else {
                price = pickedEquipements*EACH_EQUIPEMENT_PRICE;
            }
            mEquipPrice=price;
            return price;
        }
    }

    private class MonthlyPaymentPriceRule {
        // since montlyPayment is evaluated in years
        private static final double MONTHS=12.0;
        private static final double ANNUAL_INTEREST_RATE = 0.05;

        private MonthlyPayment monthlyPayment;
        private int principal;
        private int installments;

        MonthlyPaymentPriceRule(MonthlyPayment payment, int currentPrice) {
            this.monthlyPayment = payment;
            this.principal = currentPrice;
            initInstallments();
        }

        private void initInstallments() {
            String payment = monthlyPayment.toString();
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(payment);
            if (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                installments = Integer.parseInt(payment.substring(start, end)) * (int)MONTHS;
            }
            else {
                // something went very wrong
                throw new NullPointerException("installments couldn't be evaluated.");
            }

        }
        // NRP
        double getFinalPrice() {
            double monthlyRate = ANNUAL_INTEREST_RATE / MONTHS;
            double monthlyPayment = (principal*monthlyRate) /
                    (1-Math.pow(1+monthlyRate, -installments));
            return monthlyPayment*installments;
        }
    }


    public class PriceBuilder {
        private Audi.AudiModel model;
        private Audi.FuelType fuel;
        private Audi.HorsePowers horsePower;
        private List<Audi.AdditionalEquipement> equipements;

        private PaymentMethod method;
        private MonthlyPayment monthlyPayment;

        public PriceBuilder setModel(Audi.AudiModel model) {
            this.model = model;
            return this;
        }

        public PriceBuilder setFuel(Audi.FuelType fuel) {
            this.fuel = fuel;
            return this;
        }

        public PriceBuilder setHorsePower(Audi.HorsePowers hs) {
            this.horsePower=hs;
            return this;
        }

        public PriceBuilder setEquipements(List<Audi.AdditionalEquipement> equipements) {
            this.equipements = equipements;
            return this;
        }

        public PriceBuilder setPaymentMethod(PaymentMethod method) {
            this.method = method;
            return this;
        }

        public PriceBuilder setMonthlyPayment(MonthlyPayment payment) {
            this.monthlyPayment=payment;
            return this;
        }


        public int build() {
            if (model==null || fuel==null || horsePower==null || equipements==null || method == null) {
                throw new IllegalStateException("All fields have to be set before building the price");
            }

            if (method == PaymentMethod.MONTHLY && monthlyPayment == null) {
                throw new IllegalStateException("For paymentMethod:monthly, monthlyPayment has to be selected");
            }
            int price = 0;
            switch (model) {
                case A1:
                    price=20000;
                    break;
                case A2:
                    price=25000;
                    break;
                case A3:
                    price=30000;
                    break;
                case A4:
                    price=35000;
                    break;
                case A5:
                    price=40000;
                    break;
                case A6:
                    price=45000;
                    break;
                case A7:
                    price=50000;
                    break;
                case A8:
                    price=55000;
                    break;
                case A9:
                    price=60000;
                    break;
                case Q3:
                    price=30000;
                    break;
                case Q5:
                    price=45000;
                    break;
                case Q7:
                    price=55000;
                    break;
                case RSA3:
                    price=50000;
                    break;
                case RSA4:
                    price=60000;
                    break;
                case RSA5:
                    price=75000;
                    break;
                case RSA7:
                    price=85000;
                    break;
            }
            mModelPrice=price;
            switch (fuel) {
                case BENZIN:
                    price+=2000;
                    break;
                case DIZEL:
                    price+=5000;
            }
            mFuelPrice = price-mModelPrice;

            //hs price is set while building one
            HorsePowerPriceRule HSpriceRule = new HorsePowerPriceRule(model, horsePower);
            price+=HSpriceRule.getAdditionalPrice();
            //equip price is set while building one
            EquipementsPriceRule equipPriceRule = new EquipementsPriceRule(equipements, model);
            price+=equipPriceRule.getAdditionalPrice();

            // if user pays in cash then he gets no boosted price in installments
            if (method == PaymentMethod.CASH) {
                paymentMethodPrice=0;
                mFinalPrice=price;
                return price;
            }

            MonthlyPaymentPriceRule monthlyPaymentPriceRule = new MonthlyPaymentPriceRule(monthlyPayment, price);
            int newPrice=(int) monthlyPaymentPriceRule.getFinalPrice();
            paymentMethodPrice=newPrice-price;
            mFinalPrice = newPrice;
            return newPrice;
        }
    }

    public enum PaymentMethod {
        CASH ("Po preuzimanju"),
        MONTHLY ("Platite na rate");

        private final String name;

        PaymentMethod(String s) {
            this.name = s;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static PaymentMethod get(int pos) {
            return values()[pos];
        }
    }

    public enum MonthlyPayment {
        THREE_YEARS ("3 godine"),
        SIX_YEARS ("6 godina"),
        NINE_YEARS ("9 godina"),
        TWELVE_YEARS ("12 godina");

        private final String name;

        MonthlyPayment(String s) {
            this.name = s;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static String[] valuesAsString () {
            String[] sValues = new String[values().length];
            int i=0;
            for (MonthlyPayment payment : values()) {
                sValues[i++] = payment.toString();
            }
            return sValues;
        }

        public static MonthlyPayment get(int pos) {
            return values()[pos];
        }
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        mPaymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return mPaymentMethod;
    }

    public MonthlyPayment getMonthlyPayment() {
        return mMonthlyPayment;
    }

    public void setMonthlyPayment(MonthlyPayment monthlyPayment) {
        mMonthlyPayment = monthlyPayment;
    }

}
