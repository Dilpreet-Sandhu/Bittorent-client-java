package com.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.Helper.Helper;

public class PeerData {

    private final byte[] peerId;
    private final byte[] ip;
    private final long port;

    PeerData(
        byte[] peerId,
        byte[] ip,
        long port
    ) {
        this.peerId = peerId;
        this.ip = ip;
        this.port = port;
    }

    public static List<PeerData> getPeers(
        Object peers
    ) throws Exception{

        if (peers instanceof List<?> p) {

            List<PeerData> res = new ArrayList<>();

            for (Object peer : p) {


                if (peer instanceof Map<?,?> pMap) {

                    byte[] dPeerId = (byte[]) pMap.get("peer id");
                    byte[] dIp = (byte[]) pMap.get("ip");
                    long dPort = (long) pMap.get("port");

                    Helper.nullCheck(dPeerId,"peer id in tracker resposne");
                    Helper.nullCheck(dIp,"ip address in tracker resposne");
                    Helper.nullCheck(dPort,"port in tracker response");

                    res.add(new PeerData(dPeerId, dIp, dPort));

                    

                }else {
                    throw new Exception("peer should be a dictionary");
                }


            }

            return res;


        }else {
            throw new Exception("peers is not a list");
        }


    }

    public byte[] getPeerId() {
        return peerId;
    }
    public byte[] getIp() {
        return ip;
    }
    public long getPort() {
        return port;
    }
    
}
