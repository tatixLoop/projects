
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

class cookery_crawl
{

    static void print(String str)
    {
        System.out.println(str);
    }
    public static void main(String args[])
    {
        String searchData = "";
        List<String> listIngr = new ArrayList<>();
        List<String> listDir  = new ArrayList<>();
        for(int i = 0; i < args.length; i++)
        {
            searchData += args[i] + " ";
        }

        CookCrawl crawlObj = new CookCrawl();

        print("Find recipe for "+searchData);
        Document recipePage = crawlObj.getCrawlData(searchData);
        print("Page is loaded get the recipe Name");
        String recipeName = crawlObj.getRecipeName(recipePage);
        if (!recipePage.equals(""))
        {
            int numIngredient = crawlObj.getIngredients(recipePage, listIngr);
            if (numIngredient != 0)
            {
                int numDirections = crawlObj.getDirection(recipePage, listDir);
                if (numDirections != 0)
                {
                    String calory = crawlObj.getCalory(recipePage);
                    crawlObj.getPreviewImage(recipePage);
                    calory = calory.replace(" calories;","");
                    int cal = Integer.parseInt(calory);
                    if (cal != 0)
                    {
                        for(int i = 0; i < listIngr.size(); i++)
                        {
                            print(""+listIngr.get(i));
                        }
                        for(int i = 0; i < listDir.size(); i++)
                        {
                            print("Step " +(i+1) + " : "+listDir.get(i));
                        }
                    }
                    else
                    {
                        print("JKS got zero as calory");
                    }
                }
                else
                {
                    print("JKS No directions for making the recipe");
                }
            }
            else
            {
                print("JKS No ingredients found");
            }
        }
        else
        {
            print("JKS couldnt find the recipe by crawler");
        }
    }
}

class CookCrawl
{
    private static final String folderPath = "./img/";

    void print(String str)
    {
        System.out.println(str);
    }
    public String getRecipeName(Document recipePage)
    {
        String name = "";
        Elements elementsObj = recipePage.select("h1");
        print(elementsObj.text());
        name = elementsObj.text(); 
        return name;
    }

    public int getDirection( Document doc, List<String> list)
    {
        Elements li = doc.select("span.recipe-directions__list--item");

        for (int i = 0; i < li.size() - 1; i++)
        {
            list.add(li.get(i).text());  
        }
        return li.size();
    }
    public int getIngredients( Document doc, List<String> list)
    {
        Elements li = doc.select("span.recipe-ingred_txt, span.added");

        for (int i = 0; i < li.size() - 3; i++)
        {
            list.add(li.get(i).text());  
        }
        return li.size();
    }
    public String getCalory(Document doc)
    {
        String cal = "";
        Elements recipe = doc.select("span[itemprop=calories]");
        cal = recipe.text();
        return cal;
    }

    public Document getCrawlData(String productName) {
        Document doc = null;

        try {
            // get all links
            String url = "https://www.google.com/search?&q=" + productName.replace(" ", "+")
                    + "+from+allrecipes.com";
            print("Google search url is "+url);
            // fetch the google data
            doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");

            for (Element link : links) {
               // print(link.toString());
                // get the first search link for amazon from the list
                if (link.attr("href").startsWith("https://www.allrecipes.com") && (!(link.attr("href").contains("print")))){
                        //||link.attr("href").startsWith("https://www.amazon.in") ) {
                    print("\nlink : " + link.attr("href"));
                    doc = Jsoup.connect(link.attr("href")).get();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    public void getPreviewImage(Document doc)
    {

        Elements recipeImg = doc.select("img[class=rec-photo]");
        print("JKS size = "+recipeImg.size());
        for (Element img : recipeImg)
        {
            String src = img.absUrl("src");
            print("Dump "+src);
            try
            {
                getImages(src);
            } catch (IOException ex) {
                print("EXCEPTION");
            }
        }
    }

    public void saveImages(Document doc)
    {
          Elements img = doc.getElementsByTag("img");
          for (Element el : img) {
              //for each element get the srs url
              String src = el.absUrl("src");

              System.out.println("Image Found!");
              System.out.println("src attribute is : "+src);
              if(!src.equals(""))
              {
                  try
                  {
                      getImages(src);
                  } catch (IOException ex) {
                      print("EXCEPTION");
                  }
              } else
              {
                  print("JKS no src attribute");
              }

          }
    }

    private  void getImages(String src) throws IOException {

      String folder = null;
      //Exctract the name of the image from the src attribute
      int indexname = src.lastIndexOf("/");
      if (indexname == src.length()) {
          src = src.substring(1, indexname);
      }

      indexname = src.lastIndexOf("/");
      String name = src.substring(indexname, src.length());
      System.out.println(name);

      //Open a URL Stream
      URL url = new URL(src);
      InputStream in = url.openStream();
      OutputStream out = new BufferedOutputStream(new FileOutputStream( folderPath+ name));
      for (int b; (b = in.read()) != -1;) {
          out.write(b);
      }
      out.close();
      in.close();
  }

}
