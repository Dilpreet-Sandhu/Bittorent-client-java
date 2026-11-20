package com.bittorrent;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

import com.PeerCommunication.TcpClient;
import com.bencode.Parser;
import com.metadata.TorrentMetaData;
import com.tracker.TrackerHandler;

public class Bittorrent {

     static byte[] rawInfoBytes;
     static byte[] peerId = new byte[20];
    public static void downloadFile(byte[] input) throws Exception{
         
            Object res = Parser.parse(input, new int[] {0});

     
            TorrentMetaData metaData = TorrentMetaData.decodeMetaData(res);

            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            byte[] infoHash = digest.digest(rawInfoBytes);


            SecureRandom random = new SecureRandom();
            random.nextBytes(peerId);

            // String trackerUrl = new String(new String(metaData.getAnnounce(),StandardCharsets.UTF_8));
            // // String trackerUrl = "http://127.0.0.1:6969/announce";

            // TrackerHandler.handleTracker(
            //     trackerUrl,
            //     infoHash,
            //     peerId,
            //     6881,
            //     0,
            //     0,
            //     metaData.getInfo().getLength(),
            //     "started"
            // );

            
    

    }
    public static void setRawInfoBytes(byte[] input) {
        rawInfoBytes = input;
    }
    
}
