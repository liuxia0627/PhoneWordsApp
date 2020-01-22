package com.bgl.phonewordsapp.service;

import com.bgl.phonewordsapp.dao.FileDao;
import java.util.List;

/**
 * This service class provides API to get the list of dictionary words by reading lines from dictionary file
 * @author rogerwill
 *
 */
public class DictionaryService {
    private FileDao fileDao;

    public DictionaryService(FileDao fileDao){
        this.fileDao = fileDao;
    }

    public List<String> getDictionaryWordsList(String dictionaryFilePath){
        List<String> dictionaryWordsList = fileDao.readLinesFromFile(dictionaryFilePath, "LETTER");
        if(dictionaryWordsList.size() > 0){
            //Add default dictionary words for digits 1 and 2 to the list
            dictionaryWordsList.add("1");
            dictionaryWordsList.add("2");
        }
        return dictionaryWordsList;
    }
}
