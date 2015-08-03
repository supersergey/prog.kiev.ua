package ua.kiev.prog;

import java.io.*;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private void doPost(OutputStream os, String request) throws IOException
    {
        // String content = "Test POST reply";
        int currentIndex = 0;
        int boundaryIndex = request.indexOf("boundary=") + "boundary=".length();
        String boundary = request.substring(boundaryIndex, request.indexOf("\r\n", boundaryIndex));

        currentIndex = boundaryIndex + boundary.length() + 2;
        currentIndex = request.indexOf(boundary, currentIndex);
        currentIndex = request.indexOf("\r\n\r\n", currentIndex) + 4;
        int payLoadBeginIndex = currentIndex;
        currentIndex = request.indexOf(boundary, currentIndex) - 4;

        String payload = request.substring(payLoadBeginIndex, currentIndex);

        FileOutputStream fos = new FileOutputStream("c:\\temp\\tempfile.txt");
        fos.write(payload.getBytes());
        fos.flush();
        fos.close();

        List<String> headers = new ArrayList<String>();
        headers.add("HTTP/1.1 200 OK\r\n");
        headers.add("Content-Length: " + request.length() + "\r\n");
        headers.add("Connection: close\r\n\r\n");
        os.write(getBinaryHeaders(headers));
        os.write(request.getBytes());
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

        FileWriter writer = new FileWriter("c:\\temp\\request.txt");
        writer.write(request);
        writer.flush();
        writer.close();
        // System.out.println(request);
        System.out.println("---------------------------------------------");

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
            byte[] buf = new byte[64 * 1024];
            // byte[] temp;
            int readBytesCount;

                try {
                    /*do {
                        readBytesCount = is.read(buf);
                        if (readBytesCount == -1) {
                            // no more data left
                            break;
                        }
                        else
                            bs.write(buf, 0, readBytesCount);


                    } while ( (! Thread.currentThread().isInterrupted()));*/
                    byte[] temp = new byte[in.readInt()];
                    in.readFully(temp);
                    String request = temp.toString();
                    process(request, os);

                } finally {
                    socket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }
}