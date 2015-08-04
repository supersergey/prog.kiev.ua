package ua.kiev.prog;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Client implements Runnable {
    private Socket socket;
    private FileManager fm;

    public Client(Socket socket, String path) {
        this.socket = socket;
        fm = new FileManager(path);
    }

    private void returnStatusCode(int code, OutputStream os) throws IOException {
        String msg = null;

        switch (code) {
            case 400:
                msg = "HTTP/1.1 400 Bad Request";
                break;
            case 404:
                msg = "HTTP/1.1 404 Not Found";
                break;
            case 500:
                msg = "HTTP/1.1 500 Internal Server Error";
                break;
        }

        byte[] resp = msg.concat("\r\n\r\n").getBytes();
        os.write(resp);
    }

    private byte[] getBinaryHeaders(List<String> headers) {
        StringBuilder res = new StringBuilder();

        for (String s : headers)
            res.append(s);

        return res.toString().getBytes();
    }

    private String getFileName(String source)
    {
        int filenameIndex = source.indexOf("filename=\"");
        int filenameEndIndex = source.indexOf("\"\r\n", filenameIndex);
        if (filenameIndex == -1 || filenameEndIndex == -1)
            return null;
        else
        return source.substring(filenameIndex + "filename=\"".length(), filenameEndIndex);
    }

    private void doPost(OutputStream os, String request) throws IOException
    {
        // String content = "Test POST reply";
        String boundary = "";

        int boundaryStart = request.indexOf("boundary=") + "boundary=".length();
        if (boundaryStart!=-1)
            boundary = request.substring(boundaryStart, request.indexOf("\r\n", boundaryStart));

        boundary = "--" + boundary;
        int payloadStart = request.indexOf(boundary);
        payloadStart += ((boundary).length() + 2);
        int payloadEnd = request.indexOf(boundary+"--") - 3;
        String[] payloads = request.substring(payloadStart, payloadEnd).split(boundary);
        String[] fileNames = new String[payloads.length];
        for (int i = 0; i < payloads.length; i++)
            fileNames[i] = getFileName(payloads[i]);
        for (int i=0; i<payloads.length; i++)
        {
            int payLoadBeginIndex = payloads[i].indexOf("\r\n\r\n") + 4;
            if (payLoadBeginIndex!=-1)
                payloads[i] = payloads[i].substring(payLoadBeginIndex, payloads[i].length());
        }

        FileOutputStream fos = new FileOutputStream("c:\\temp\\result.zip");
        ZipOutputStream zos = new ZipOutputStream(fos);
        for (int i=0; i<payloads.length; i++)
        {
            zos.putNextEntry(new ZipEntry(fileNames[i]));
            zos.write(payloads[i].getBytes(), 0, payloads[i].length());
            zos.closeEntry();
        }
        zos.flush();

        List<String> headers = new ArrayList<String>();
        headers.add("HTTP/1.1 201 Created\r\n");
        headers.add("Content-Length: " + request.length() + "\r\n");
        headers.add("Connection: close\r\n\r\n");
        os.write(getBinaryHeaders(headers));
        os.write("OK".getBytes());
    }

    private void doGet(OutputStream os, String url) throws IOException
    {
        if ("/".equals(url))
            url = "/index.html";

        List<String> headers = new ArrayList<String>();
        headers.add("HTTP/1.1 200 OK\r\n");

        byte[] content = fm.get(url);

        if (content == null) {
            returnStatusCode(404, os);
            return;
        }

        ProcessorsList pl = new ProcessorsList();
        pl.add(new Compressor(6));
        // pl.add(new Chunker(30)); // comment
        content = pl.process(content, headers);

        if (content == null) {
            returnStatusCode(500, os);
            return;
        }

        // uncomment next line
        headers.add("Content-Length: " + content.length + "\r\n");
        headers.add("Connection: close\r\n\r\n");
        os.write(getBinaryHeaders(headers));
        os.write(content);
    }

    private void process(String request, OutputStream os) throws IOException {

        // FileWriter writer = new FileWriter("c:\\temp\\request.txt");
        // writer.write(request);
        // writer.flush();
        // writer.close();
        // System.out.println(request);
        // System.out.println("---------------------------------------------");

        String originalRequest = request;
        int idx = request.indexOf("\r\n");
        request = request.substring(0, idx);

        String[] parts = request.split(" ");
        if (parts.length != 3) {
            returnStatusCode(400, os);
            return;
        }

        String method = parts[0], url = parts[1], version = parts[2];

        if ((!version.equalsIgnoreCase("HTTP/1.0")) && (!version.equalsIgnoreCase("HTTP/1.1"))) {
            returnStatusCode(400, os);
            return;
        }
        if (method.equalsIgnoreCase("GET")) {
            doGet(os, url);
        }
        else if (method.equalsIgnoreCase("POST"))
        {
            doPost(os, originalRequest);
        }
        else
            returnStatusCode(400, os);
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            DataInputStream in = new DataInputStream(is);

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            byte[] buf = new byte[64];
                try {
                    do {
                        int readCount = 1;
                        while ((readCount = in.read()) != -1 && in.available() > 0)
                        {
                            bs.write(readCount);
                        }
                        // System.out.println(bs.size());
                        byte[] temp = bs.toByteArray();
                        // FileOutputStream fos = new FileOutputStream("c:\\temp\\request.txt");
                        // fos.write(temp);
                        // fos.flush();
                        // fos.close();
                        String request = new String(temp);
                        process(request, os);

                    } while ( (! Thread.currentThread().isInterrupted()));


                } finally {
                    socket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }
}