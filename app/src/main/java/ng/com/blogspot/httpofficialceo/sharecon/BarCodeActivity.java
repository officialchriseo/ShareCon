package ng.com.blogspot.httpofficialceo.sharecon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

import ng.com.blogspot.httpofficialceo.sharecon.model.Contacts;

public class BarCodeActivity extends AppCompatActivity {

    ArrayList<String> selectedItem;
    ArrayList<Contacts> contacts = new ArrayList<>();
    ImageView barcodePic;
    Button conv_butt;
    String text2Qr;
    private TextView contactName, contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Intent myIntent = getIntent();

        String name = myIntent.getStringExtra("CONTACT_NAME");

        collapsingToolbar.setTitle(name);

        contactName = (TextView) findViewById(R.id.personName);
        contactNumber = (TextView) findViewById(R.id.personNumber);
        barcodePic = (ImageView) findViewById(R.id.barcode_image);
        conv_butt = (Button) findViewById(R.id.convert_button);

        conv_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodePic.setVisibility(View.VISIBLE);
                text2Qr = contactNumber.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(bitMatrix);
                    barcodePic.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });


        getIntents();


    }

    private void getIntents() {
        Intent myIntent = getIntent();

        String name = myIntent.getStringExtra("CONTACT_NAME");
        String number = myIntent.getStringExtra("CONTACT_NUMBER");

        contactName.setText(name);
        contactNumber.setText(number);
    }

}
