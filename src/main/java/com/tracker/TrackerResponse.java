package com.tracker;

import java.util.List;
import java.util.Map;

import com.Helper.Helper;

public class TrackerResponse {

    private final long interval;
    private final List<PeerData> peers;
    private final long incomplete;
    private final long complete;
    private final Long minInterval;
    private final byte[] trackerId;


    TrackerResponse(
        long interval,
        List<PeerData> peers,
        long incomplete,
        long complete,
        Long minInterval,
        byte[] trackerId
    ) {
        this.interval = interval;
        this.peers = peers;
        this.incomplete = incomplete;
        this.complete = complete;
        this.minInterval = minInterval;
        this.trackerId = trackerId;
    }

    public static TrackerResponse decode(
        Object trackerResponse
    ) throws Exception{

        if (trackerResponse instanceof Map<?,?> t) {

            if (!t.containsKey("interval")) throw new Exception("interval is required");

            long dInterval = (long) t.get("interval");
            long incomplete = (long) t.get("incomplete");
            long complete = (long) t.get("complete");
            Long minInterval = t.containsKey("min interval") ? (Long) t.get("min interval") : null;
            byte[] trackerId = (byte[]) t.get("tracker id");
            Object dPeers = t.get("peers");

            Helper.nullCheck(dInterval, "interval in tracker response");
            Helper.nullCheck(dPeers,"peers in tracker response");

            List<PeerData> lPeers = PeerData.getPeers(dPeers);

            return new TrackerResponse(dInterval, lPeers,incomplete,complete,minInterval,trackerId);


        }else {
            throw new Exception("tracker response should be a dictionary");
        }

    }
    public long getInterval() {
        return interval;
    }
    public List<PeerData> getPeers() {
        return peers;
    }
    public long getIncomple() {
        return incomplete;
    }
    public long complete() {
        return complete;
    }
    public byte[] getTrackerId() {
        return trackerId;
    }
    public Long getMinInterval() {
        return minInterval;
    }
    
}
