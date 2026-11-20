package com.PeerCommunication;

public class PeerState { 
    private boolean amChoking = true;
    private boolean amInterested = false;
    private boolean peerChoking = true;
    private boolean peerInterested = false;

    PeerState() {}

    public void setAmChoking(boolean v) {
        amChoking = v;
    }
    public void setAmInterested(boolean v) {
        amInterested = v;
    }
    public void setPeerChoking(boolean v) {
        peerChoking = v;
    }
    public void peerInterested(boolean v) {
        peerInterested = v;
    }
    public boolean getAmChoking() {
        return amChoking;
    }
    public boolean getAmInterested() {
        return amInterested;
    }
    public boolean getPeerChoking() {
        return peerChoking;
    }
    public boolean getPeerInterested() {
        return peerInterested;
    }

}
