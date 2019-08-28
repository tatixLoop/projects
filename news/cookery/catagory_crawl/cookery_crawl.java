
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

class cookery_crawl
{


    static void print(String str)
    {
        System.out.println(str);
    }

    public static void uploadDatatoDatabase(String name, int type, int cookTime, int serveCount, int calory, int rating, String author, List<String> listIngr , List<String> listDir, String img_path_box, String img_path_main)
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
                            print(""+listIngr.get(i));
                     ingredients += "&ingr"+i+"="+URLEncoder.encode(listIngr.get(i),  java.nio.charset.StandardCharsets.UTF_8.toString());
                 } catch (Exception e)
                 {
                 }
             }
             for(int i = 0; i < listDir.size(); i++)
             {
                 try
                 {
                            print("Step " +(i+1) + " : "+listDir.get(i));
                     step += "&step"+i+"="+ URLEncoder.encode(listDir.get(i),  java.nio.charset.StandardCharsets.UTF_8.toString());
                 } catch (Exception e)
                 {
                 }
             }
        String web_url;

        web_url = upLoadServerUri + extraData + step + ingredients;

        try {
             print(web_url);

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
             print( "HTTP Response is : "
                       + serverResponseMessage + ": " + serverResponseCode);
             BufferedReader in = new BufferedReader(
                                         new InputStreamReader(conn.getInputStream()));
             String inputLine;
             StringBuffer response = new StringBuffer();
             
             while ((inputLine = in.readLine()) != null) {
                 response.append(inputLine);
             }
             in.close();
             print(response.toString());
        } catch (Exception e) {
            print("JKS EXCEPTION");
            e.printStackTrace();
        }
 
    }

    public static void main(String args[])
    {
        String searchData = "";
        String sourceFileNameForUpload;
        int type = 1;
        int serveCount = 2;
        int cookTime = 2000;
        int rating = 8;
        String author = "Jithin K S";
        List<String> listIngr = new ArrayList<>();
        List<String> listDir  = new ArrayList<>();

        type = Integer.parseInt(args[0]);

        for(int i = 1; i < args.length; i++)
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
                    sourceFileNameForUpload = crawlObj.getPreviewImage(recipePage);
                    calory = calory.replace(" calories;","");
                    int cal = Integer.parseInt(calory);
                    if (cal != 0)
                    {
                        int upload = 1;
                        if(upload == 1)
                        {
                        uploadDatatoDatabase(recipeName,
                                             type,
                                             cookTime,
                                             serveCount,
                                             cal,
                                             rating,
                                             author,
                                             listIngr ,
                                             listDir,
                                             sourceFileNameForUpload,
                                             sourceFileNameForUpload);
                        }
                        else
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

/*
                        print("JKS got "+listIngr.size() + " ingredients and "+ listDir.size() + " steps ");
                        String upLoadServerUri = "http://localhost/app/news/cookery/web/tmp.php";
                        int type = 1;
                        int rating = 8;
                        String author = "Jithin K S";
                        String extraData = "?type="+type+"&timeToCook=2000&calory="+cal+"&recipe_name="+recipeName.replace(" ", "%20")+"&serve_count=2&"
                                            +"author="+author.replace(" ", "%20")+"&rating="+rating;
                        print("URL = "+upLoadServerUri + extraData);
                        DataOutputStream dos = null;
                        HttpURLConnection conn = null;
                        String lineEnd = "\r\n";
                        String twoHyphens = "--";
                        String boundary = "*****";
                        int bytesRead, bytesAvailable, bufferSize;
                        byte[] buffer;
                        int maxBufferSize = 1 * 1024 * 1024;
                        int serverResponseCode = 0;

                        try {
                         FileInputStream fileInputStream = new FileInputStream(sourceFileNameForUpload);
                         URL url = new URL(upLoadServerUri + extraData);
                         
                         // Open a HTTP  connection to  the URL
                         conn = (HttpURLConnection) url.openConnection(); 
                         conn.setDoInput(true); // Allow Inputs
                         conn.setDoOutput(true); // Allow Outputs
                         conn.setUseCaches(false); // Don't use a Cached Copy
                         conn.setRequestMethod("POST");
                         conn.setRequestProperty("Connection", "Keep-Alive");
                         conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                         conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                         conn.setRequestProperty("recipe_name", recipeName );
                         conn.setRequestProperty("serve_count", ""+2 );
                         conn.setRequestProperty("calory",""+ cal );
                         conn.setRequestProperty("timeToCook", ""+2000 );
                         conn.setRequestProperty("rating", ""+9 );
                         conn.setRequestProperty("author", "Jithin" );
                         conn.setRequestProperty("type", ""+1 );
                         for(int i = 0; i < listIngr.size(); i++)
                         {
                             conn.setRequestProperty("ingr"+i, listIngr.get(i) );
                         }
                         for(int i = 0; i < listDir.size(); i++)
                         {
                             conn.setRequestProperty("step"+i, listDir.get(i) );
                         }
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

                         FileInputStream fileInputStream2 = new FileInputStream(sourceFileNameForUpload);
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
                         print( "HTTP Response is : "
                                   + serverResponseMessage + ": " + serverResponseCode);
                         BufferedReader in = new BufferedReader(
                                                     new InputStreamReader(conn.getInputStream()));
                         String inputLine;
                         StringBuffer response = new StringBuffer();

                         while ((inputLine = in.readLine()) != null) {
                             response.append(inputLine);
                         }
                         in.close();

		//print result
		System.out.println(response.toString());
                       } catch (Exception e) {
                            print("JKS EXCEPTION");
                            e.printStackTrace();
                       }
*/
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
    public String getPreviewImage(Document doc)
    {

        Elements recipeImg = doc.select("img[class=rec-photo]");
        for (Element img : recipeImg)
        {
            String src = img.absUrl("src");
            print("Dump "+src);
            try
            {
                return getImages(src);
            } catch (IOException ex) {
                print("EXCEPTION");
            }
        }
        return "";
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
      OutputStream out = new BufferedOutputStream(new FileOutputStream( folderPath+ name));

      for (int b; (b = in.read()) != -1;) {
          out.write(b);
      }
      out.close();
      in.close();
      return folderPath + name;
  }

}
