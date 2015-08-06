package Anketa;

import sun.misc.Sort;

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

    public void addSurveyEntry(SurveyData entry)
    {
        names.add(entry.getName());
        drinks.add(entry.getDrinks());
        ages.add(entry.getAges());
        for (String music : entry.getMusics())
            musics.add(music);
    }

    public int getNumberOfEntries()
    {
        return names.size();
    }

    private Map<String, Integer> getStatistics(List<String> field)
    {
        Set<String> uniqueEntries = new HashSet<>(field);
        Map<String, Integer> result = new HashMap<>();

        for (String s : uniqueEntries)
        {
            int occurencies = Collections.frequency(field, s);
            result.put(s, occurencies);
        }
        return result;
    }

    public Map<String, Integer> getNamesStatistics()
    {
        return getStatistics(names);
    }

    public Map<String, Integer> getDrinksStatistics()
    {
        return getStatistics(drinks);
    }

    public Map<String, Integer> getMusicsStatistics()
    {
        return getStatistics(musics);
    }

    public Map<String, Integer> getAgesStatistics()
    {
        return getStatistics(ages);
    }

    public static SurveyStatistics getInstance() {
        return ourInstance;
    }

    private SurveyStatistics() {
    }


}
