import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by user on 24.07.2015.
 */
@XmlRootElement(name="vehicle")
public class Vehicle {
    private String name;
    private String model;
    private int cc;
    private String color;

    public Vehicle() {
    }

    public Vehicle(String name, String model, int cc, String color) {
        this.name = name;
        this.model = model;
        this.cc = cc;
        this.color = color;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @XmlElement
    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    @XmlElement
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
