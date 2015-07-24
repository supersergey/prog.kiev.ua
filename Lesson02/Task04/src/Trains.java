import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by st on 23.07.2015.
 */
@XmlRootElement(name="trains")
public class Trains {
    @XmlElement(name="train")
    private List<Train> trains = new ArrayList<>();

    public List<Train> getTrains() {
        return trains;
    }
}
