package uk.co.moreofless;

/**
 * The method validates an ISBN-13 number.
 * It checks that the number is the correct length and validates the checksum – it accepts ISBNs with or without hyphens.
 * (http://www.moreofless.co.uk/validate-isbn-13-java/)
 *
 * @author Steve Claridge (moreofless.co.uk)
 * @version 2014-03-24
 */
public final class ISBNValidator {
    private static final int DEFAULT_MULTIPLIER = 3;
    private static final int ISBN_LENGTH = 13;
    private static final int CHECKSUM10 = 10;

    /**
     * Private default constructor, it should not be possible to create a instance.
     */
    private ISBNValidator() {
    }

    /**
     * Validate a isbn13 number.
     *
     * @param isbn the isbn to validate.
     * @return the result, if a isbn is valid
     */
    public static boolean validateISBN13(String isbn) {
        if (isbn == null) {
            return false;
        }

        //remove any hyphens and whitespace
        isbn = isbn.replaceAll("-", "").replace(" ", "");

        //must be a 13 digit ISBN
        if (isbn.length() != ISBN_LENGTH) {
            return false;
        }

        try {
            int tot = 0;
            for (int i = 0; i < ISBN_LENGTH - 1; i++) {
                int digit = Integer.parseInt(isbn.substring(i, i + 1));
                tot += (i % 2 == 0) ? digit * 1 : digit * DEFAULT_MULTIPLIER;
            }

            //checksum must be 0-9. If calculated as 10 then = 0
            int checksum = CHECKSUM10 - (tot % CHECKSUM10);
            if (checksum == CHECKSUM10) {
                checksum = 0;
            }

            return checksum == Integer.parseInt(isbn.substring(ISBN_LENGTH - 1));
        } catch (NumberFormatException nfe) {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }
}
