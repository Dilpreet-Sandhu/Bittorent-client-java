package com.bencode;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.bittorrent.Bittorrent;
public class Parser {

    private static long parseInteger(byte[] input,int[] i) throws IllegalArgumentException {

        long num = 0;

        while (i[0] < input.length && input[i[0]] != 'e') {

            int c = input[i[0]] - '0';

            num = (num * 10) + c;

            i[0]++;

        }

        if (i[0] == input.length) throw new IllegalArgumentException("reached end of string while parsing an integer");

        i[0]++;

        return num;

    }

    private static byte[] parseString(byte[] input,int[] i) throws IllegalArgumentException{

        int len = 0;
        while (i[0] < input.length && input[i[0]] != ':') {

            int c = input[i[0]] - '0';

            len = (len * 10) + c;

            i[0]++;
            
        }

        if (i[0] >= input.length) throw new IllegalArgumentException("reached end of string while parsing string");

        i[0]++;
        if (i[0] >= input.length) throw new IllegalArgumentException("reached end of string while parsing string");


        byte[] res = new byte[len];
       for (int j = 0;j < len;j++) {

         if (i[0] >= input.length) throw new IllegalArgumentException("reached end of string while parsing string");

            res[j] = input[i[0]];

            i[0]++;

        
       } 

       return res; 

    }

    public static Object parse(byte[] input,int[] i) throws IllegalArgumentException {

        byte c = input[i[0]];


        if (c == 'i') {
            i[0]++;
            return parseInteger(input,i);
        }

        if (c >= '0' && c <= '9') {

            return parseString(input,i);
            
        }

        if (c == 'l') {

            List<Object> l = new ArrayList<>();
            i[0]++;
            while (i[0] < input.length && input[i[0]] != 'e') {

                l.add(parse(input,i));

            }

            if (i[0] >= input.length) throw new IllegalArgumentException("reached end of string while parsing a list");
            i[0]++;

            return l;


        }

        if (c == 'd') {

            Map<String,Object> map = new HashMap<>();
            i[0]++;
            while (i[0] < input.length && input[i[0]] != 'e') {

                String key = new String(parseString(input,i),StandardCharsets.UTF_8); 
                int start = i[0];                
                Object val = parse(input,i);
                
                if (key.equals("info")) {
                    int end = i[0];
                    byte[] bytes = Arrays.copyOfRange(input,start,end);
                    Bittorrent.setRawInfoBytes(bytes);
                }

                map.put(key,val);

            }

            if (i[0] >= input.length) throw new IllegalArgumentException("reached end of string while parsing a dictionary");
            i[0]++;
            return map;

        }

        throw new IllegalArgumentException("invalid bencode");

    } 

}