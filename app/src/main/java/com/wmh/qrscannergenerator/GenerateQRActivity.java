package com.wmh.qrscannergenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class GenerateQRActivity extends AppCompatActivity {

    private EditText edTxt;
    private ImageView imgQR;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qractivity);

        edTxt = findViewById(R.id.edTxt);
        imgQR = findViewById(R.id.imgQR);
        imgQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "palette", "share palette");
                Uri bitmapUri = Uri.parse(bitmapPath);

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                startActivity(Intent.createChooser(share,"Share via"));
            }
        });

        edTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                imgQR.setVisibility(View.VISIBLE);
                if(!edTxt.getText().toString().isEmpty()){
                    QRGEncoder encoder = new QRGEncoder(edTxt.getText().toString(), null, QRGContents.Type.TEXT, 1000);
                    imgQR.setImageBitmap(encoder.getBitmap());
                    bitmap = encoder.getBitmap();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}