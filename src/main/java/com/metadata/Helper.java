package com.metadata;

public class Helper {
    public static void nullCheck(Object ob,String n) throws Exception {
        if (ob == null) throw new Exception(n + " is required");
    }
}
