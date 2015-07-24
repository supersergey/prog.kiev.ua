import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24.07.2015.
 */
@XmlRootElement(name="garage")
public class Garage {
    private List<Vehicle> vehicles = new ArrayList<>();

    public Garage() {
    }

    public Garage(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @XmlElement(name="vehicles")
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
