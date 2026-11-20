package com.PeerCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpClient {

    public static void connectAndHandShake(
        String ip,
        int port,
        byte[] peerId,
        byte[] infoHash
    ) {
        int connectionTimeout = 5000;
        int readTimeoutMs = 10000;

        try (
            Socket socket = new Socket();
        ) {
            
            socket.connect(new InetSocketAddress(ip,port),connectionTimeout);
            socket.setSoTimeout(readTimeoutMs);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             

            socket.setTcpNoDelay(true);


            Helper.sendHandShake(
                out,
                infoHash,
                peerId
            );
            
            byte[] rPeerId = Helper.receiveHandshake(
                in,
                infoHash
            );
            System.out.println("connected to " + new String(rPeerId,StandardCharsets.US_ASCII));
            listenForMessages(in, out);


            
        } catch (Exception e) {
            System.err.println("error while connecting to" + ip + ":" + port + e.toString());
        }

    }

    public static void listenForMessages(
        DataInputStream in,
        DataOutputStream out
    ) throws IOException {
        while (true) {
            int length = in.readInt();

            if (length == 0) {

                System.out.println("keep alive ");
                continue;
            }

            byte messageId = in.readByte();
            byte[] payload = new byte[length - 1];

            if (length - 1 > 0) {
                in.readFully(payload);
            }

            System.out.println("received payload with id" + messageId + " " + payload);

        }
    }

    
}