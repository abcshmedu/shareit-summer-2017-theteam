package edu.hm.huberneumeier.shareit.media.logic.helpers;

import edu.hm.huberneumeier.shareit.media.media.Medium;
import org.wikipedia.de.BarcodeValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Collection of helpful static methods.
 *
 * @author Tobias Huber
 * @author Andreas Neumeier
 * @version 2017-04-25
 */
public final class Utils {

    /**
     * Private default constructor, it should not be possible to create a instance.
     */
    private Utils() {
    }

    private static final int BARCODE_LENGTH = 13;

    /**
     * Get all media of a given type.
     *
     * @param mediumList         list of media where to get special type from
     * @param typeParameterClass special type to get
     * @return array of special type media
     */
    public static Medium[] getMediaOfType(List<Medium> mediumList, Class typeParameterClass) {
        final List<Medium> mediaTypeList = new ArrayList<>();

        mediumList.stream().filter(Objects::nonNull).forEach(elem -> {
            if (elem.getClass() == typeParameterClass) {
                mediaTypeList.add(elem);
            }
        });

        Medium[] result = new Medium[mediaTypeList.size()];
        return mediaTypeList.toArray(result);
    }

    /**
     * Method to validate a barcode.
     *
     * @param barcode string of barcode numbers
     * @return if barcode is valid or not
     */
    public static boolean validateBarcode(String barcode) {
        if (barcode == null) {
            return false;
        }
        //replace '-' and ' ' to get a number only string
        barcode = barcode.replace("-", "").replace(" ", "");

        //length have to be 13
        if (barcode.length() != BARCODE_LENGTH) {
            return false;
        }
        //last int is checksum
        int checksum = Integer.parseInt(barcode.substring(BARCODE_LENGTH - 1));

        //create a int array out of the values
        int[] codes = new int[barcode.length() - 1];
        try {
            for (int i = 0; i < barcode.length() - 1; i++) {
                codes[i] = Integer.parseInt(barcode.substring(i, i + 1));
            }
        } catch (NumberFormatException ex) {
            return false;
        }

        //calculate the checksum
        int calculatedChecksum = BarcodeValidator.calculateCheckDigit(codes);

        return checksum == calculatedChecksum;
    }
}
