package com.bittorrent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import com.bencode.Parser;
import com.metadata.TorrentMetaData;
import com.tracker.TrackerHandler;

public class App {

    
     static byte[] rawInfoBytes;
     static byte[] peerId = new byte[20];
    public static void main(String[] args) throws Exception {
        
        try {
            byte[] input = Files.readAllBytes(Path.of(args[0]));
   
    
            Object res = Parser.parse(input, new int[] {0});

     
            TorrentMetaData metaData = TorrentMetaData.decodeMetaData(res);

            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            byte[] infoHash = digest.digest(rawInfoBytes);


            SecureRandom random = new SecureRandom();
            random.nextBytes(peerId);

            String trackerUrl = new String(new String(metaData.getAnnounce(),StandardCharsets.UTF_8));

            TrackerHandler.handleTracker(
                trackerUrl,
                infoHash,
                peerId,
                6881,
                0,
                0,
                metaData.getInfo().getLength(),
                "started"
            );

            TrackerHandler.requestToAnnounce(
                trackerUrl,
                infoHash,
                peerId,
                6881,
                0,0,
                0,
                "stopped"
            );
            
        }catch(IOException e) {
            System.out.println(e.toString());
        }

        
    }
    
    public static void setRawInfoBytes(byte[] input) {
        rawInfoBytes = input;
    }
}
