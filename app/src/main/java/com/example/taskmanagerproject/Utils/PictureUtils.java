package com.example.taskmanagerproject.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class PictureUtils {
    public static Bitmap getScaleBitMap(String filePath, int dstWidth, int dstHeight){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(filePath,options);

        int srcWidth=options.outWidth;
        int srcHeight=options.outHeight;

        int scaleFactor=Math.max(1,Math.min(srcHeight/dstHeight,srcWidth/dstWidth));

        options.inJustDecodeBounds=false;
        options.inSampleSize=scaleFactor;

        Bitmap bitmap=BitmapFactory.decodeFile(filePath,options);
        return bitmap;

    }

    public static Bitmap getScaleBitMap(String filePath, Activity activity){
        Point size=new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaleBitMap(filePath,size.x,size.y);
    }

}
