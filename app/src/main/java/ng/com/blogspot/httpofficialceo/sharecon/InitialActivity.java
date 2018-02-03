package ng.com.blogspot.httpofficialceo.sharecon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import es.dmoral.toasty.Toasty;

public class InitialActivity extends Activity {

    Intent myIntent;
    private Button shareResource, recieveResource, sendApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        final Activity activity = this;

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.first_collapsing_toolbar);

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsed_title);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expanded_title);


        shareResource = (Button) findViewById(R.id.share_resource_button);
        recieveResource = (Button) findViewById(R.id.receive_resource_button);
        sendApp = (Button) findViewById(R.id.share_app_button);

        shareResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(InitialActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        recieveResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Please hold over a QR code to scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();

            }
        });

        sendApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InitialActivity.this, "Currently unavailable", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("QUIT");
        alertDialog.setMessage("Do you want to exit this app?");
        alertDialog.setIcon(R.drawable.ic_exit_app_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toasty.error(this, "Scanning cancelled", Toast.LENGTH_LONG, true).show();
            } else {
                Intent addContact = new Intent(Intent.ACTION_INSERT);
                addContact.setType(ContactsContract.Contacts.CONTENT_TYPE);
                addContact.putExtra(ContactsContract.Intents.Insert.PHONE, result.getContents());
                startActivity(addContact);
                // Toasty.info(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
