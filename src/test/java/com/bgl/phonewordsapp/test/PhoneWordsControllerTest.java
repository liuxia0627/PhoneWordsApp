package com.bgl.phonewordsapp.test;

import com.bgl.phonewordsapp.controller.PhoneWordsController;
import com.bgl.phonewordsapp.dao.FileDao;
import com.bgl.phonewordsapp.service.DictionaryService;
import com.bgl.phonewordsapp.service.PhoneNumberService;
import com.bgl.phonewordsapp.service.PhoneWordService;
import org.hamcrest.collection.IsMapContaining;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class PhoneWordsControllerTest {
    private PhoneWordsController phoneWordsController;
    private FileDao fileDao;
    private PhoneWordService phoneWordService;
    private PhoneNumberService phoneNumberService;
    private DictionaryService dictionaryService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void before(){
        this.fileDao = new FileDao();
        this.phoneWordService = new PhoneWordService();
        this.phoneNumberService = new PhoneNumberService(fileDao);
        this.dictionaryService = new DictionaryService(fileDao);
        this.phoneWordsController = new PhoneWordsController(phoneWordService, phoneNumberService, dictionaryService);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testPrintMatchedPhoneWords(){
        Map<String, List<List<String>>> matchedPhoneWordsMap = new LinkedHashMap<>();
        List<List<String>> list = new ArrayList<>();
        list.add(Arrays.asList("BROWN","FOX"));
        matchedPhoneWordsMap.put("27696369", list);
        phoneWordsController.printMatchedPhoneWords(matchedPhoneWordsMap);
        assertEquals("Matched phone words for phone number 27696369 are: \r\n[BROWN-FOX]\r\n", outContent.toString());
    }

    @Test
    public void testGetMatchedPhoneWordsMap() throws Exception{
        List<String> dictWordsList = Arrays.asList("ST,AR", "PL ACES", "HEL!LO", "WOR.LD", "WA?RS", "KITTY");
        Path dictFile = Paths.get("dict.txt");
        Files.write(dictFile, dictWordsList, StandardCharsets.UTF_8);

        List<String> phoneNumbersList = Arrays.asList("54,889", "78 27927 71");
        Path phoneNumbersFile = Paths.get("phoneNumbers.txt");
        Files.write(phoneNumbersFile, phoneNumbersList, StandardCharsets.UTF_8);

        Map<String, List<List<String>>> expected = new LinkedHashMap<>();
        List<List<String>> wordList1 = new ArrayList<>();
        List<List<String>> wordList2 = new ArrayList<>();
        wordList1.add(Arrays.asList("KITTY"));
        wordList2.add(Arrays.asList("STAR", "WARS", "1"));
        expected.put("54889", wordList1);
        expected.put("782792771", wordList2);

        Map<String, List<List<String>>> actual = phoneWordsController.getMatchedPhoneWordsMap("phoneNumbers.txt", "dict.txt");

        //1. Test equal, ignore order
        assertThat(actual, is(expected));
        //2. Test size
        assertThat(actual.size(), is(expected.size()));
        //3. Test map entry, best!

        assertThat(actual, IsMapContaining.hasEntry("54889", wordList1));
        assertThat(actual, IsMapContaining.hasEntry("782792771", wordList2));
        //4. Test map key
        assertThat(actual, IsMapContaining.hasKey("54889"));
        assertThat(actual, IsMapContaining.hasKey("782792771"));
        //5. Test map value
        assertThat(actual, IsMapContaining.hasValue(wordList1));
        assertThat(actual, IsMapContaining.hasValue(wordList2));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}