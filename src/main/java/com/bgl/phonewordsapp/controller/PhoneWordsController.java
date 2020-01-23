package com.bgl.phonewordsapp.controller;

import com.bgl.phonewordsapp.service.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This controller class invokes APIs of service layer to get the matched phone words for the provided phone numbers
 * @author rogerwill
 *
 */
public class PhoneWordsController {

    private IPhoneWordService phoneWordService;
    private IPhoneNumberService phoneNumberService;
    private IDictionaryService dictionaryService;

    public PhoneWordsController(IPhoneWordService phoneWordService, IPhoneNumberService phoneNumberService, IDictionaryService dictionaryService){
        this.phoneWordService = phoneWordService;
        this.phoneNumberService = phoneNumberService;
        this.dictionaryService = dictionaryService;
    }

    /**
     * This method calls the APIs of service layer to get the matched phone words combinations for the provided phone numbers
     * @param phoneNumbersFilePath file path of phone numbers file
     * @param dictionaryFilePath file path of dictionary file
     * @return Map stores phone number as key and its matched phone words combinations list as value
     */
    public Map<String, List<List<String>>> getMatchedPhoneWordsMap(String phoneNumbersFilePath, String dictionaryFilePath){
        Map<String, List<List<String>>> matchedPhoneWordsMap = new LinkedHashMap<>();

        //get all the possible letter combinations of the provided phone numbers
        Map<String, List<String>> phoneNumToPhoneWordsMap = phoneNumberService.getPhoneNumToPhoneWordsMap(phoneNumbersFilePath);
        // get the dictionary words list
        List<String> dictWordsList = dictionaryService.getDictionaryWordsList(dictionaryFilePath);
        //loop each possible letter combinations of the provided phone numbers to get matched words combinations from the dictionary words list
        for(Map.Entry<String, List<String>> phoneNumToPhoneWordsEntry : phoneNumToPhoneWordsMap.entrySet()){
            List<List<String>>mergedMatchedPhoneWordsCombinations = new ArrayList<>();
            for(String phoneWord : phoneNumToPhoneWordsEntry.getValue()){
                List<List<String>> matchedPhoneWordsCombinations = phoneWordService.getMatchedPhoneWordsCombinations(phoneWord, dictWordsList);
                if(matchedPhoneWordsCombinations.size()>0){
                    mergedMatchedPhoneWordsCombinations.addAll(matchedPhoneWordsCombinations);
                }
            }
            matchedPhoneWordsMap.put(phoneNumToPhoneWordsEntry.getKey(), mergedMatchedPhoneWordsCombinations);
        }
        return matchedPhoneWordsMap;
    }

    /**
     * This method calls the API of server layer to print the phone numbers and their matched phone words combinations
     * @param matchedPhoneWordsMap phone numbers and their matched phone words combinations
     */
    public void printMatchedPhoneWords(Map<String, List<List<String>>> matchedPhoneWordsMap){
        phoneWordService.printMatchedPhoneWords(matchedPhoneWordsMap);
    }
}
