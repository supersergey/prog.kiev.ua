import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by st on 03.09.2015.
 */
public class Sheets implements Serializable{
    private List<Sheet> sheets = new ArrayList<>();

    public Sheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public Sheets() {
    }

    public List<Sheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public void addSheet(Sheet sheet)
    {
        sheets.add(sheet);
    }
}
