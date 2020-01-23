package com.bgl.phonewordsapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This service class provides API to find the matched word combinations
 * from dictionary for phone words encoded by phone numbers
 *  * @author rogerwill
 *
 */
public class PhoneWordService implements IPhoneWordService {

    /**
     * This is a recursive method to return list of word combinations from dictionary for phone word encoded by phone number
     * @param targetWords phone word to be matched
     * @param dicWordsList words list of dictionary
     * @return list to stores all the possible words combinations from dictionary for the provided phone word
     */
    public List<List<String>> getMatchedPhoneWordsCombinations(String targetWords, List<String> dicWordsList){
        List<List<String>> results = new ArrayList<List<String>>();
        //loop each word of the dictionary
        for(String element : dicWordsList) {
            //if provided phone word is totally equal to the word of the dictionary then add that word to the result list
            if(targetWords.equals(element)) {
                List<String> list = new ArrayList<String>();
                list.add(element);
                results.add(list);
            }else {
                //if provided phone words starts with the word of dictionary
                if(targetWords.indexOf(element) == 0) {
                    //remove the matched word to get the remaining words to be matched
                    String remaining = targetWords.substring(element.length());
                    //recursively invoke the method to get the possible word combinations for the remaining words
                    List<List<String>> subResults = getMatchedPhoneWordsCombinations(remaining, dicWordsList);
                    List<String> newList = new ArrayList<String>();
                    //loop and add the the result list
                    for(List<String> li : subResults) {
                        newList.add(element);
                        newList.addAll(li);
                        results.add(newList);
                    }
                }
            }
        }

        return results;
    }

    /**
     * This method prints and phone number and its matched phone words combinations
     * @param matchedPhoneWordsMap Matched phone words combinations map
     */
    public void printMatchedPhoneWords(Map<String, List<List<String>>> matchedPhoneWordsMap){
        for(Map.Entry<String, List<List<String>>> entry : matchedPhoneWordsMap.entrySet()){
            //prints no matched phone words for the phone number that has no matched phone words combinations
            if(entry.getValue().size() == 0) {
                System.out.println("There are no matched phone words for phone number " + entry.getKey());
            }else{
                //prints all possible matched phone words for the phone number that has matched phone words combinations
                System.out.println("Matched phone words for phone number " + entry.getKey() + " are: " );
                for(List<String> phoneWordsCombination : entry.getValue()){
                    System.out.print("[");
                    for(int i = 0; i < phoneWordsCombination.size(); i++){
                        String phoneWord = phoneWordsCombination.get(i);
                        if(i < phoneWordsCombination.size()-1){
                            System.out.print(phoneWord+"-");
                        }
                        else{
                            System.out.print(phoneWord);
                        }
                    }
                    System.out.println("]");
                }
            }
        }
    }

}
