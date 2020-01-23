package com.bgl.phonewordsapp.main;

import com.bgl.phonewordsapp.controller.PhoneWordsController;
import com.bgl.phonewordsapp.dao.FileDao;
import com.bgl.phonewordsapp.dao.IFileDao;
import com.bgl.phonewordsapp.service.*;

import java.util.*;

/**
 * This is the app entry point main method to execute all logics to get and printed all matched phone words for the provided phone numbers
 * @author rogerwill
 */
public class PhoneWordsAppDemo {

    public static void main(String[] args) {
        //check if path of the phone number files is passed by java command line args
        if (args.length == 0 || args[0].equals("")) {
            System.out.println("Please add the file name of phone number list as args in command line!");
            return;
        }
        //check if path of the dictionary file is passed by java command line option -Ddictionary
        if(System.getProperty("dictionary")==null || System.getProperty("dictionary").equals("")) {
            System.out.println("Please add the file name of dictionary as -Ddictionary <filepath> as option in command line!");
            return;
        }

        //get the file path of dictionary file and phone number file from command option and args
        String dictionaryFilePath = System.getProperty("dictionary");
        String phoneNumFilePath = args[0];

        //initialize dao and service layer beans
        IFileDao fileDao = new FileDao();
        IPhoneWordService phoneWordService = new PhoneWordService();
        IPhoneNumberService phoneNumberService = new PhoneNumberService(fileDao);
        IDictionaryService dictionaryService = new DictionaryService(fileDao);

        //initialize controller layer by inject day and service layer beans into controller layer
        PhoneWordsController phoneWordsController = new PhoneWordsController(phoneWordService, phoneNumberService, dictionaryService);

        //call API of controller layer to get the matched phone words combinations of the provide phone words
        Map<String, List<List<String>>> matchedPhoneWordsCombinationsMap = phoneWordsController.getMatchedPhoneWordsMap(phoneNumFilePath, dictionaryFilePath);

        //call API of controller layer to print the phone numbers and their matched phone words combinations
        phoneWordsController.printMatchedPhoneWords(matchedPhoneWordsCombinationsMap);
        

    }
}
