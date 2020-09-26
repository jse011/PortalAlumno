package com.consultoraestrategia.ss_portalalumno.util;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilsPortalAlumno {
    public static String tiempoCreacion(long fecha){

        Calendar calendarActual = Calendar.getInstance();
        int anhoActual = calendarActual.get(Calendar.YEAR);
        int mesActual = calendarActual.get(Calendar.MONTH);
        int diaActual = calendarActual.get(Calendar.DAY_OF_MONTH);
        int horaActual = calendarActual.get(Calendar.HOUR_OF_DAY);
        int minutoActual = calendarActual.get(Calendar.MINUTE);
        int segundosActual = calendarActual.get(Calendar.SECOND);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(fecha);
        int anhio = calendar.get(Calendar.YEAR);
        int mes= calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        int segundos= calendar.get(Calendar.SECOND);

        calendarActual.set(Calendar.DAY_OF_YEAR,-1);

        int anioAyer = calendarActual.get(Calendar.YEAR);
        int mesAyer= calendarActual.get(Calendar.MONTH);
        int diaAyer = calendarActual.get(Calendar.DAY_OF_MONTH);

        if (anhio==anhoActual&&mesActual==mes&&dia==diaActual&&horaActual==hora&&minutoActual==minuto&&segundosActual>=segundos){
            return "hace " + (segundosActual-segundos) + (segundosActual-segundos==1?" segundo":" segundos");
        } else if (anhio==anhoActual&&mesActual==mes&&dia==diaActual&&horaActual==hora&&minutoActual>minuto){
            return "hace " + (minutoActual-minuto) + (minutoActual-minuto==1?" minuto":" minutos");
        }else if (anhio==anhoActual&&mesActual==mes&&dia==diaActual){
            return "hoy a las " + changeTime12Hour(hora,minuto);
        }else if (anhio==anioAyer&&mesAyer==mes&&dia==diaAyer) {
            return "ayer a las " + changeTime12Hour(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
        }else if(anhio==anhoActual){
            return "el "+f_fecha_letras(fecha) + " " + changeTime12Hour(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
        }else {
            return  "el "+getFechaDiaMesAnho(fecha) + " " + changeTime12Hour(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
        }

    }

    public static String changeTime12Hour(int hr , int min){
        String format_min = "";
        if(min<10){
            format_min = "0"+min;
        }else {
            format_min =  String.valueOf(min);
        }
        return  hr%12 + ":" + format_min + " " + ((hr>=12) ? "PM" : "AM");
    }

    public static String changeTime12Hour(String _24HourTime){
        String hora = null;
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            hora = _12HourSDF.format(_24HourDt);
        }catch (Exception e){
            e.printStackTrace();

        }
        return  hora;
    }



    public static String f_fecha_letras(long timesTamp) {

        String mstr_fecha = "";

        String[] vobj_days = {"Dom", "Lun", "Mart", "Mié", "Jue", "Vie", "Sáb"};
        String[] vobj_Meses = {"Ene.", "Feb.", "Mar.", "Abr.", "May.", "Jun.", "Jul.", "Ago.", "Sept.", "Oct.", "Nov.", "Dic."};

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timesTamp);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        mstr_fecha = vobj_days[dayOfWeek - 1] + " " + dayOfMonth + " de " + vobj_Meses[month];

        return mstr_fecha;
    }

    public static String f_fecha_Pregunta(long timesTamp) {
        Calendar calendarActual = Calendar.getInstance();
        int anhoActual = calendarActual.get(Calendar.YEAR);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timesTamp);
        int anhio = calendar.get(Calendar.YEAR);
        //Jue 02 Jul.10:50 AM
        if(anhio==anhoActual){
            return f_fecha_letras(timesTamp) + " " + changeTime12Hour(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
        }else {
            return  getFechaDiaMesAnho(timesTamp) + " " + changeTime12Hour(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
        }
    }

    public static String getFechaDiaMesAnho(long fecha) {

        Date date = new Date(fecha);
        SimpleDateFormat formatterUI = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        return formatterUI.format(date);
    }

    public static String getFirstWord(String text) {
        if(TextUtils.isEmpty(text))return "";
        if (text.indexOf(' ') > -1) { // Check if there is more than one word.
            return text.substring(0, text.indexOf(' ')); // Extract first word.
        } else {
            return text; // Text is the first word itself.
        }
    }

    public static String capitalize(String x) {
        StringBuilder result = new StringBuilder();
        if(TextUtils.isEmpty(x))return "";
        int count = 0;
        for (String ws : x.split(" ")){
            count++;
            if(count > 1) result.append(" ");
            if(ws.length()<2){
                result.append(ws);
            }else {
                result.append(ws.substring(0, 1).toUpperCase()).append(ws.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

     /** This method converts dp unit to equivalent pixels, depending on device density.
     *
             * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, android.content.Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static String limpiarAcentos(String cadena) {
        String limpio = null;
        if (cadena != null) {
            String valor = cadena;
            valor = valor.toUpperCase();
            // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
            limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
            // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
            limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
            // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
            limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
        }
        return limpio;
    }

    public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }

    /**
     * Checks if an Activity is on the Top of the Application
     *
     * @param context
     * @param className
     * @return boolean
     */
    public static boolean isActivityOnTop(Context context, Class<?> className) {
        boolean onTop = false;
        String activityName;

        if (context != null) {
            activityName = className.getName();

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);

            for (ActivityManager.RunningTaskInfo object : taskInfo) {
                if (object.topActivity.getClassName().equals(activityName)) {
                    onTop = true;

                    break;
                }
            }
        }

        return onTop;
    }
}
