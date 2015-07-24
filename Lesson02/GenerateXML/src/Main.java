import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by user on 24.07.2015.
 */
public class Main {
    public static void main(String[] args) throws IOException
    {
        final String filename = "garage.xml";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = "";

        Garage garage = new Garage();

        while (!"exit".equals(s))
        {
            Vehicle vehicle = new Vehicle();
            System.out.print("Name: ");
            vehicle.setName(br.readLine());
            System.out.print("Model: ");
            vehicle.setModel(br.readLine());
            System.out.print("cc: ");
            vehicle.setCc(Integer.parseInt(br.readLine()));
            System.out.print("Color: ");
            vehicle.setColor(br.readLine());
            System.out.print("Continue? ");
            s = br.readLine();
            garage.getVehicles().add(vehicle);
        }

        br.close();

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(garage.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(garage, new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
