package com.tracker;

public class Helper {


    public static String urlEncodeBytes(
        byte[] input
    ) {

        StringBuilder res = new StringBuilder();

        for (byte b : input) {

            int u = b & 0xff;

            if (
                (u >= 'a' && u <='z') ||
                (u >= 'A' && u <= 'Z') || 
                (u >= '0' && u <= '9') || 
                u == '.' || u == '-' || u == '_' || u == '~'
            ) {
                res.append((char) u);
            }else {
                res.append(String.format("%%%02X",u));
            }

        }

        return res.toString();
    }
    
}
