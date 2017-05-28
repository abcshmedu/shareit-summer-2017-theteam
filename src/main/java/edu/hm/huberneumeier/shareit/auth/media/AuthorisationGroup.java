package edu.hm.huberneumeier.shareit.auth.media;

import java.util.ArrayList;
import java.util.List;

/**
 * Combine multiple Authorisations to AuthorisationGroups.
 *
 * @author Andreas Neumeier
 * @author Tobias Huber
 * @version 28.05.2017
 */
public enum AuthorisationGroup {

    ADMINS("admins", getAdminAuthorisations()),
    USERS("users", getUserAuthorisations());

    private final String name;
    private final List<Authorisation> authorisations;

    AuthorisationGroup(String name, List<Authorisation> authorisations) {
        this.name = name;
        this.authorisations = authorisations;
    }

    public String getName() {
        return name;
    }

    public List<Authorisation> getAuthorisations() {
        return authorisations;
    }

    private static List<Authorisation> getAdminAuthorisations(){
        final List<Authorisation> adminAuths = new ArrayList<>();
        adminAuths.add(Authorisation.BOOK_CREATE);
        adminAuths.add(Authorisation.BOOK_READ);
        adminAuths.add(Authorisation.BOOK_UPDATE);
        adminAuths.add(Authorisation.BOOK_DELETE);
        adminAuths.add(Authorisation.DISC_CREATE);
        adminAuths.add(Authorisation.DISC_READ);
        adminAuths.add(Authorisation.DISC_UPDATE);
        adminAuths.add(Authorisation.DISC_DELETE);
        return adminAuths;
    }
    private static List<Authorisation> getUserAuthorisations(){
        final List<Authorisation> userAuths = new ArrayList<>();
        userAuths.add(Authorisation.BOOK_READ);
        userAuths.add(Authorisation.DISC_READ);
        return userAuths;
    }
}
