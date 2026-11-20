package com.bittorrent;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import java.util.Scanner;

import com.PeerCommunication.TcpClient;
import com.PeerCommunication.TcpServer;
import com.bencode.Parser;
import com.metadata.TorrentMetaData;


public class App {

    public static void main(String[] args) throws Exception {

        // Scanner sc = new Scanner(System.in);

        // while (true) {

        //     String s = sc.nextLine();

        //     if (s.equals("exit"))
        //         break;

        //     try {

        //         Path p = Path.of(s);

        //         byte[] bytes = Files.readAllBytes(p);

        //         Bittorrent.downloadFile(bytes);

        //     } catch (InvalidPathException e) {
        //         System.out.println("path is not valid");
        //     } catch (Exception e) {

        //         System.out.println(e.toString());

        //     }

        // }

        // sc.close(); 

        Scanner sc = new Scanner(System.in);

        byte[] infoHash = new byte[20];
        byte[] peerId = new byte[20];
        


        int port = Integer.parseInt(args[0]);
        int oPort = Integer.parseInt(args[1]);

        if (oPort != -1) TcpClient.connectAndHandShake(
            "127.0.0.1",
            oPort,
            peerId,
            infoHash
        );

        TcpServer.setUpSocketServer(infoHash,port);
        


        sc.close();


    }

    
    
       

    
}
