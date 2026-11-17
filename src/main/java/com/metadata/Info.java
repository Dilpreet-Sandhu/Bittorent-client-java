package com.metadata;
import java.util.List;
import java.util.Map;

import com.Helper.Helper;

public class Info {

    private byte[] name;
    private long pieceLength; // number of bytes in each piece
    private byte[] pieces; //sha1 hash of the pieces 
    private Long length; //length of file in bytes
    private List<FileInfo> files;

    Info(
        byte[] name,
        long pieceLength,
        byte[] pieces,
        Long length,
        List<FileInfo> files
    ) {

        this.name = name;
        this.pieceLength = pieceLength;
        this.pieces = pieces;
        this.length = length;
        this.files = files;
    }

    public byte[] getName() {
        return name;
    }

    public long getPieceLength() {
        return pieceLength;
    }
    public byte[] getPieces() {
        return pieces;
    }
    public Long getLength() {
        return length;
    }
    public List<FileInfo> getFiles() {
        return files;
    }
    
    public static Info decodeInfo(Object dInfo) throws Exception {

        
        if (dInfo instanceof Map<?,?> info) {

            byte[] dName = (byte[]) info.get("name");
            long pieceLength = (long) info.get("piece length");
            byte[] pieces = (byte[]) info.get("pieces");

            Helper.nullCheck(dName,"name of file");
            Helper.nullCheck(pieceLength,"piece length");
            Helper.nullCheck(pieces,"pieces");

            if (pieces.length % 20 != 0) {
                throw new Exception("pieces length should be a multiple of 20");
            }

            Long length = (Long) info.get("length");
            List<FileInfo> files = FileInfo.getFile(info.get("files"));

            if ((length != null && files != null) || (length == null && files == null)) {
                throw new Exception("Info:- both length and files are present or neither are present");
            }

            return new Info(dName, pieceLength, pieces, length,files);


        }else {
            throw new Exception("Info:- wrong format of .torrent file");
        }
    }
        

 
}