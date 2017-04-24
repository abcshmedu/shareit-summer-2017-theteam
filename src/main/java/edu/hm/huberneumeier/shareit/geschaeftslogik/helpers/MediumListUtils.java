package edu.hm.huberneumeier.shareit.geschaeftslogik.helpers;

import edu.hm.huberneumeier.shareit.fachklassen.medien.Medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Get all elements of a media collection which are of type T
 *
 * @author Andreas Neumeier
 * @version 2017-04-22
 */
public class MediumListUtils {

    public static Medium[] getMediaOfType(List<Medium> mediaSet, Class typeParameterClass) {
       final List<Medium> mediaTypeList = new ArrayList<>();

        mediaSet.stream().filter(Objects::nonNull).forEach(elem -> {
            if (elem.getClass() == typeParameterClass) {
                mediaTypeList.add(elem);
            }
        } );

        Medium[] result = new Medium[mediaTypeList.size()];
        return mediaTypeList.toArray(result);
    }
}
