/* Task: parse trains.xml file, filter trains with the specified departure time (15:00 - 19:00) */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by st on 23.07.2015.
 */
public class Main {

    public static void main(String[] args) {
        final File file = new File("trains.xml");
        Trains trains = new Trains();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(trains.getClass());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            trains = (Trains) unmarshaller.unmarshal(file);

            for (Train train : trains.getTrains())
            {
                String[] departure = train.getDeparture().split(":");
                if (Integer.parseInt(departure[0])>=15 && Integer.parseInt(departure[0])<19)
                    System.out.println(train);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
