package gov.cmp430.multiapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FlashLightActivity extends AppCompatActivity {

    Button FlashLight;

    private final int CAMERRA_REQUEST_CODE = 2;

    boolean hasCameraFlash = false;
    boolean isFlashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_flash_light);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        FlashLight = findViewById(R.id.btn);

        FlashLight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                askPermission(Manifest.permission.CAMERA, CAMERRA_REQUEST_CODE);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLight(){

        if(hasCameraFlash){
            if(isFlashOn) {
                FlashLight.setBackgroundResource(R.drawable.off);
                flashLightOff();
                isFlashOn = false;
            }
            else
            {
                FlashLight.setBackgroundResource(R.drawable.on);
                flashLightOn();
                isFlashOn = true;

            }

        }
        else
        {
            Toast.makeText(FlashLightActivity.this, "No Flash Available on your device",Toast.LENGTH_LONG).show();
        }




    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);

        try{
            String cameraid = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraid, true);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);

        try{
            String cameraid = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraid, false);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askPermission(String permission, int requestCode) {

        if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
        else {
            flashLight();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults){

        switch (requestCode){
            case CAMERRA_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                    Toast.makeText(this, "Camera Permission Granted",Toast.LENGTH_LONG).show();
                    flashLight();
                }
                else
                {
                    Toast.makeText(this, "Camera Permission Denied",Toast.LENGTH_LONG).show();

                }

                break;

        }

    }

}