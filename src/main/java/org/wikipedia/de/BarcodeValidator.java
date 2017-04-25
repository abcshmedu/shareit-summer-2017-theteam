package org.wikipedia.de;

/**
 * Der folgende Java-Code setzt die Berechnung der Prüfziffer nach der obigen Beschreibung um:
 * Die Berechnung startet mit der letzten Ziffer und beginnt die Multiplikation mit 3.
 * Nach der Summenbildung der Produkte wird die Prüfziffer zurückgegeben.
 *
 * @author Benutzer: Kuli (de.wikipedia.org)
 * @version 2017-04-12
 */
public class BarcodeValidator {
    public static int calculateCheckDigit(int[] digits) {
        int sum = 0;
        int multiplier = 3;
        for (int i = digits.length - 1; i >= 0; i--) {
            sum += digits[i] * multiplier;
            multiplier = (multiplier == 3) ? 1 : 3;
        }
        int sumPlus9 = sum + 9;
        int nextMultipleOfTen = sumPlus9 - (sumPlus9 % 10);
        return nextMultipleOfTen - sum;
    }
}
