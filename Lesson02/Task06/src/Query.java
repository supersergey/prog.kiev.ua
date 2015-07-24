import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by user on 24.07.2015.
 */
@XmlRootElement(name="query")
public class Query {

    private Results results;

    @XmlElement(name="results")
    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public Query(Results results) {
        this.results = results;
    }

    public Query() {
    }
}
