import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24.07.2015.
 */
@XmlRootElement(name="results")
public class Results {
    private Rate[] rate;

    @XmlElement(name="rate")
    public Rate[] getRate() {
        return rate;
    }

    public void setRate(Rate[] rate) {
        this.rate = rate;
    }

    public Results(Rate[] rate) {
        this.rate = rate;
    }

    public Results() {
    }
}
