package com.metadata;

import java.util.Map;

public class TorrentMetaData {

    private final byte[] announce; //url of the tracker
    private final Info info; 

    TorrentMetaData(
        byte[] announce,
        Info info
    )  {

        this.announce = announce;
        this.info = info;

    }

    public static TorrentMetaData decodeMetaData(Object data) throws Exception {

         if (data instanceof Map<?,?> d) {

            byte[] dAnnounce = (byte[]) d.get("announce");

            if (dAnnounce == null) {
                throw new Exception("announce url is required");
            }

            Object dinfo = d.get("info");

            Info nInfo = Info.decodeInfo(dinfo);

            return new TorrentMetaData(dAnnounce, nInfo);

         } else {
            throw new Exception("Torrent meta data:- wrong .torrent format");
         }

    }

   public byte[] getAnnounce() {
        return announce;
   } 

   public Info getInfo() {
    return info;
   }
    
}
