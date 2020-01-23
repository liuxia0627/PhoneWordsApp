package com.bgl.phonewordsapp.dao;

import java.util.List;

/**
 * This dao interface provides File IO related API
 * @author rogerwill
 *
 */
public interface IFileDao {
    List<String> readLinesFromFile(String filePath, String type);
}
