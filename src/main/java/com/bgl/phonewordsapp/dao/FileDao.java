package com.bgl.phonewordsapp.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This dao class provides File IO related API
 * @author rogerwill
 *
 */
public class FileDao {
    /**
     * This method reads each line of the specified file and store them in a list
     * all the punctuation and whitespaces in the lines are ignored while reading
     * @param filePath  path of file to be read
     * @param  type type of file to be read.
     * If type is NUMBER which means the file is phone numbers file that can only include numbers
     * If type is LETTER which means the file is dictionary file that can only include letters
     * @return List stores each line of file
     *
     */
    public List<String> readLinesFromFile(String filePath, String type) {
        List<String> list = new ArrayList<>();
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                //skip blank lines
                if(line.length() > 0) {
                    //remove punctuation and white spaces in each line
                    line = line.replaceAll("\\s|\\p{Punct}", "");
                    //if type is NUMBER means the file can only contain numbers
                    if(type.equals("NUMBER")){
                        //check contents for each line, add numbers to the list, and throw exception when there is texts other than numbers
                        if(line.matches("^[0-9]+$")){
                            list.add(line);
                        }else{
                            throw new RuntimeException("phone numbers file can only contain numbers!");
                        }
                    }
                    //if type is LETTER means the file can only contain letters
                    else if(type.equals("LETTER")){
                        //check contents for each line, add letters to the list, and throw exception when there is texts other than letters
                        if(line.matches("^[a-zA-Z]*$")){
                            list.add(line);
                        }else{
                            throw  new RuntimeException("dictionary file can only contain letters!");
                        }
                    }
                    else{
                        list.add(line);
                    }

                }
            }
            return list;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        finally {
            try {
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
