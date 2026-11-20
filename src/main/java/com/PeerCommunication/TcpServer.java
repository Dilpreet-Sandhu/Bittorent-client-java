package com.PeerCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpServer {

    public static void setUpSocketServer(
        byte[] infoHash,
        int port
    ) throws IOException {

        try (
            ServerSocket sc = new ServerSocket(port);
            Socket socket = sc.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());            
        ) {
           
            byte[] rPeerId = Helper.receiveHandshake(
                in,
                infoHash
            );

            Helper.sendHandShake(
                out, 
                infoHash, 
                infoHash
            );

            System.out.println("connected to " + new String(rPeerId,StandardCharsets.US_ASCII));
            TcpClient.listenForMessages(in,out);


        } catch (Exception e) {
            System.out.println("error while setting up server socket " + e.toString());
        }

    }
    
}
