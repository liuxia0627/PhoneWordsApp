package com.bgl.phonewordsapp.service;

import java.util.List;
import java.util.Map;

/**
 * This interface provides API to encode provided phone numbers to possible letter combinations
 * @author rogerwill
 */

public interface IPhoneNumberService {
    Map<String, List<String>> getPhoneNumToPhoneWordsMap(String phoneNumbersFilePath);

    List<String> convertPhoneNumberToWords(String phoneNumber);
}
