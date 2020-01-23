package com.bgl.phonewordsapp.service;

import java.util.List;
import java.util.Map;

/**
 * This interface provides API to find the matched word combinations
 * from dictionary for phone words encoded by phone numbers
 *  * @author rogerwill
 *
 */
public interface IPhoneWordService {
    List<List<String>> getMatchedPhoneWordsCombinations(String targetWords, List<String> dicWordsList);

    void printMatchedPhoneWords(Map<String, List<List<String>>> matchedPhoneWordsMap);
}
