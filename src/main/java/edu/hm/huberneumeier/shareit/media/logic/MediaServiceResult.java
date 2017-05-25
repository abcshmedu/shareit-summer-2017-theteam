package edu.hm.huberneumeier.shareit.media.logic;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.ws.rs.core.Response;

/**
 * All used media service results (status codes).
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017-04-26
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MediaServiceResult {
    /**
     * Media service results.
     */
    OK(200, Response.Status.OK, "ok"),
    CREATED(201, Response.Status.CREATED, "resource created"),
    ACCEPTED(202, Response.Status.ACCEPTED, "accepted"),
    NOT_MODIFIED(304, Response.Status.NOT_MODIFIED, "resource not modified"),
    BAD_REQUEST(400, Response.Status.BAD_REQUEST, "not a valid request"),
    NOT_FOUND(404, Response.Status.NOT_FOUND, "resource not found");

    private final int code;
    private final Response.Status status;
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the status code
     * @param status  the response status
     * @param message the message
     */
    MediaServiceResult(int code, Response.Status status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    /**
     * Getter of status code.
     *
     * @return the status code
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter of response status.
     *
     * @return the response status.
     */
    public Response.Status getStatus() {
        return status;
    }

    /**
     * Getter of message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
