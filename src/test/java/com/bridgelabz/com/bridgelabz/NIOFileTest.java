package com.bridgelabz.com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class NIOFileTest {
    public static  String HOME = System.getProperty("user.home");
    public static  String PlayWithNio = "TempPlayGround";
    Path playPath = Paths.get(HOME+"/"+PlayWithNio);



    @Test
    public void givenPathThenCheckOperationShouldReturnData() {
        // checking for file exist
        Path homePath = Paths.get(HOME);
        Assert.assertTrue(Files.exists(homePath));
    }
    // delete file and check File Not exist

    @Test
    public void deletePathAndReturnFileNotExist()  {

        if (Files.exists(playPath)) {
         //  FilesUtil.deleteFiles(playPath.toFile());
        }
        Assert.assertTrue(Files.notExists(playPath));


    }


    @Test
    public void createADirectoryWithFile()throws IOException {
        // create directory
        Files.createDirectory(playPath);
        Assert.assertTrue(Files.exists(playPath));
    }

    // create empty file

    @Test
    public void createfile() {
        IntStream.range(1,10).forEach(n ->{
            Path tempFile = Paths.get(playPath+"/temp"+n);
            Assert.assertTrue(Files.exists(tempFile));
            try {
                Files.createFile(tempFile);
            } catch (IOException e) {
               Assert.assertTrue(Files.exists(tempFile));
            }
        });
    }
    // list files directories as well as files with extension

    @Test
    public void listFilesDirectoriesAsWellAsFilesWithExtension() throws IOException {
        Files.list(playPath).filter(Files :: isRegularFile).forEach(System.out :: println);
        Files.newDirectoryStream(playPath).forEach(System.out :: println);
        Files.newDirectoryStream(playPath, Path -> Path.toFile().isFile() && Path.toString().startsWith("temp")).forEach(System.out :: println);
    }
}
