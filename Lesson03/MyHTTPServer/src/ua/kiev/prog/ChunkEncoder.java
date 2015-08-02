package ua.kiev.prog;

import java.io.*;

/**
 * Created by sergey on 01.08.2015.
 */
public class ChunkEncoder extends OutputStream {

    private ByteArrayOutputStream outputStream;
    private int chunkSize;

    public ChunkEncoder(int chunkSize) {
        this.chunkSize = chunkSize;
        outputStream = new ByteArrayOutputStream();
    }

    @Override
    public void flush() throws IOException {
        outputStream.write("0\r\n\r\n".getBytes()); // any chunk must end with this sequence
        super.flush();
    }

    @Override
    public void write(int b) throws IOException {
        String head = Integer.toHexString(1) + "\r\n";
        outputStream.write(head.getBytes());
        outputStream.write(b);
        outputStream.write("\r\n".getBytes());
    }

    @Override
    public void write(byte[] b) throws IOException {
        try {

            int n = b.length / chunkSize;
            int tail = b.length % chunkSize;
            int offset = 0;
            String head = Integer.toHexString(chunkSize) + "\r\n";

            for (int i = 0; i < n; i++) {
                outputStream.write(head.getBytes());
                outputStream.write(b, offset, chunkSize);
                outputStream.write("\r\n".getBytes());
                offset += chunkSize;
            }
            if (tail > 0) {
                head = Integer.toHexString(tail) + "\r\n";
                outputStream.write(head.getBytes());
                outputStream.write(b, offset, tail);
                outputStream.write("\r\n".getBytes());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public static void main(String[] args) throws IOException
    {
        FileInputStream fis = new FileInputStream("c:\\temp\\index.html");
        byte[] buf = new byte[fis.available()];
        fis.read(buf);
        ChunkEncoder chunkEncoder = new ChunkEncoder(51);
        // chunkEncoder.write(0x61);
        chunkEncoder.write(buf);
        chunkEncoder.flush();
        FileOutputStream fos = new FileOutputStream("c:\\temp\\chunkoutput.txt");
        fos.write(chunkEncoder.getOutputStream().toByteArray());
        fos.flush();
        fos.close();
    }
}
