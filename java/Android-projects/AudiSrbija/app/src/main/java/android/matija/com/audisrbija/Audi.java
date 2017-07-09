package android.matija.com.audisrbija;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matija on 28.1.17..
 */

public class Audi implements Serializable{

    private AudiModel mAudiModel;
    private FuelType mFuelType;
    private List<AdditionalEquipement> mAdditionalEquipements;
    private final List<String> horsePowers;
    private List<String> colors;
    private AudiSeries mAudiSeries;
    private HorsePowers mHorsePower;
    private Color mColor;


    public Audi() {
        this(AudiSeries.A, FuelType.DIZEL);
    }

    public Audi(AudiSeries audiSeries, FuelType fuelType) {
        this.mAudiSeries = audiSeries;
        setDefaultModel();
        this.mFuelType = fuelType;
        setDefaultEquipements();
        horsePowers = new ArrayList<>();
        colors = new ArrayList<>();
        populateHorsePower();
        populateAddtEquips();
        populateColors();
    }

    private void populateColors() {
        if (!colors.isEmpty()) {
            colors.clear();
        }
        Color color[] = Color.values();
        for (Color c : color) {
            if (mAudiSeries!= AudiSeries.RS && !c.name().contains("_RS")) {
                colors.add(c.toString());
            }

            else if (mAudiSeries== AudiSeries.RS && c.name().contains("_RS")) {
                colors.add(c.toString());
            }
        }
    }

    private void populateAddtEquips() {
        if (mAudiSeries==AudiSeries.RS) {
            mAdditionalEquipements.addAll(Arrays.asList(AdditionalEquipement.values()));
        }
    }


    private void populateHorsePower() {
        if (!horsePowers.isEmpty()) {
            horsePowers.clear();
        }
        HorsePowers hs[] = HorsePowers.values();
        String sModel = mAudiModel.name();
        Pattern pattern = Pattern.compile(sModel);
        Matcher matcher;
        for (HorsePowers power : hs) {
            String sPower = power.name();
            matcher = pattern.matcher(sPower);
            int cnt = 0;
            while (matcher.find()){
                int start = matcher.start();
                if (start-2 >=0){
                    String matchedPart = sPower.substring(matcher.start()-2);
                    if (matchedPart.startsWith("RS")) {
                        continue;
                    }
                }

                cnt++;
            }
            if (cnt==1) {
                horsePowers.add(power.toString());
            }
        }
    }

    public static List<String> populateHorsePowers(AudiModel model) {
        List<String> horsePowers = new ArrayList<>();
        HorsePowers hs[] = HorsePowers.values();
        String sModel = model.name();
        Pattern pattern = Pattern.compile(sModel);
        Matcher matcher;
        for (HorsePowers power : hs) {
            String sPower = power.name();
            matcher = pattern.matcher(sPower);
            int cnt = 0;
            while (matcher.find()){
                int start = matcher.start();
                if (start-2 >=0){
                    String matchedPart = sPower.substring(matcher.start()-2);
                    if (matchedPart.startsWith("RS")) {
                        continue;
                    }
                }

                cnt++;
            }
            if (cnt==1) {
                horsePowers.add(power.toString());
            }
        }
        return horsePowers;
    }


    public void setDefaultEquipements() {
        mAdditionalEquipements = new ArrayList<>();
        if (mAudiSeries==AudiSeries.RS) {
            mAdditionalEquipements.addAll(Arrays.asList(AdditionalEquipement.values()));
        }
    }

    public boolean checkForNullFields() {
        if (mAudiModel==null || mFuelType==null || mColor == null
                || mHorsePower==null) {
            return true;
        }
        return false;
    }


    public enum AudiSeries {
        A ("Series A"),
        Q ("Series Q"),
        RS ("Series RS - sport racing");

        private final String name;

