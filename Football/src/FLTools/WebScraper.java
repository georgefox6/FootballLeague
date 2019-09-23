package FLTools;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {

    public static Document connect(){
        Document doc = null;
        String url = "https://www.transfermarkt.co.uk/premier-league/startseite/wettbewerb/GB1";
        String team = "Chelsea";
        String url2 = "https://www.premierleague.com/clubs/1/" + team + "/squad";
        try {
            doc = Jsoup.connect(url2)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .timeout(60 * 1000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static void main(String[] args) {

        String plPlayerClass = "name";
        Document doc = connect();

        System.out.println(doc.title());
        Elements teams = doc.getElementsByClass(plPlayerClass);


        for(Element team : teams){
            System.out.println(team.text());
        }

    }
}
