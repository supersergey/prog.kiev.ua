package TestServletPackage;

import java.util.*;

/**
 * Created by user on 05.08.2015.
 */
public class SurveyStatistics {
    private static SurveyStatistics ourInstance = new SurveyStatistics();

    private static List<String> names = new ArrayList<>();
    private static List<String> drinks = new ArrayList<>();
    private static List<String> musics = new ArrayList<>();
    private static List<String> ages = new ArrayList<>();

    public static void addSurveyEntry(SurveyData entry)
    {
        names.add(entry.getName());
        drinks.add(entry.getDrinks());
        ages.add(entry.getAges());
        for (String music : entry.getMusics())
            musics.add(music);
    }

    public static Map<Integer, String> getStatistics(List<String> field)
    {
        Set<String> uniqueEntries = new HashSet<>();
        for (String s : field)
            uniqueEntries.add(s);
        for (S)

        HashMap<String, Integer> result = new HashMap<>();
        List<String> temp = new ArrayList<>();

        if (!temp.contains(s))
            temp.add(s);
        for (String s : temp)
        result.put(field.)


        return result;
    }

    public static SurveyStatistics getInstance() {
        return ourInstance;
    }

    private SurveyStatistics() {
    }


}
