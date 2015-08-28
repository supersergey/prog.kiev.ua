package mygroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28.08.2015.
 */
public class PizzaHtmlParsers {

    public static List<Pizza> parseVesuvio() {
        try {
            final String UrlVesuvio = "http://vesuvio.ua/catalog/pizza";
            final Document doc = Jsoup.connect(UrlVesuvio).get();
            final List<Pizza> result = new ArrayList<Pizza>();
            final Elements pizzaInfo = doc.select("div.info");
            for (Element e : pizzaInfo) {
                final Pizza pizza = new Pizza();
                pizza.setName(e.getElementsByClass("title").text());
                pizza.setDescription(e.getElementsByClass("text").text());
                final Elements pizzaDetails = e.getElementsByClass("attribute-option");
                for (Element pd : pizzaDetails) {
                    final int diameter = Integer.parseInt(pd.getElementsByClass("attribute-name").text().split(" ")[0]);
                    final int weight = Integer.parseInt(pd.getElementsByClass("attribute-weight").text().split(" ")[0]);
                    final float price = Float.parseFloat(pd.child(2).child(0).getElementsByClass("price-span").text().split(" ")[0]);
                    pizza.addPizzaVariant(new PizzaVariant(price, weight, diameter));
                }
                result.add(pizza);
            }
            return result;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<Pizza> parseDomino()
    {
        try
        {
            final String UrlDomino = "http://dominos.ua/ru/Pizza";
            final Document doc = Jsoup.connect(UrlDomino).get();
            final Elements pizzaInfo = doc.select("div.productItem");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }

    }

}
