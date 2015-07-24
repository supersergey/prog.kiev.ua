/* Написать парсер для Yahoo Finance в режиме XML
(format=xml). */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 24.07.2015.
 */
public class Main {
    public static void main(String[] args) {

        final String urlStr = "http://query.yahooapis.com/v1/public/yql?format=XML&q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDEUR%22,%20%22USDUAH%22)&env=store://datatables.org/alltableswithkeys";
        try
        {
            Query query = new Query();
            JAXBContext jaxbContext = JAXBContext.newInstance(query.getClass());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            try
            {
                query = (Query) unmarshaller.unmarshal(new URL(urlStr));
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            if (null!=query.getResults())
            {
                for (Rate r : query.getResults().getRate())
                    System.out.println(r.getName() + " : " + r.getRate());
            }
            else
                System.out.println("Empty result.");
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }
}
