package com.bgl.phonewordsapp.service;

/**
 * This service class provides API to encode provided phone numbers to possible letter combinations
 * based on the predefined digit-to-char table
 * @author rogerwill
 *
 */
import com.bgl.phonewordsapp.dao.FileDao;
import com.bgl.phonewordsapp.dao.IFileDao;

import java.util.*;

public class PhoneNumberService implements  IPhoneNumberService {
    private IFileDao fileDao;

    public PhoneNumberService(IFileDao fileDao){
        this.fileDao = fileDao;
    }

    /**
     * This method loops and encode each phone number in the phone numbers file to get the possible letter combinations for each number
     * @param phoneNumbersFilePath file path of the phone numbers file
     * @return Map stores each phone number as key and its encoded possible letter combinations list as value
     */
    public Map<String, List<String>> getPhoneNumToPhoneWordsMap(String phoneNumbersFilePath){
        Map<String, List<String>> phoneNumToPhoneWordsMap = new LinkedHashMap<>();
        List<String> phoneNumbersList = fileDao.readLinesFromFile(phoneNumbersFilePath, "NUMBER");

        for(String phoneNumber : phoneNumbersList){
            List<String> phoneWordsList = convertPhoneNumberToWords(phoneNumber);
            phoneNumToPhoneWordsMap.put(phoneNumber, phoneWordsList);
        }
        return phoneNumToPhoneWordsMap;
    }

    /**
     * This method encode the provided phone number based on the predefined digit-to-char table and get all the possible encoded letter combinations of it
     * @param phoneNumber provided phone number
     * @return List stores all the possible encoded letter combinations
     */
    public List<String> convertPhoneNumberToWords(String phoneNumber){
        List<String> result = new ArrayList<>();
        if(phoneNumber == null || phoneNumber.equals("")){
            return  result;
        }
        Map<Character, char[]> phoneWordsMap = getPhoneWordMap();
        StringBuilder sb = new StringBuilder();
        convertPhoneNumberToWordsHelper(phoneNumber, phoneWordsMap, sb, result);
        return result;
    }

    /**
     * This is a recursion method to get all the possible encoded letter combinations of the provided phone number
     * based on the predefined digit-to-char table
     * @param phoneNumber phoneNumber
     * @param phoneWordsMap predefined digit-to-char table
     * @param sb String Builder to append each digit's encoded character
     * @param result list to stores all the possible encoded letter combinations
     */
    private void convertPhoneNumberToWordsHelper(String phoneNumber, Map<Character, char[]> phoneWordsMap, StringBuilder sb, List<String> result){
        //if the length of encoded letters combinations
        // equal to the length of the phone number then add it to the result list
        if(sb.length() == phoneNumber.length()){
            result.add(sb.toString());
            return;
        }
        //loop each digit of the phone number and call the method self recursively to append the encoded characters to sb
        for(char ch : phoneWordsMap.get(phoneNumber.charAt(sb.length()))){
            sb.append(ch);
            convertPhoneNumberToWordsHelper(phoneNumber, phoneWordsMap, sb, result );
            //remove the appended characters to skip to the above level
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * This method creates the digit-to-char map based on predefined digit-to-char table
     * @return Map stores digit as key and its possible encoded character list as value
     */
    private Map<Character, char[]> getPhoneWordMap(){
        Map<Character, char[]> map = new HashMap<>();
        map.put('0', new char[]{'0'});
        map.put('1', new char[]{'1'});
        map.put('2', new char[]{'A','B','C'});
        map.put('3', new char[]{'D','E','F'});
        map.put('4', new char[]{'G','H','I'});
        map.put('5', new char[]{'J','K','L'});
        map.put('6', new char[]{'M','N','O'});
        map.put('7', new char[]{'P','Q','R','S'});
        map.put('8', new char[]{'T','U','V'});
        map.put('9', new char[]{'W','X','Y','Z'});
        return map;
    }
}
