package android.matija.com.audisrbija;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by matija on 4.2.17..
 */

public class AudiImagePicker {
    private static final String prefix="audi_";

    public static int[] getImgIdsByModel(Audi.AudiModel model, Activity context){
        int[] ids= new int[3];
        String name=getNameByModel(model,"ext1");
        Resources res = context.getResources();
        int id = res.getIdentifier(name, "drawable", context.getPackageName());
        ids[0] = id;
        name=getNameByModel(model,"ext2");
        ids[1] = res.getIdentifier(name,"drawable", context.getPackageName());
        name=getNameByModel(model,"int1");
        ids[2] = res.getIdentifier(name,"drawable",context.getPackageName());
        return ids;
    }

    private static String getNameByModel(Audi.AudiModel model, String suffix) {
        String sModel = model.toString().toLowerCase();
        if (sModel.contains("_")) {
            sModel=sModel.replaceAll("_","");
        }
        String a =prefix+sModel+suffix;
        Log.d("GLEDAJ OVDE**********", a);
        return a;
    }
}
