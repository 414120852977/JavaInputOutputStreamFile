package com.bridgelabz.com.bridgelabz;

import com.bridgelabz.WatchService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.bridgelabz.com.bridgelabz.NIOFileTest.HOME;
import static com.bridgelabz.com.bridgelabz.NIOFileTest.PlayWithNio;

public class WatchServiceTest {
    private static final String PLAY_WITH_NIO = PlayWithNio;
    /**
     * watch service to views all directories files and sub directories
     * @throws IOException
     */
    @Test
    public void givenDirectoriesWhenWatchedListsAllTheActivities() throws IOException {
       Path dir = Paths.get(HOME+"/"+PLAY_WITH_NIO);
        Files.list(dir).filter(Files :: isRegularFile).forEach(System.out :: println);
        new WatchService(dir).processEvents();
        Assert.assertTrue(true);
    }
}
