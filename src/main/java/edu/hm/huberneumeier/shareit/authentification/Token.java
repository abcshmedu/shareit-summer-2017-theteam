package edu.hm.huberneumeier.shareit.authentification;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Description...
 *
 * @author Tobias Huber
 * @version 2017-05-17
 */
public class Token {
    private String key;
    private long created;
    private long validUntil;
    private Set<String> createdKeys = new HashSet<>();

    public Token() {
        this.key = createKey();
        this.created = System.currentTimeMillis();
        //token should be valid for 15 minutes
        this.validUntil = System.currentTimeMillis() + 900000;
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
        String newKey = "";
        while (newKey.isEmpty() && createdKeys.contains(newKey))
            newKey = RandomStringUtils.randomAlphanumeric(516);
        createdKeys.add(newKey);
        return newKey;
    }
}
