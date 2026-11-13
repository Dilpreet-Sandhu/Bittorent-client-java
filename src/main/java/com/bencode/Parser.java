package com.bencode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    private static int parseInteger(String input,Integer i) throws IllegalArgumentException {

        int num = 0;

        while (i < input.length() && input.charAt(i) != 'e') {

            int c = input.charAt(i) - '0';

            num = (num * 10) + c;

            i++;

        }

        if (i == input.length()) throw new IllegalArgumentException("reached end of string while parsing an integer");

        return num;

    }

    private static String parseString(String input,Integer i) throws IllegalArgumentException{

        int len = 0;
        while (i < input.length() && input.charAt(i) != ':') {

            int c = input.charAt(i) - '0';

            len = (len * 10) + c;

            i++;
            
        }

        if (i >= input.length()) throw new IllegalArgumentException("reached end of string while parsing string");

        i++;
        if (i >= input.length()) throw new IllegalArgumentException("reached end of string while parsing string");


        StringBuilder sb = new StringBuilder();

       for (int j = 0;j < len;j++) {

         if (i >= input.length()) throw new IllegalArgumentException("reached end of string while parsing string");


            sb.append(input.charAt(i));

            i++;

        
       } 


       return sb.toString();
        

    }

    public static Object parse(String input,Integer i) throws IllegalArgumentException {

        char c = input.charAt(i);


        if (c == 'i') {
            i++;
            return parseInteger(input,i);
        }

        if (c >= '0' && c <= '9') {

            return parseString(input,i);
            
        }

        if (c == 'l') {

            List<Object> l = new ArrayList<>();

            while (i < input.length() && input.charAt(i) != 'e') {

                l.add(parse(input,i));

            }

            if (i >= input.length()) throw new IllegalArgumentException("reached end of string while parsing a list");
            

            return l;


        }

        if (c == 'd') {

            Map<Object,Object> map = new HashMap<>();

            while (i < input.length() && input.charAt(i) != 'e') {

                Object key = parse(input,i);
                Object val = parse(input,i);

                map.put(key,val);

            }

            if (i >= input.length()) throw new IllegalArgumentException("reached end of string while parsing a dictionary");

            return map;

        }

        throw new IllegalArgumentException("invalid bencode");

    } 

}