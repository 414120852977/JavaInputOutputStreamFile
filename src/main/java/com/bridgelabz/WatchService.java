package com.bridgelabz;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatchService {
    private  final WatchService watcher;
    private final Map<WatchKey, Path> dirWatchers;

    // create watch service and resister the given directory

    public WatchService(Path dir) throws IOException {
        this.watcher = (WatchService) FileSystems.getDefault().newWatchService();
        this.dirWatchers = new HashMap<WatchKey,Path>();
        ScanAndResisterDirectories(dir);
    }



    // resister the given directory with watch service
    public void resisterDirWatchers(Path dir) throws IOException {
        WatchKey key = dir.register((java.nio.file.WatchService) watcher,ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY);
        dirWatchers.put(key,dir);
    }
    // resister the given directory and all its sub directories with the watch service
    private void ScanAndResisterDirectories( final  Path start) throws IOException {
        // resister directory and subdirectory
        Files.walkFileTree(start,new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                resisterDirWatchers(dir);
                return FileVisitResult.CONTINUE;
            }
        });

    }
// process all events keys queued with watcher
    public void processEvents() {
        while (true) {
            WatchKey key ;
            key = watcher.take();
            Path dir = dirWatchers.get(key);
            if (dir == null) continue;
            for (WatchEvent<?> event : key.pollEvents()) {
            WatchEvent.Kind kind = event.kind();
            Path name = ((WatchEvent<Path>)event).context();
            Path child = dir.resolve(name);
                System.out.format("%s : %s\n",event.kind().name(),child);
                // if directory is created then resister it and its sub directories
                if (kind == ENTRY_CREATE) {
                    try{
                        if (Files.isDirectory(child)) ScanAndResisterDirectories(child) ;
                    }catch (IOException e) {

                    }
                }else if(kind.equals(ENTRY_DELETE)) {
                    if (Files.isDirectory(child)) dirWatchers.remove(key);
                }
            }
            // reset key and remove from set if dictionary no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                dirWatchers.remove(key);
                if (dirWatchers.isEmpty()) break; // all dictionaries are inaccessible
            }
        }
    }

    private WatchKey take() {
        return take();
    }

}
