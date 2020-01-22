package com.bgl.phonewordsapp.test;

import com.bgl.phonewordsapp.dao.FileDao;
import com.bgl.phonewordsapp.service.PhoneNumberService;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.collection.IsMapContaining;

public class PhoneNumberServiceTest {

    private FileDao fileDao;
    private PhoneNumberService phoneNumberService;

    @Before
    public void before(){
        this.fileDao = new FileDao();
        this.phoneNumberService = new PhoneNumberService(fileDao);
    }

    @Test
    public void testGetPhoneNumToPhoneWordsMapWithValidFile() throws Exception{
        Map<String, List<String>> expected = new LinkedHashMap();
        expected.put("23", Arrays.asList("AD","AE","AF","BD","BE","BF","CD","CE","CF"));
        expected.put("34", Arrays.asList("DG","DH","DI","EG","EH","EI","FG","FH","FI"));
        List<String> phoneNumbersList = Arrays.asList("23","34");
        Path file = Paths.get("test.txt");
        Files.write(file, phoneNumbersList, StandardCharsets.UTF_8);

        Map<String, List<String>> actual = phoneNumberService.getPhoneNumToPhoneWordsMap("test.txt");

        //1. Test equal, ignore order
        assertThat(actual, is(expected));
        //2. Test size
        assertThat(actual.size(), is(expected.size()));
        //3. Test map entry, best!
        assertThat(actual, IsMapContaining.hasEntry("23", Arrays.asList("AD","AE","AF","BD","BE","BF","CD","CE","CF")));
        assertThat(actual, IsMapContaining.hasEntry("34", Arrays.asList("DG","DH","DI","EG","EH","EI","FG","FH","FI")));
        //4. Test map key
        assertThat(actual, IsMapContaining.hasKey("23"));
        assertThat(actual, IsMapContaining.hasKey("34"));
        //5. Test map value
        assertThat(actual, IsMapContaining.hasValue(Arrays.asList("AD","AE","AF","BD","BE","BF","CD","CE","CF")));
        assertThat(actual, IsMapContaining.hasValue(Arrays.asList("DG","DH","DI","EG","EH","EI","FG","FH","FI")));
        Files.delete(file);
    }

    @Test
    public void testGetPhoneNumToPhoneWordsMapWithEmptyFile() throws Exception{
        Map<String, List<String>> expected = new LinkedHashMap();
        List<String> phoneNumbersList = new ArrayList<>();
        Path file = Paths.get("test.txt");
        Files.write(file, phoneNumbersList, StandardCharsets.UTF_8);
        Map<String, List<String>> actual = phoneNumberService.getPhoneNumToPhoneWordsMap("test.txt");
        //1. Test equal, ignore order
        assertThat(actual, is(expected));
        //2. Test size
        assertThat(actual.size(), is(expected.size()));
        Files.delete(file);

    }
}