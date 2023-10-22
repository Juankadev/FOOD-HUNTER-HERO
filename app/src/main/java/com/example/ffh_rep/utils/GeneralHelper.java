package com.example.ffh_rep.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class GeneralHelper {

    public static Date createDateWithFormat(String dateArg){
        Date _date = null;
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            _date = dateformat.parse(dateArg);
            return _date;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return _date;
    }

    public static java.sql.Date returnSQLDate(String dateArg){
        java.sql.Date _date = null;
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            Date uDate = dateformat.parse(dateArg);
            _date = new java.sql.Date(uDate.getTime());
            return _date;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return _date;
    }

}
