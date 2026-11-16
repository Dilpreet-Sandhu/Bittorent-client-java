package com.metadata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class FileInfo {

    long length; //length of file in bytes
    List<byte[]> path;

    FileInfo(
        long length,
        List<byte[]> path
    ) {
        this.length = length;
        this.path = path;
    }


    public long getLength() {
        return length;
    }
    public List<byte[]> getPath() {
        return path;
    }

    public static List<FileInfo> getFile(
        Object files
    ) throws Exception {

        if (files == null) return null;

        if (files instanceof List<?> f) {

            List<FileInfo> l = new ArrayList<>();

            for (Object i : f) {

                if (i instanceof Map<?,?> it) {

                    long dLength = (long) it.get("length");
                    List<byte[]> dPath = (List<byte[]>) it.get("path");

                    l.add(new FileInfo(dLength, dPath));

                }else {
                    throw new Exception("FileInfo:- wrong .torrent format");
                }

            }

            return l;


        }else {
            throw new Exception("FileInfo:- wrong .torrent format");
        }


    }

}