        AudiSeries(String s) {
            this.name = s;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static AudiSeries getEnum(String value) {
            for (AudiSeries series : values()) {
                if (series.toString().equalsIgnoreCase(value)) {
                    return series;
                }
            }
            throw new IllegalArgumentException(value + " is not a constant in Audi.AudiModel");
        }
    }

    public enum HorsePowers {
        A1_60 ("60ks"),
        A1A2_70 ("70ks"),
        A1A2A3_90 ("90ks"),
        A1A2A3A4_120 ("120ks"),
        A2A3A4A5_136 ("136ks"),
        Q3A3A4A5A6_143 ("143ks"),
        Q5Q3A4A5A6A7RSA3_170 ("170ks"),
        Q3Q5A5A6A7A8RSA3RSA4_190 ("190ks"),
        Q3Q5Q7A6A7A8A9RSA3RSA4RSA5_210 ("210ks"),
        Q7A7Q5A8A9RSA3RSA4RSA5RSA7_240 ("240ks"),
        Q7A8A9RSA4RSA5RSA7_270 ("270ks"),
        Q7A9RSA5RSA7_320 ("320ks"),
        RSA7_460 ("460ks");

        private final String name;

        HorsePowers(String s) {
            this.name = s;
        }

        @Override
        public String toString() {
            return this.name;
        }


        public static HorsePowers getEnum(String value) {
            for (HorsePowers power : values()) {
                if (power.toString().equals(value)) {
                    return power;
                }
            }
            throw new IllegalArgumentException(value + "is not constant in Audi.HorsePowers");
        }
    }

    public enum AudiModel{
        A1 ("A1"),
        A2 ("A2"),
        A3 ("A3"),
        A4 ("A4"),
        A5 ("A5"),
        A6 ("A6"),
        A7 ("A7"),
        A8 ("A8"),
        A9 ("A9"),
        Q3 ("Q3"),
        Q5 ("Q5"),
        Q7 ("Q7"),
        RSA3 ("RS_A3"),
        RSA4 ("RS_A4"),
        RSA5 ("RS_A5"),
        RSA7 ("RS_A7");



        private final String name;

        AudiModel(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static AudiModel getEnum(String value) {
            for (AudiModel model : values()) {
                if (model.toString().equalsIgnoreCase(value)) {
                    return model;
                }
            }
            throw new IllegalArgumentException(value + " is not a constant in Audi.AudiModel");
        }

    }

    public enum Color{
        BLACK ("Crna"),
        WHITE ("Bela"),
        RED ("Crvena"),
        GRAY ("Siva"),
        ORANGE_RS ("Narandzasta"),
        MAT_BLACK_RS ("Mat crna"),
        YELLOW_RS ("Zuta"),
        RED_RS ("Crvena");


        private final String name;

        Color(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static Color getEnum(String value) {
            for (Color color : values()) {
                if (color.toString().equalsIgnoreCase(value)) {
                    return color;
                }
            }
            throw new IllegalArgumentException(value + " is not a constant in Audi.AudiModel");
        }

    }



    public enum FuelType {
        DIZEL ("Dizel"),
        BENZIN ("Benzin");

        private final String name;

        FuelType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static FuelType getEnum(String value) {
            for (FuelType fuelType : values()) {
                if (fuelType.name().equalsIgnoreCase(value)) {
                    return fuelType;
                }
            }
            throw new IllegalArgumentException(value + " is not a constant in Audi.FuelType");
        }
    }

    public enum AdditionalEquipement {
        XENON_SVETLA ("Xenon svetla"),
        PARKING_SENZORI ("Parking senzori"),
        PANORAMA_KROV ("Panorama krov"),
        KOZNA_SEDISTA ("Kožna sedišta"),
        AUTO_MENJAC ("Automatski menjač"),
        GREJANJE_SEDISTA ("Grejači sedišta"),
        TV_IZ_KROVA ("TV u krovu");

        private final String name;

