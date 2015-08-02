package ua.kiev.prog;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;

/**
 * Created by sergey on 01.08.2015.
 */
public class ChunkDecoder extends InputStream {
    private InputStream inputStream;

    public ChunkDecoder(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private int findNextCRLF(byte[] buf, int currentPos)
    {
        // returns the position of the next \r\n
        // if not found, returns -1
        do {
            if (buf[currentPos]==13 && buf[currentPos+1]==10)
                return currentPos;
            else currentPos++;
        }
        while (currentPos < buf.length);
        return -1;
    }

    @Override
    public int read(byte[] b) throws IOException {
        // b - output array without chunks

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] inputArray = new byte[inputStream.available()];
        int inputSize = inputStream.read(inputArray);
        int inputArrayPos = 0;
        int writeCounter = 0; // the output size

        if (inputSize < 11)
            return -1; // valid chunk must be at least 11 bytes, if not - exit

        if ((inputArray[inputSize - 1] != 10)
                || (inputArray[inputSize - 2] != 13)
                || (inputArray[inputSize - 3] != 10)
                || (inputArray[inputSize - 4] != 13)
                || (inputArray[inputSize - 5] != 0x30))
            return -1; // valid chunk must end with 0\r\n\r\n, if not - exit

        do {
            int CRLFPoisition = findNextCRLF(inputArray, inputArrayPos);
            if (CRLFPoisition == -1)
                return -1; // invalid chunk
            byte[] lengthValue = new byte[CRLFPoisition - inputArrayPos];
            System.arraycopy(inputArray, inputArrayPos, lengthValue, 0, CRLFPoisition - inputArrayPos); // read the chunk length
            try
            {
                int chunkLength = Integer.parseInt(new String(lengthValue), 16);
                if (chunkLength <1 || chunkLength > inputSize)
                {
                    return -1; // invalid chunk
                }
                baos.write(inputArray, inputArrayPos + lengthValue.length + 2, chunkLength);
                inputArrayPos+= (lengthValue.length + 2 + chunkLength + 2); // chunk length identifier + \r\n + chunk + \r\n
                writeCounter+=chunkLength;
            }
            catch (NumberFormatException e)
            {
                return -1; // invalid chunk
            }
        }
        while (inputArrayPos < (inputSize-5)); // end sequence 0\r\n\r\n\ = 5 bytes
        baos.flush();
        System.arraycopy(baos.toByteArray(), 0, b, 0, writeCounter);
        baos.close();
        // System.out.println("The chunk is valid.");
        return writeCounter;
    }

    @Override
    public int read() throws IOException {
        return -1;
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("c:\\temp\\chunkoutput.txt");
        ChunkDecoder chunkDecoder = new ChunkDecoder(fis);
        byte[] content = new byte[chunkDecoder.available()];
        int size = chunkDecoder.read(content);
        System.out.println("Bytes read: " + size);
        fis.close();
        chunkDecoder.close();
    }
}
