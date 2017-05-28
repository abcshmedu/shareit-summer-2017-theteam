package edu.hm.huberneumeier.shareit.auth.media.jsonMappings;

import edu.hm.huberneumeier.shareit.auth.media.Authorisation;

/**
 * Description.
 *
 * @author andreas
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
