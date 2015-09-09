import com.google.gdata.data.spreadsheet.SpreadsheetEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 09.09.2015.
 */
public class SpreadSheetDB {

    private List<SpreadsheetEntry> spreadsheets;

    public void addSpreadsheet(SpreadsheetEntry spreadsheetEntry)
    {
        spreadsheets.add(spreadsheetEntry);
    }

    public List<SpreadsheetEntry> getSpreadsheets()
    {
        return new LinkedList<>(spreadsheets);
    }

    private static SpreadSheetDB ourInstance = new SpreadSheetDB();

    public static SpreadSheetDB getInstance() {
        return ourInstance;
    }

    private SpreadSheetDB() {
    }
}
