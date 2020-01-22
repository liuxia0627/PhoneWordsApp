package com.bgl.phonewordsapp.test;

import com.bgl.phonewordsapp.main.PhoneWordsAppDemo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PhoneWordsAppDemoTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams(){
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testMainWithoutPassDictionaryFilePath(){
        System.setProperty("dictionary","");
        String [] args = new String [1];
        args[0] = "phoneNumbers.txt";
        PhoneWordsAppDemo.main(args);
    }

    @Test
    public void testMainWithoutPassPhoneNumbersFilePath(){
        System.setProperty("dictionary","dictionary.txt");
        String [] args = new String [1];
        args[0] = "";
        PhoneWordsAppDemo.main(args);
    }

    @Test
    public void testMainWithPassAllFilePaths() throws Exception{
        System.setProperty("dictionary","dictionary.txt");
        String [] args = new String [1];
        args[0] = "phoneNumbers.txt";

        List<String> dictWordsList = Arrays.asList("ST,AR", "PL ACES", "HEL!LO", "WOR.LD", "WA?RS", "KITTY");
        Path dictFile = Paths.get("dictionary.txt");
        Files.write(dictFile, dictWordsList, StandardCharsets.UTF_8);

        List<String> phoneNumbersList = Arrays.asList("78 27927 71");
        Path phoneNumbersFile = Paths.get("phoneNumbers.txt");
        Files.write(phoneNumbersFile, phoneNumbersList, StandardCharsets.UTF_8);

        PhoneWordsAppDemo.main(args);
        assertEquals("Matched phone words for phone number 782792771 are: \r\n[STAR-WARS-1]\r\n", outContent.toString());

        Files.delete(dictFile);
        Files.delete(phoneNumbersFile);
    }


    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}