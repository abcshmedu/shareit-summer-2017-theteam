package org.wikipedia.de;

/**
 * Der folgende Java-Code setzt die Berechnung der Prüfziffer nach der obigen Beschreibung um:
 * Die Berechnung startet mit der letzten Ziffer und beginnt die Multiplikation mit 3.
 * Nach der Summenbildung der Produkte wird die Prüfziffer zurückgegeben.
 *
 * @author Benutzer: Kuli (de.wikipedia.org)
 * @version 2017-04-12
 */
public final class BarcodeValidator {

    private static final int DEFAULT_MULTIPLIER = 3;
    private static final int SUM9 = 9;
    private static final int DEFAULT_MODULO = 10;

    /**
     * Private default constructor, it should not be possible to create a instance.
     */
    private BarcodeValidator() {
    }

    /**
     * Calculate the check digit of a barcode.
     *
     * @param digits the digits of the barcode.
     * @return the check digit
     */
    public static int calculateCheckDigit(int[] digits) {
        int sum = 0;
        int multiplier = DEFAULT_MULTIPLIER;
        for (int i = digits.length - 1; i >= 0; i--) {
            sum += digits[i] * multiplier;
            multiplier = (multiplier == DEFAULT_MULTIPLIER) ? 1 : DEFAULT_MULTIPLIER;
        }
        int sumPlus9 = sum + SUM9;
        int nextMultipleOfTen = sumPlus9 - (sumPlus9 % DEFAULT_MODULO);
        return nextMultipleOfTen - sum;
    }
}
