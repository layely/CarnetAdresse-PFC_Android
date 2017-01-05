package com.univ_thies.www.carnetdadresseufrset.util;

import android.widget.DatePicker;

/**
 * Created by layely on 12/2/16.
 */

public class Utilitaire {

    public static boolean modeAdmin = false;

    public static String filterString(String arg) {
        if (arg != null && !arg.equalsIgnoreCase("null")) {
            return arg;
        }
        return "";
    }

    public static void setDatePicker(String date, DatePicker datePicker) {
        String[] jjmmyyyy = date.split("/");

        int jj = Integer.parseInt(jjmmyyyy[0].trim());
        int mm = Integer.parseInt(jjmmyyyy[1].trim());
        int year = Integer.parseInt(jjmmyyyy[2].trim());

        datePicker.init(year, mm - 1, jj, null);
    }

    public static String getDateString(DatePicker datePicker) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(datePicker.getDayOfMonth() + "/");
        stringBuilder.append((datePicker.getMonth() + 1) + "/");
        stringBuilder.append(datePicker.getYear());

        return stringBuilder.toString();
    }

    public static boolean isValidName(String nom) {
        if (nom == null)
            return false;
        return !nom.isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return !email.isEmpty();
    }
}
