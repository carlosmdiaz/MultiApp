package gov.cmp430.multiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarCodeActivity extends AppCompatActivity {

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bar_code);

        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(BarCodeActivity.this);
                intentIntegrator.setCaptureActivity(ScannerActivity.class);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent scanData) {
        final IntentResult intentResult = IntentIntegrator.parseActivityResult(reqCode, resCode, scanData);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "No Results Found", Toast.LENGTH_LONG).show();
            } else {
                showResult(intentResult.getContents());
            }
        } else {
            super.onActivityResult(reqCode, resCode, scanData);
        }
    }

    public void showResult(final String result) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Scanned Result: ")
                .setMessage(result)
                .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("Scanned Result:", result);
                        clipboardManager.setPrimaryClip(clipData);

                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(result));
                        BarCodeActivity.this.startActivity(intent);
                    }

                })
                .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(BarCodeActivity.this, "Barcode & QR Scanner Closed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).show();
    }



}
