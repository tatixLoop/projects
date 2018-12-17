
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
        int uploadnow = 0;
        int type =0;
        String dish = "";

        if (args.length < 2)
        {
            printf("Usage : run  <Type id of database> <food catagory name>");
            return;
        }

        type = Integer.parseInt(args[0]);

        for(int i = 1; i < args.length; i++)
        {
            searchData += args[i] + " ";
        }

        CreateFoodListCrawl crawlObj = new CreateFoodListCrawl();

        count = 1;
        int addedCount = 0;
        while ( addedCount < 100)
        {

             printf("Find recipe for "+searchData);
             Document recipePage = crawlObj.getCrawlData(searchData, count);
             if (recipePage != null)
             {
                 printf("Page is loaded get the data");
//                 crawlObj.getRecipeNameList (recipePage, listNames);
                 addedCount += crawlObj.getLinkList (recipePage, type, listNames);
             }
             else
             {
                 printf("JKS error in accessing catagory page");
                 break;
             }
             printf("JKS added "+ addedCount + " recipes");
             count++;
        }

        for (int i = 0; i < listNames.size(); i++)
        {
//            printf(""+listNames.get(i));
//            String command = "./getRecipieInfo.sh "+type+ " '"+listNames.get(i)+"'";
            String command = "java -cp .:/home/jithin/code/webCrawl/cookery_crawl/jsoup-1.11.3.jar cookery_crawl "+type+ " '"+listNames.get(i)+"'";
            printf(""+command);
            if (uploadnow == 1)
            {
            String s;
            Process p;
            try {
                p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null)
                    System.out.println("line: " + s);
                p.waitFor();
                System.out.println ("exit: " + p.exitValue());
                p.destroy();
            } catch (Exception e) {}
            }
            else
            {
printf("JKS not executing");
            }
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
    private static InputStream incomingImg;

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
    public int getLinkList(Document doc, int type, List<String> list)
    {
        int added = 0;
        Elements li = doc.select("div.fixed-recipe-card__info");
        printf("JKS got "+li.size() + " Links");
        Elements links = li.select("a[href]");
        int set = 0;

        List<String> listIngr = new ArrayList<>();
        List<String> listDir  = new ArrayList<>();

        /* hard coding for now */
        int cookTime = 2000;
        int serveCount = 2;
        int rating = 10;
        for (Element link : links) {
            if (!(link.attr("href").contains("print")) && !(link.attr("href").contains("video")) && (link.attr("href").contains("/recipe/")))
            {
                if( set == 0)
                {
                    printf("\nlink : " + link.attr("href"));
                    set =1;
                    try
                    {
                    Document recipePage = Jsoup.connect(link.attr("href")).get();
                    String recipeName = getRecipeName(recipePage);
                    if (!recipePage.equals(""))
                    {
                        printf("Recipe Name = "+recipeName);
                        String recipieAuthorName = getAuthor(recipePage);
                        if (! recipieAuthorName.equals(""))
                        {
                            printf("Author : "+ recipieAuthorName);
                            String calory = getCalory(recipePage);
                            calory = calory.replace(" calories;","");
                            int cal = Integer.parseInt(calory);
                            if (cal != 0)
                            {
                                printf("JKS calory = "+cal);
                                int numIngredient = getIngredients(recipePage, listIngr);
                                if (numIngredient != 0)
                                {
                                    int numDirections = getDirection(recipePage, listDir);
                                    if (numDirections != 0)
                                    {
                                         added++;
                                         int upload = 1;
                                         if(upload == 1)
                                         {
                                         String sourceFileNameForUpload = getPreviewImage(recipePage);
                                         uploadDatatoDatabaseFromJava(recipeName,
                                                              type,
                                                              cookTime,
                                                              serveCount,
                                                              cal,
                                                              rating,
                                                              recipieAuthorName,
                                                              listIngr ,
                                                              listDir,
                                                              sourceFileNameForUpload,
                                                              sourceFileNameForUpload);
                                         System.exit(0);
                                         }

                                    }
                                    else
                                    {
                                        printf("ERROR : Getting directions");
                                    }
                                }
                                else
                                {
                                    printf("ERROR : Getting ingredients");
                                }
                            }
                            else
                            {
                                printf("ERROR : error in fetching calory");
                            }
                            getCookTime(recipePage);
                            
                        }
                        else
                        {
                            printf("ERROR : Author is invalid:");
                        }
                        
                    }
                    else
                    {
                        printf("ERROR : Recipe name is invalid:");
                    }
                    }
                    catch (Exception e)
                    {

                    }
                }
                else
                {
                    set = 0;
                }
            }
        }
        return added;
    }

    public String getPreviewImage(Document doc)
    {

        Elements recipeImg = doc.select("img[class=rec-photo]");
        for (Element img : recipeImg)
        {
            String src = img.absUrl("src");
            printf("Dump "+src);
            try
            {
                return getImages(src);
            } catch (IOException ex) {
                printf("EXCEPTION");
            }
        }
        return "";
    }
    private  String getImages(String src) throws IOException {

      String folder = null;
      //Exctract the name of the image from the src attribute
      int indexname = src.lastIndexOf("/");
      if (indexname == src.length()) {
          src = src.substring(1, indexname);
      }

      indexname = src.lastIndexOf("/");
      String name = src.substring(indexname, src.length());

      //Open a URL Stream
      URL url = new URL(src);
      InputStream in = url.openStream();
      OutputStream out = new BufferedOutputStream(new FileOutputStream( folderPath + name));
      incomingImg = in;

      for (int b; (b = in.read()) != -1;) {
          out.write(b);
      }
      out.close();
      in.close();
      return folderPath + name;
  }


    public int getDirection( Document doc, List<String> list)
    {
        Elements li = doc.select("span.recipe-directions__list--item");

        for (int i = 0; i < li.size() - 1; i++)
        {
            list.add(li.get(i).text() +" ");
        }
        return li.size();
    }
    public int getIngredients( Document doc, List<String> list)
    {
        Elements li = doc.select("span.recipe-ingred_txt, span.added");

        for (int i = 0; i < li.size() - 3; i++)
        {
            list.add(li.get(i).text()+" ");
        }
        return li.size();
    }

    public String getCookTime (Document doc)
    {
        String cookTime = "";
        Elements recipe = doc.select("span[itemprop=ready-in-time]");
        cookTime = recipe.text();
        printf("cookTime = "+cookTime);
        return cookTime;

    }

    public String getCalory(Document doc)
    {
        String cal = "";
        Elements recipe = doc.select("span[itemprop=calories]");
        cal = recipe.text();
        return cal;
    }

    public String getAuthor(Document recipePage)
    {
        String name ="";
        Elements elementsObj = recipePage.select("span[itemprop=author]");
        printf(elementsObj.text());
        name = elementsObj.text();
        return name;
    }
    public String getRecipeName(Document recipePage)
    {
        String name = "";
        Elements elementsObj = recipePage.select("h1");
        printf(elementsObj.text());
        name = elementsObj.text();
        return name;
    }

    public void getRecipeNameList(Document doc, List<String> list)
    {
        Elements li = doc.select("span.fixed-recipe-card__title-link, span.added");

        for (int i = 0; i < li.size() - 3; i++)
        {
        //    printf(li.get(i).text()+" ");  
            list.add(li.get(i).text()+" ");  
        }
        for (Element paragraph : doc.select("article[itemprop=articleBody]"))
            System.out.println(paragraph.text());
    }
    public void uploadDatatoDatabaseFromJava(String name, int type, int cookTime, int serveCount, int calory, int rating, String author, List<String> listIngr , List<String> listDir, String img_path_box, String img_path_main)
    {
printf("Upload from java");
    }

    public void uploadDatatoDatabase(String name, int type, int cookTime, int serveCount, int calory, int rating, String author, List<String> listIngr , List<String> listDir, String img_path_box, String img_path_main)
    {
//        String upLoadServerUri = "http://localhost/app/news/cookery/web/tmp.php";
        String upLoadServerUri = "http://tatixtech.com/cookery/upload/dataupload.php";
        String extraData = "";
                 try
                 {
            extraData = "?type="+ type+
                           "&timeToCook="+cookTime+
                           "&calory="+  calory+
                           "&recipe_name="+ URLEncoder.encode( name,  java.nio.charset.StandardCharsets.UTF_8.toString())+
                           "&serve_count="+ serveCount+
                           "&author="+URLEncoder.encode( author,  java.nio.charset.StandardCharsets.UTF_8.toString())+
                           "&rating="+ rating;
                 } catch (Exception e)
                 {
                 }


        DataOutputStream  dos  = null;
        HttpURLConnection conn = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int serverResponseCode = 0;
        String ingredients ="";
        String step = "";

             for(int i = 0; i < listIngr.size(); i++)
             {
                 try
                 {
                            printf(""+listIngr.get(i));
                     ingredients += "&ingr"+i+"="+URLEncoder.encode(listIngr.get(i),  java.nio.charset.StandardCharsets.UTF_8.toString());
                 } catch (Exception e)
                 {
                 }
             }
             for(int i = 0; i < listDir.size(); i++)
             {
                 try
                 {
                            printf("Step " +(i+1) + " : "+listDir.get(i));
                     step += "&step"+i+"="+ URLEncoder.encode(listDir.get(i),  java.nio.charset.StandardCharsets.UTF_8.toString());
                 } catch (Exception e)
                 {
                 }
             }
        String web_url;

        web_url = upLoadServerUri + extraData + step + ingredients;

        try {
             printf(web_url);

             FileInputStream fileInputStream = new FileInputStream(img_path_box);
             URL url = new URL(web_url);
             
             // Open a HTTP  connection to  the URL
             conn = (HttpURLConnection) url.openConnection(); 
             conn.setDoInput(true); // Allow Inputs
             conn.setDoOutput(true); // Allow Outputs
             conn.setUseCaches(false); // Don't use a Cached Copy
             conn.setRequestMethod("POST");
             conn.setRequestProperty("Connection", "Keep-Alive");
             conn.setRequestProperty("ENCTYPE", "multipart/form-data");
             conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
/*
             conn.setRequestProperty("recipe_name", recipeName );
             conn.setRequestProperty("serve_count", ""+2 );
             conn.setRequestProperty("calory",""+ cal );
             conn.setRequestProperty("timeToCook", ""+2000 );
             conn.setRequestProperty("rating", ""+9 );
             conn.setRequestProperty("author", "Jithin" );
             conn.setRequestProperty("type", ""+1 );
*/

             conn.setRequestProperty("image", "preview.jpg");
             conn.setRequestProperty("image_food", "preview.jpg");
             
             dos = new DataOutputStream(conn.getOutputStream());
             
             dos.writeBytes(twoHyphens + boundary + lineEnd); 
             dos.writeBytes("Content-Disposition: form-data; name='image';filename="
                           + "preview.jpg" + "" + lineEnd);
             
             dos.writeBytes(lineEnd);
             
             // create a buffer of  maximum size
             bytesAvailable = fileInputStream.available(); 
             bufferSize = Math.min(bytesAvailable, maxBufferSize);
             buffer = new byte[bufferSize];
             // read file and write it into form...
             bytesRead = fileInputStream.read(buffer, 0, bufferSize);
             
             while (bytesRead > 0) {
                 dos.write(buffer, 0, bufferSize);
                 bytesAvailable = fileInputStream.available();
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);
             }
             // send multipart form data necesssary after file data...
             dos.writeBytes(lineEnd);
             dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
             
             FileInputStream fileInputStream2 = new FileInputStream(img_path_main);
             dos.writeBytes(twoHyphens + boundary + lineEnd); 
             dos.writeBytes("Content-Disposition: form-data; name='image_food';filename="
                           + "preview2.jpg" + "" + lineEnd);
             
             dos.writeBytes(lineEnd);
             
             // create a buffer of  maximum size
             bytesAvailable = fileInputStream2.available(); 
             bufferSize = Math.min(bytesAvailable, maxBufferSize);
             buffer = new byte[bufferSize];
             // read file and write it into form...
             bytesRead = fileInputStream2.read(buffer, 0, bufferSize);
             
             while (bytesRead > 0) {
                 dos.write(buffer, 0, bufferSize);
                 bytesAvailable = fileInputStream2.available();
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 bytesRead = fileInputStream2.read(buffer, 0, bufferSize);
             }
             // send multipart form data necesssary after file data...
             dos.writeBytes(lineEnd);
             dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
             
             // Responses from the server (code and message)
             serverResponseCode = conn.getResponseCode();
             String serverResponseMessage = conn.getResponseMessage();
             printf( "HTTP Response is : "
                       + serverResponseMessage + ": " + serverResponseCode);
             BufferedReader in = new BufferedReader(
                                         new InputStreamReader(conn.getInputStream()));
             String inputLine;
             StringBuffer response = new StringBuffer();
             
             while ((inputLine = in.readLine()) != null) {
                 response.append(inputLine);
             }
             in.close();
             printf(response.toString());
        } catch (Exception e) {
            printf("JKS EXCEPTION");
            e.printStackTrace();
        }
 
    }


}