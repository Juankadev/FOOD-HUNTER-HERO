package com.example.ffh_rep.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public  class GeneralHelper {
    /**
     * Convierte una cadena de texto en formato "dd/MM/yyyy" a un objeto java.sql.Date.
     *
     * @param dateArg La cadena de texto que representa la fecha en formato "dd/MM/yyyy".
     * @return Un objeto java.sql.Date representando la fecha proporcionada, o null si la conversión falla.
     */
    public static java.sql.Date returnSQLDate(String dateArg){
        java.sql.Date _date = null;
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
            Date uDate = dateformat.parse(dateArg);
            _date = new java.sql.Date(uDate.getTime());
            return _date;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return _date;
    }

    public static java.sql.Date returnSQLFORMAT2(String dateArg){
        java.sql.Date _date = null;
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            Date uDate = dateformat.parse(dateArg);
            _date = new java.sql.Date(uDate.getTime());
            return _date;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return _date;
    }
    /**
     * Verifica si la cadena de texto proporcionada es una dirección de correo electrónico válida.
     *
     * @param email La dirección de correo electrónico a validar.
     * @return True si la dirección de correo electrónico es válida, False de lo contrario.
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
