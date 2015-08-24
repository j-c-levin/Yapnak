package com.uq.yapnak;

import android.app.Activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.frontend.yapnak.subview.RedEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * Created by vahizan on 24/08/2015.
 */
public class QRGenerator extends AlertDialog {

    private ImageView QR;
    private Button dismiss;
    private LinearLayout layout;
    private Activity a;
    private Context c;
    private String content;



    public QRGenerator(Context context, Activity activity,String url){
        super(context);
        c = context;
        a = activity;
        content = url;

        View v = a.getLayoutInflater().inflate(R.layout.qr_layout, null);

        QR = (ImageView) v.findViewById(R.id.qrimage);
        layout = (LinearLayout) v.findViewById(R.id.qrlayout);
        dismiss = (Button) v.findViewById(R.id.dismissButton);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        QR.setImageBitmap(encode(content, 300, 300));
        setTitle("QR Code");
        setView(v);
    }

    public Bitmap encode(String content,int width,int height){
        QRCodeWriter writer = new QRCodeWriter();

        try {

            Hashtable<EncodeHintType,ErrorCorrectionLevel> hash = new Hashtable<>();
            hash.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);

            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height,hash);

            Bitmap bitmap= Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);

            for(int x = 0;x<width;x++){
                for(int y=0;y<height;y++){
                    bitmap.setPixel(x,y,matrix.get(x,y)? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;

        }catch (WriterException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getContext().getResources();
        int id= res.getIdentifier("titleDivider", "id", "android");
        final View v = findViewById(id);
        if(v!=null) {
            v.setBackgroundColor(Color.parseColor("#BF360C"));
        }
    }

    public void decode(){
        QRCodeReader reader = new QRCodeReader();
        //reader.d;
    }


}
