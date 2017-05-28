package edu.hm.huberneumeier.shareit.auth.media.jsonMappings;

/**
 * Used for json requests to the authorisation service.
 *
 * @author Andreas Neumeier
 * @version 28.05.2017
 */
public class AuthorisationIDRequest {
    private Integer authorisationID;

    public AuthorisationIDRequest(){

    }

    public AuthorisationIDRequest(Integer authorisationID) {
        this.authorisationID = authorisationID;
    }

    public Integer getAuthorisationID() {
        return authorisationID;
    }
}
