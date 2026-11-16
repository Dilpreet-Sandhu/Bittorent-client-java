package com.bittorrent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.bencode.Parser;
import com.metadata.TorrentMetaData;

public class App {
    public static void main(String[] args) throws Exception {

        try {

            byte[] input = Files.readAllBytes(Path.of(args[0]));
   
    
            Object res = Parser.parse(input, new int[] { 0 });

     
            TorrentMetaData metaData = TorrentMetaData.decodeMetaData(res);

            System.out.println(metaData.getInfo());
    
    
        }catch(IOException e) {
            System.out.println(e.toString());
        }

        
    }
}
