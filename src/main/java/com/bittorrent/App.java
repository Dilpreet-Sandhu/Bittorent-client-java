package com.bittorrent;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import java.util.Scanner;


public class App {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        while (true) {

            String s = sc.nextLine();

            if (s.equals("exit"))
                break;

            try {

                Path p = Path.of(s);

                byte[] bytes = Files.readAllBytes(p);

                Bittorrent.downloadFile(bytes);

            } catch (InvalidPathException e) {
                System.out.println("path is not valid");
            } catch (Exception e) {

                System.out.println(e.toString());

            }

        }

        sc.close();

    }

    
}
