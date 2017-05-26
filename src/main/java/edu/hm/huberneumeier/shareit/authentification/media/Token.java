package edu.hm.huberneumeier.shareit.authentification.media;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public class Token {
    private static final int LENGTH = 128;
    private String key;
    private long created;
    private long validUntil;
    private static Map<String, Token> createdKeys = new HashMap<>();

    public Token() {
        this.key = createKey();
        this.created = System.currentTimeMillis();
        //token should be valid for 15 minutes
        this.validUntil = System.currentTimeMillis() + 900000;

        createdKeys.put(this.key, this);
    }

    public String getKey() {
        return key;
    }

    public long getCreated() {
        return created;
    }

    public long getValidUntil() {
        return validUntil;
    }

    private String createKey() {
        String newKey = RandomStringUtils.randomAlphanumeric(LENGTH);
        while (newKey.isEmpty() && createdKeys.containsKey(newKey))
            newKey = RandomStringUtils.randomAlphanumeric(LENGTH);
        return newKey;
    }

    public static Token getTokenToKey(String key) {
        if (createdKeys.containsKey(key))
            return createdKeys.get(key);
        return null;
    }
}
