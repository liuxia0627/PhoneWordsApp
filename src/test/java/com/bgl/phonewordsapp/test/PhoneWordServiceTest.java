package com.bgl.phonewordsapp.test;

import com.bgl.phonewordsapp.service.PhoneWordService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

public class PhoneWordServiceTest {
    private PhoneWordService phoneWordService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams(){
        this.phoneWordService = new PhoneWordService();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testGetSingleMatchedPhoneWordsCombination(){
        String targetWords = "THEQUICKBROWNFOX";
        List<String> dicWordsList = Arrays.asList("THE","QUICK","BROWN","FOX","JUMPS","OVER","LAZY","DOG");
        List<List<String>> expected = new ArrayList<List<String>>();
        expected.add(Arrays.asList("THE","QUICK","BROWN","FOX"));
        List<List<String>> actual = phoneWordService.getMatchedPhoneWordsCombinations(targetWords, dicWordsList);
        //1. Test equal.
        assertThat(actual, is(expected));
        //2. Check List Size
        assertThat(actual.size(), is(expected.size()));
        //3. If List has this value?
        assertThat(actual, hasItems(Arrays.asList("THE","QUICK","BROWN","FOX")));
        //4. Ensure Correct order
        assertThat(actual.get(0), contains("THE","QUICK","BROWN","FOX"));
        //5. check empty list
        assertThat(actual, not(IsEmptyCollection.empty()));
    }

    public void testGetMultipleMatchedPhoneWordsCombinations(){
        String targetWords = "THEQUICKBROWNFOX";
        List<String> dicWordsList = Arrays.asList("THE","QUICK","BROWN","FOX", "THEQ", "UICKB", "ROW", "NFOX");
        List<List<String>> expected = new ArrayList<List<String>>();
        expected.add(Arrays.asList("THE","QUICK","BROWN","FOX"));
        expected.add(Arrays.asList("THEQ","UICKB","ROW","NFOX"));
        List<List<String>> actual = phoneWordService.getMatchedPhoneWordsCombinations(targetWords, dicWordsList);
        //1. Test equal.
        assertThat(actual, is(expected));
        //2. Check List Size
        assertThat(actual.size(), is(expected.size()));
        //3. If List has this value?
        assertThat(actual, hasItems(Arrays.asList("THE","QUICK","BROWN","FOX")));
        assertThat(actual, hasItems(Arrays.asList("THEQ","UICKB","ROW","NFOX")));
        //4. Ensure Correct order
        assertThat(actual.get(0), contains("THE","QUICK","BROWN","FOX"));
        assertThat(actual.get(1), contains("THEQ","UICKB","ROW","NFOX"));
        //5. check empty list
        assertThat(actual, not(IsEmptyCollection.empty()));
    }

    @Test
    public void testGetNoMatchedPhoneWordsCombinations() {
        String targetWords = "THEQUICKBROWNFOX";
        List<String> dicWordsList = Arrays.asList("THE", "JUMPS", "OVER", "LAZY", "DOG");
        List<List<String>> expected = new ArrayList<List<String>>();
        List<List<String>> actual = phoneWordService.getMatchedPhoneWordsCombinations(targetWords, dicWordsList);
        //1. Test equal.
        assertThat(actual, is(expected));
        //2. Check List Size
        assertThat(actual.size(), is(expected.size()));
        //3. check empty list
        assertThat(actual, IsEmptyCollection.empty());
    }

    @Test
    public void testPrintMatchedPhoneWords(){
        Map<String, List<List<String>>> matchedPhoneWordsMap = new LinkedHashMap<>();
        List<List<String>> list = new ArrayList<>();
        list.add(Arrays.asList("BROWN","FOX"));
        matchedPhoneWordsMap.put("27696369", list);
        phoneWordService.printMatchedPhoneWords(matchedPhoneWordsMap);
        assertEquals("Matched phone words for phone number 27696369 are: \r\n[BROWN-FOX]\r\n", outContent.toString());
    }

    @Test
    public void testNoPrintMatchedPhoneWords(){
        Map<String, List<List<String>>> matchedPhoneWordsMap = new LinkedHashMap<>();
        List<List<String>> list = new ArrayList<>();
        matchedPhoneWordsMap.put("27696369", list);
        phoneWordService.printMatchedPhoneWords(matchedPhoneWordsMap);
        assertEquals("There are no matched phone words for phone number 27696369\r\n", outContent.toString());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}