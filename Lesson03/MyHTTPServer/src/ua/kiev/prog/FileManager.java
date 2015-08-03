package ua.kiev.prog;

import java.io.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class FileManager {

    private class CachedFile
    {
        public byte[] buf;
        public long timeStamp;
    }

    private String path;
    private static ConcurrentHashMap<String, CachedFile> map = new ConcurrentHashMap<String, CachedFile>();

    public FileManager(String path) {
        // "c:\folder\" --> "c:\folder"
        if (path.endsWith("/") || path.endsWith("\\"))
            path = path.substring(0, path.length() - 1);

        this.path = path;
    }

    public byte[] get(String url) {
        final long cacheTime = 40 * 1000L; // 40 seconds
        try {
            CachedFile aFile = new CachedFile();

            if (map.containsKey(url)) // in cache
            {
                aFile = map.get(url);
                if ((new Date().getTime() - aFile.timeStamp) < cacheTime)
                    return aFile.buf;
            }

            // "c:\folder" + "/index.html" -> "c:/folder/index.html"
            String fullPath = path.replace('\\', '/') + url;

            RandomAccessFile f = new RandomAccessFile(fullPath, "r");

            try {
                aFile.buf = new byte[(int) f.length()];
                aFile.timeStamp = new Date().getTime();
                f.read(aFile.buf, 0, aFile.buf.length);
            } finally {
                f.close();
            }

            map.put(url, aFile); // put to cache
            return aFile.buf;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
