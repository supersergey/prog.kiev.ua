package XMLGenerator;

import ua.kiev.prog.Advertisement;
import ua.kiev.prog.Advertisements;
import ua.kiev.prog.Photo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;

/**
 * Created by st on 27.08.2015.
 */
public class Main {

    public static void main(String[] args) throws Exception
    {
        RandomAccessFile raf = new RandomAccessFile("c:\\temp\\yacht.jpg", "r");
        byte[] buf = new byte[(int) raf.length()];
        raf.read(buf);
        Photo photo1 = new Photo("photo1", buf);
        raf = new RandomAccessFile("c:\\temp\\bmw.jpg", "r");
        buf = new byte[(int) raf.length()];
        raf.read(buf);
        Photo photo2 = new Photo("photo2", buf);
        Advertisement a1 = new Advertisement("Yacht", "Beneteau 43", "Sailing yacht in nice condition", "+38050484838382", 300000, photo1, false);
        Advertisement a2 = new Advertisement("Car", "BMW 3", "BMW black", "+44202019298201", 20000, photo2, false);
        Advertisement a3 = new Advertisement("Bike", "Honda CBR600", "Honda CBR600 F4i Red 2012", "+65741657324165", 5000, null, false);
        Advertisements ads = new Advertisements();
        ads.getAdvertisements().add(a1);
        ads.getAdvertisements().add(a2);
        ads.getAdvertisements().add(a3);

        File file = new File("output.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Advertisements.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(ads, file);
    }
}
