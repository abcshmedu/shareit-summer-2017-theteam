package edu.hm.huberneumeier.shareit.geschaeftslogik.helpers;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;
import org.wikipedia.de.BarcodeValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Collection of helpful static methods.
 *
 * @author Tobias Huber, Andreas Neumeier
 * @version 2017-04-25
 */
public class Utils {

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
     * @return
     */
    public static boolean validateBarcode(String barcode) {
        //replace '-' and ' ' to get a number only string
        barcode = barcode.replace("-", "").replace(" ", "");

        //length have to be 13
        if (barcode.length() != 13)
            return false;

        //last int is checksum
        int checksum = Integer.parseInt(barcode.substring(12));

        //create a int array out of the values
        int[] codes = new int[barcode.length() - 1];
        for (int i = 0; i < barcode.length() - 1; i++) {
            codes[i] = Integer.parseInt(barcode.substring(i, i + 1));
        }

        //calculate the checksum
        int calculatedChecksum = BarcodeValidator.calculateCheckDigit(codes);

        return checksum == calculatedChecksum;
    }
}
