package com.univ_thies.www.carnetdadresseufrset.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by layely on 12/2/16.
 */

public class Utilitaire {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    public static boolean sendMail(Context context, String dest) {
        //TODO
        return true;
    }

    public static boolean call(Activity activity, String numDest) {
        String number = "tel:" + numDest.trim();
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,

            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            activity.startActivity(callIntent);
            return true;
        }
        activity.startActivity(callIntent);
        return false;
    }

    public static boolean sendMessage(Activity activity, String numDest) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("address", numDest);
        sendIntent.putExtra("sms_body", "");
        sendIntent.setType("vnd.android-dir/mms-sms");
        sendIntent.setData(Uri.parse("smsto:" + numDest));

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {

            activity.startActivity(sendIntent);
        }
        return true;
    }

}