package com.amf.pfm.transaction.domain.validator;

import java.math.BigInteger;
import java.util.Locale;
import java.util.regex.Pattern;

public class IbanValidator {

    private static final Pattern IBAN_PATTERN = Pattern.compile("^[A-Z]{2}\\d{2}[A-Z0-9]{1,30}$");

    public static boolean isValidIban(String iban) {
        if (iban == null) {
            return false;
        }

        String cleanedIban = iban.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
        if (!IBAN_PATTERN.matcher(cleanedIban).matches()) {
            return false;
        }

        // Rearranged: move first 4 chars to end
        String rearranged = cleanedIban.substring(4) + cleanedIban.substring(0, 4);

        // Replace letters with numbers (A = 10, B = 11, ..., Z = 35)
        StringBuilder numericIban = new StringBuilder();
        for (char ch : rearranged.toCharArray()) {
            if (Character.isLetter(ch)) {
                numericIban.append(Character.getNumericValue(ch));
            } else {
                numericIban.append(ch);
            }
        }

        // Modulo 97 check
        BigInteger ibanNumber = new BigInteger(numericIban.toString());
        return ibanNumber.mod(BigInteger.valueOf(97)).intValue() == 1;
    }
}
