package ua.kiev.prog;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sergey on 02.08.2015.
 */
public class TestEncoderDecoder {

    public static void main(String[] args) throws IOException
    {
        FileInputStream fis = new FileInputStream("c:\\temp\\DSC_7714.JPG");
        FileOutputStream fos = new FileOutputStream("c:\\temp\\chunkoutput.txt");
        ChunkEncoder chunkEncoder = new ChunkEncoder(150);
        /*byte[] input = new byte[fis.available()];
        int r = fis.read(input);
        if (r > 0)
        {
            chunkEncoder.write(input);
            chunkEncoder.flush();
            chunkEncoder.close();
        }
        fis.close();*/
        chunkEncoder.write(0x61);
        chunkEncoder.write(0x62);
        chunkEncoder.write(0x63);
        chunkEncoder.write(0x64);
        chunkEncoder.flush();
        fos.write(chunkEncoder.getOutputStream().toByteArray());
        chunkEncoder.close();
        fos.flush();
        fos.close();


        fis = new FileInputStream("c:\\temp\\chunkoutput.txt");
        ChunkDecoder chunkDecoder = new ChunkDecoder(fis);
        byte[] output = new byte[fis.available()];
        int r = chunkDecoder.read(output);
        fos = new FileOutputStream("c:\\temp\\decoded.txt");
        fos.write(output, 0, r);
        fos.flush();
        fos.close();
        fis.close();
        chunkDecoder.close();
    }
}
