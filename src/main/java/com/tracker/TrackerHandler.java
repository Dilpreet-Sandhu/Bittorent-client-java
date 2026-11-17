package com.tracker;

import java.beans.Beans;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

import com.bencode.Parser;

public class TrackerHandler {

    public static byte[] requestToAnnounce(
            String url,
            byte[] infoHash,
            byte[] peerId,
            int port,
            long uploaded,
            long downloaded,
            long left,
            String event) throws Exception {

        StringBuilder query = new StringBuilder(url)
                .append("?")
                .append("info_hash=").append(Helper.urlEncodeBytes(infoHash))
                .append("&peer_id=").append(Helper.urlEncodeBytes(peerId))
                .append("&port=").append(port)
                .append("&uploaded=").append(uploaded)
                .append("&downloaded=").append(downloaded)
                .append("&left=").append(left);

        if (event != null && event.length() > 0) {
            query.append("&event=").append(event);
        }

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query.toString()))
                .header("User-Agent", "BittorrentClient/1.0")
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() != 200) {
            throw new RuntimeException("handler error =" + response.statusCode());
        }

        return response.body();

    }

    public static void handleTracker(
            String url,
            byte[] infoHash,
            byte[] peerId,
            int port,
            long uploaded,
            long downloaded,
            long left,
            String event) throws Exception {

        byte[] trackerResponse = requestToAnnounce(url, infoHash, peerId, port, uploaded, downloaded, left, event);

        Object parsedResponse = Parser.parse(trackerResponse, new int[] { 0 });
        TrackerResponse response = decodeResponse(parsedResponse);


        for (PeerData p : response.getPeers()) {

            System.out.println(new String(p.getIp(),StandardCharsets.UTF_8) + "," + p.getPort());

        }





    }

    public static TrackerResponse decodeResponse(
            Object parsedResponse) throws Exception {

        if (parsedResponse instanceof Map<?, ?> p) {
            if (p.containsKey("failure reason")) {

                String reason = new String((byte[]) p.get("failure reason"), StandardCharsets.UTF_8);
                throw new Exception("error :" + reason);

            }

            return TrackerResponse.decode(p);

        } else {
            throw new Exception("response should be a dictionary");
        }

    }

}
