package edu.hm.huberneumeier.shareit.geschaeftslogik;

import javax.ws.rs.core.Response;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 */
public enum MediaServiceResult {
    ACCEPTED(200, Response.Status.ACCEPTED),
    BAD_REQUEST(400, Response.Status.BAD_REQUEST);

    private final int code;
    private final Response.Status status;

    MediaServiceResult(int code, Response.Status status) {
        this.code = code;
        this.status = status;
    }

    public int getCode(){
        return code;
    }

    public Response.Status getStatus() {
        return status;
    }
}
