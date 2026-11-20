package com.PeerCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class Helper {
   public static void sendHandShake(
    DataOutputStream out,
    byte[] infoHash,
    byte[] peerId
   ) throws IOException {

        byte[] pstr = "BitTorrent protocol".getBytes(StandardCharsets.US_ASCII);

        //sending out handshake
        out.writeByte(pstr.length);
        out.write(pstr);
        byte[] reserved = new byte[8]; //reserved bytes
        out.write(reserved);
        out.write(infoHash);
        out.write(peerId);
        out.flush();
   }

   public static byte[] receiveHandshake(
    DataInputStream in,
    byte[] infoHash
   ) throws IOException{

        byte pstrLength = in.readByte();

        if (pstrLength != 19) {
            throw new IOException("invalid protocol string length");
        }

        byte[] pstr = new byte[19];
        byte[] protcolStr = "BitTorrent protocol".getBytes(StandardCharsets.US_ASCII);
        in.readFully(pstr);

        if (!Arrays.equals(pstr, protcolStr)){
            throw new IOException("invalid protocol string");
        }    

        byte[] reserved = new byte[8];

        in.readFully(reserved);

        byte[] rInfoHash = new byte[20];

        in.readFully(rInfoHash);

        if (!Arrays.equals(rInfoHash,infoHash)) {
            throw new IOException("info hash mismatch , client is not serving asked torrent");
        }

        byte[] rPeerId = new byte[20];

        in.readFully(rPeerId);

        return rPeerId;

   }
}
