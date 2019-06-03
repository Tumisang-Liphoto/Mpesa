package com.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.hover.sdk.api.Hover;
import com.hover.sdk.permissions.PermissionActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.mpesa.HoverActionListAdapter;
import com.mpesa.R;


public class MainActivity extends AppCompatActivity implements PermissionsListener,
        HoverActionListAdapter.OnActionListItemClickListener {

    private static final String TAG = "MainActivity";
    static int REQUEST_PERMISSIONS = 1;

    Button bBalance;//use button to get balance



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hover.initialize(this); //start hover


        bBalance = findViewById(R.id.Bbalance);

        //balance check button
        bBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ////function to check balance
            }
        });
    }


    public static boolean hasAllPermissions(Context c) {
        return hasPhonePermissions(c) && hasAdvancedPermissions(c);
    }

    private static boolean hasPhonePermissions(Context c) {
        return Build.VERSION.SDK_INT < 23 || (ContextCompat.checkSelfPermission(c, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(c, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
    }

    private static boolean hasAdvancedPermissions(Context c) {
        return Hover.isAccessibilityEnabled(c) && Hover.isOverlayEnabled(c);
    }

    public void requestAdvancedPermissions(){
        startActivityForResult(new Intent(this, PermissionActivity.class), REQUEST_PERMISSIONS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "permissions granted");
            }
        }
    }

    @Override
    public void onActionListItemClick(String actionId) {

    }
}
