package com.bgl.phonewordsapp.test;

import com.bgl.phonewordsapp.dao.FileDao;
import com.bgl.phonewordsapp.service.DictionaryService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

public class DictionaryServiceTest {
    private FileDao fileDao;
    private DictionaryService dictionaryService;

    @Before
    public void before(){
        this.fileDao = new FileDao();
        this.dictionaryService = new DictionaryService(fileDao);
    }

    @Test
    public void testGetDictionaryWordsListFromValidFile () throws  Exception{
        List<String> original = Arrays.asList("ST,AR", "PL ACES", "HEL!LO", "WOR.LD", "WA?RS", "KITTY");
        Path file = Paths.get("test.txt");
        Files.write(file, original, StandardCharsets.UTF_8);
        List<String> expected = Arrays.asList("STAR", "PLACES", "HELLO", "WORLD", "WARS", "KITTY", "1", "2");
        List<String> actual = dictionaryService.getDictionaryWordsList("test.txt");
        //1. Test equal.
        assertThat(actual, is(expected));
        //2. Check List Size
        assertThat(actual.size(), is(expected.size()));
        //3. If List has this value?
        assertThat(actual, hasItems("HELLO"));
        //4. Ensure Correct order
        assertThat(actual, contains("STAR", "PLACES", "HELLO", "WORLD", "WARS", "KITTY", "1", "2"));
        //5. check empty list
        assertThat(actual, not(IsEmptyCollection.empty()));

        Files.delete(file);


    }

}