        AdditionalEquipement(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static AdditionalEquipement getEnum(String value) {
            for (AdditionalEquipement equipement : values()) {
                if (equipement.toString().equalsIgnoreCase(value)) {
                    return equipement;
                }
            }
            throw new IllegalArgumentException(value + " is not a constant in Audi.AdditionalEquipement");
        }

        public static List<String> stringValues() {
            List<String> values = new ArrayList<>();
            for (AdditionalEquipement equip : values()) {
                values.add(equip.toString());
            }
            return values;
        }
    }

    public AudiModel getAudiModel() {
        return mAudiModel;
    }

    public void setAudiModel(AudiModel audiModel) {
        mAudiModel = audiModel;
        populateHorsePower();
    }

    public void setAudiModel(String s) {
        mAudiModel = AudiModel.getEnum(s);
        populateHorsePower();
    }

    public FuelType getFuelType() {
        return mFuelType;
    }

    public void setFuelType(FuelType fuelType) {
        mFuelType = fuelType;
    }

    public void setFuelType(String s) {
        mFuelType = FuelType.getEnum(s);
    }

    public AudiSeries getAudiSeries() {
        return mAudiSeries;
    }

    public void setAudiSeries(String s) {
        mAudiSeries = AudiSeries.getEnum(s);
    }

    public void setAudiSeries(AudiSeries series) {
        mAudiSeries = series;
        populateColors();
        setDefaultModel();

    }

    private void setDefaultModel() {
        switch (mAudiSeries) {
            case A:
                mAudiModel=AudiModel.A4;
                break;
            case Q:
                mAudiModel= AudiModel.Q3;
                break;
            case RS:
                mAudiModel=AudiModel.RSA3;
        }
    }

    public void setHorsePower(String horsePower) {
        mHorsePower = HorsePowers.getEnum(horsePower);
    }

    public String getHorsePower() {
        return mHorsePower.toString();
    }

    public HorsePowers getHS(){
        return mHorsePower;
    }

    public void setAudiSeries(int position) {
        AudiSeries series[] = AudiSeries.values();
        mAudiSeries = series[position];
        if (mAudiSeries==AudiSeries.A){
            mAudiModel=AudiModel.A1;
        }
        else if (mAudiSeries==AudiSeries.Q){
            mAudiModel=AudiModel.Q3;
        }
        else if (mAudiSeries==AudiSeries.RS) {
            mAudiModel=AudiModel.RSA3;
        }
    }

    public AudiSeries getAudiSeries(int position) {
        AudiSeries series[] = AudiSeries.values();
        return series[position];
    }

    public Audi.AdditionalEquipement getAdditionalEquipement(int position) {
        AdditionalEquipement equips[] = AdditionalEquipement.values();
        return equips[position];
    }

    public List<AdditionalEquipement> getAdditionalEquipements() {
        return mAdditionalEquipements;
    }

    public void addAdditionalEquipements(String ... equipements) {
        for (String equipement : equipements) {
            mAdditionalEquipements.add(AdditionalEquipement.getEnum(equipement));
        }
    }

    public void addAllAdditionalEquipements(Collection<AdditionalEquipement> c) {
        mAdditionalEquipements.addAll(c);
    }

    public void removeAdditionalEquipements(String ... equipements) {
        for (String equipement : equipements) {
            mAdditionalEquipements.remove(AdditionalEquipement.getEnum(equipement));
        }
    }

    public List<String> getHorsePowers(){
        return horsePowers;
    }

    public int getAudiSeriesSelectedPosition() {
        if (mAudiSeries == null) {
            throw new IllegalStateException("mAudiSeries is null");
        }
        AudiSeries series[] = AudiSeries.values();
        for (int i=0;i<series.length;i++){
            if (mAudiSeries==series[i]){
                return i;
            }
        }
        return -1;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColor(String value) {
        mColor = Color.getEnum(value);
    }

    public Color getColor() {
        return mColor;
    }

}
