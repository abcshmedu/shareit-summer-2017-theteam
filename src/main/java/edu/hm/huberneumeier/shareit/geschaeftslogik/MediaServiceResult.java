package edu.hm.huberneumeier.shareit.geschaeftslogik;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.ws.rs.core.Response;

/**
 * Description.
 *
 * @author Andreas Neumeier
 * @version 2017-04-12
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MediaServiceResult {
    OK(200, Response.Status.OK, "ok"),
    CREATED(201, Response.Status.CREATED, "resource created"),
    ACCEPTED(202, Response.Status.ACCEPTED, "accepted"),
    NOT_MODIFIED(304,Response.Status.NOT_MODIFIED, "resource not modified"),
    BAD_REQUEST(400, Response.Status.BAD_REQUEST, "unknown error"),
    NOT_FOUND(404, Response.Status.NOT_FOUND, "resource not found");

    private final int code;
    private final Response.Status status;
    private final String message;

    MediaServiceResult(int code, Response.Status status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public Response.Status getStatus() {
        return status;
    }
}
