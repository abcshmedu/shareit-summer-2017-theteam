package uk.co.moreofless;

/**
 * The method validates an ISBN-13 number.
 * It checks that the number is the correct length and validates the checksum – it accepts ISBNs with or without hyphens.
 * (http://www.moreofless.co.uk/validate-isbn-13-java/)
 *
 * @author Steve Claridge (moreofless.co.uk)
 * @version 2014-03-24
 */
public class ISBNValidator {
    public static boolean validateISBN13(String isbn) {
        if (isbn == null) {
            return false;
        }

        //remove any hyphens and whitespace
        isbn = isbn.replaceAll("-", "").replace(" ","");

        //must be a 13 digit ISBN
        if (isbn.length() != 13) {
            return false;
        }

        try {
            int tot = 0;
            for (int i = 0; i < 12; i++) {
                int digit = Integer.parseInt(isbn.substring(i, i + 1));
                tot += (i % 2 == 0) ? digit * 1 : digit * 3;
            }

            //checksum must be 0-9. If calculated as 10 then = 0
            int checksum = 10 - (tot % 10);
            if (checksum == 10) {
                checksum = 0;
            }

            return checksum == Integer.parseInt(isbn.substring(12));
        } catch (NumberFormatException nfe) {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }
}
