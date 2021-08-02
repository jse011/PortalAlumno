package com.consultoraestrategia.ss_portalalumno.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

public class IntentHelper {

    public static void shareImage(Context context, String text, Uri imageUri) {
        try {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/*");
            context.startActivity(Intent.createChooser(shareIntent, text));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Send email.
     *
     * @param context the context
     * @param email the email
     * @param subject the subject
     * @param text the text
     */
    public static void sendEmail(Context context, String email, String subject,
                          String text, Uri imageUri) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        if(!TextUtils.isEmpty(email))
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);;
        emailIntent.setType("text/plain");
        if(imageUri!=null)emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }


    /**
     * Share email.
     *
     * @param context the context
     * @param email the email
     * @param subject the subject
     * @param text the text
     */
    public static void shareEmail(Context context, String email, String subject,
                                 String text) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        if(!TextUtils.isEmpty(email))
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);;
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, "Share with"));
    }

    /**
     * Dial.
     *
     * @param context the context
     * @param number the number
     */
    public static void dial(Context context, String number) {
        Uri dialUri = Uri.parse("tel:" + number);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, dialUri);
        context.startActivity(dialIntent);
    }

    /**
     * Call.
     *
     * @param activity the context
     * @param number the number
     */
    public static void call(Activity activity, String number) {
        try {
            Uri callUri = Uri.parse("tel:" + number);
            Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
            activity.startActivity(callIntent);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(activity, "call denied",Toast.LENGTH_SHORT).show();
        }
    }

    public static void call(Context context, String number) {
        try {
            Uri callUri = Uri.parse("tel:" + number);
            Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callIntent);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "call denied",Toast.LENGTH_SHORT).show();
        }

    }


}
