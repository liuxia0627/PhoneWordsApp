package com.bgl.phonewordsapp.test;

import com.bgl.phonewordsapp.dao.FileDao;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.collection.IsEmptyCollection;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class FileDaoTest {

    private FileDao fileDao;

    @Before
    public void before(){
        this.fileDao = new FileDao();
    }

    @Test
    public void TestReadLinesFromNonexistentFile(){
        List<String> actual = fileDao.readLinesFromFile("NoneExistent.txt","");
        //check it is an empty list returned when read from a nonexistent file
        assertThat(actual, IsEmptyCollection.empty());
    }

    @Test
    public void TestReadLinesFromValidLettersOnlyAllowedFile() throws Exception{
        List<String> original = Arrays.asList("ST,AR", "PL ACES", "HEL!LO", "WOR.LD", "WA?RS", "KITTY");
        Path file = Paths.get("test.txt");
        Files.write(file, original, StandardCharsets.UTF_8);
        List<String> expected = Arrays.asList("STAR", "PLACES", "HELLO", "WORLD", "WARS", "KITTY");
        List<String> actual = fileDao.readLinesFromFile("test.txt","LETTER");
        //1. Test equal.
        assertThat(actual, is(expected));
        //2. Check List Size
        assertThat(actual.size(), is(expected.size()));
        //3. If List has this value?
        assertThat(actual, hasItems("HELLO"));
        //4. Ensure Correct order
        assertThat(actual, contains("STAR", "PLACES", "HELLO", "WORLD", "WARS", "KITTY"));
        //5. check empty list
        assertThat(actual, not(IsEmptyCollection.empty()));

        Files.delete(file);
    }

    @Test
    public void TestReadLinesFromInvalidLettersOnlyAllowedFile() throws Exception{
        List<String> expected = Arrays.asList("ST,AR1", "PLACES2", "HELLO3", "WORLD4", "WARS5", "KITTY6");
        Path file = Paths.get("test.txt");
        Files.write(file, expected, StandardCharsets.UTF_8);
        List<String>   actual = fileDao.readLinesFromFile("test.txt","LETTER");
        //check it is an empty list returned when read from a nonexistent file
        assertThat(actual, IsEmptyCollection.empty());
        Files.delete(file);
    }

    @Test
    public void TestReadLinesFromValidNumbersOnlyAllowedFile() throws Exception{
        List<String> original = Arrays.asList("7901 38 8", "2323,987", "2?39842!55", "875.93 27");
        Path file = Paths.get("test.txt");
        Files.write(file, original, StandardCharsets.UTF_8);
        List<String> expected = Arrays.asList("7901388", "2323987", "23984255", "8759327");
        List<String> actual = fileDao.readLinesFromFile("test.txt","NUMBER");
        //1. Test equal.
        assertThat(actual, is(expected));
        //2. Check List Size
        assertThat(actual.size(), is(expected.size()));
        //3. If List has this value?
        assertThat(actual, hasItems("7901388"));
        //4. Ensure Correct order
        assertThat(actual, contains("7901388", "2323987", "23984255", "8759327"));
        //5. check empty list
        assertThat(actual, not(IsEmptyCollection.empty()));

        Files.delete(file);
    }

    @Test
    public void TestReadLinesFromInvalidNumbersOnlyAllowedFile() throws Exception{
        List<String> expected = Arrays.asList("7901 S38 8", "23V23,S987", "2?3A984S2!55", "87GK5.93 K27");
        Path file = Paths.get("test.txt");
        Files.write(file, expected, StandardCharsets.UTF_8);
        List<String>   actual = fileDao.readLinesFromFile("test.txt","NUMBER");
        //check it is an empty list returned when read from a nonexistent file
        assertThat(actual, IsEmptyCollection.empty());
        Files.delete(file);
    }

    @Test
    public void TestReadLinesFromOtherTypeFile() throws Exception{
        List<String> original = Arrays.asList("7901 S38 8", "23V23,S987", "2?3A984S2!55", "87GK5.93 K27");
        List<String> expected = Arrays.asList("7901S388", "23V23S987", "23A984S255", "87GK593K27");
        Path file = Paths.get("test.txt");
        Files.write(file, expected, StandardCharsets.UTF_8);
        List<String>   actual = fileDao.readLinesFromFile("test.txt","");
        //1. Test equal.
        assertThat(actual, is(expected));
        //2. Check List Size
        assertThat(actual.size(), is(expected.size()));
        //3. If List has this value?
        assertThat(actual, hasItems("7901S388"));
        //4. Ensure Correct order
        assertThat(actual, contains("7901S388", "23V23S987", "23A984S255", "87GK593K27"));
        //5. check empty list
        assertThat(actual, not(IsEmptyCollection.empty()));
        Files.delete(file);
    }
}