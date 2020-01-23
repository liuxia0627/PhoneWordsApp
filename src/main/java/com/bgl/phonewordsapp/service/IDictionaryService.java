package com.bgl.phonewordsapp.service;

import java.util.List;

/**
 * This interface provides API to get the list of dictionary words by reading lines from dictionary file
 * @author rogerwill
 */
public interface IDictionaryService {
    public List<String> getDictionaryWordsList(String dictionaryFilePath);
}
