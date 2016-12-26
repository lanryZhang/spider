package com.ifeng.pgc.utils;

import com.ksc.iam.util.Header;
import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommonUtil {

    public static String uniqueID() {

        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);

        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        String s = Base64.encodeBase64URLSafeString(bb.array());
        return s;
    }

    public static List<com.jayway.restassured.response.Header> transform(List<Header> headers) {
        List<com.jayway.restassured.response.Header> headers2 = new ArrayList<com.jayway.restassured.response.Header>();
        for (Header header : headers) {
            headers2.add(new com.jayway.restassured.response.Header(header.getName(), header.getValue()));
        }
        return headers2;
    }

}
