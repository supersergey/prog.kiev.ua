package ua.kiev.prog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by st on 26.08.2015.
 */
@XmlRootElement(name="advertisements")
public class Advertisements {

    private List<Advertisement> advertisements = new ArrayList<>();

    public Advertisements() {
    }

    public Advertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    @XmlElement(name="advertisement")
    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
}
