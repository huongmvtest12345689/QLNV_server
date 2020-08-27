package com.dimageshare.hrm.util;

public class AppUtils {
    private AppUtils(){}
    public static boolean compareShort(Short compare, int value) {
        if (compare == null)
            return false;
        return compare == (short) value;
    }
}
