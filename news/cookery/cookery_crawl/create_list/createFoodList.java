
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

class createFoodList
{
    public static void main(String args[])
    {
        String searchData ="";
        List<String> listNames = new ArrayList<>();
        int count = 0;

        if (args.length < 2)
        {
            printf("Usage : run  <Type id of database> <food catagory name>");
            return;
        }

        int type = Integer.parseInt(args[0]);

        for(int i = 1; i < args.length; i++)
        {
            searchData += args[i] + " ";
        }

        CreateFoodListCrawl crawlObj = new CreateFoodListCrawl();

        while (count  < 10)
        {

        printf("Find recipe for "+searchData);
        Document recipePage = crawlObj.getCrawlData(searchData, count);
        if (recipePage != null)
        {
            printf("Page is loaded get the data");
            crawlObj.getRecipeNameList (recipePage, listNames);
        }
        else
        {
            printf("JKS error in accessing catagory page");
            break;
        }
        count++;
        }

        for (int i = 0; i < listNames.size(); i++)
        {
            printf(""+listNames.get(i));
        }
    }

    static void printf(String str)
    {
        System.out.println(str);
    }

}

class CreateFoodListCrawl
{
    private static final String folderPath = "./img/";

    void printf(String str)
    {
        System.out.println(str);
    }

    public Document getCrawlData(String productName, int count) {
        Document doc = null;

        try {
            // get all links
            String url = "https://www.allrecipes.com/search/results/?wt="+productName.replace(" ", "+")+"&page="+count;
            printf("All recipe  url is "+url);
            doc = Jsoup.connect(url).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public void getRecipeNameList(Document doc, List<String> list)
    {
        Elements li = doc.select("span.fixed-recipe-card__title-link, span.added");

printf("JKS getting recipe list "+li.size());
        for (int i = 0; i < li.size() - 3; i++)
        {
        //    printf(li.get(i).text()+" ");  
            list.add(li.get(i).text()+" ");  
        }
        for (Element paragraph : doc.select("article[itemprop=articleBody]"))
            System.out.println(paragraph.text());
    }

}
