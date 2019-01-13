
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
import java.sql.Connection ;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

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
                                List<String> listIngr = new ArrayList<>();
                                printf("JKS calory = "+cal);
                                int numIngredient = getIngredients(recipePage, listIngr);
                                if (numIngredient != 0)
                                {
                                    List<String> listDir  = new ArrayList<>();
                                    int numDirections = getDirection(recipePage, listDir);
                                    if (numDirections != 0)
                                    {
                                         int upload = 1;
                                         if(upload == 1)
                                         {
                                         String sourceFileNameForUpload = getPreviewImage(recipePage);
                                         added += uploadDatatoDatabaseFromJava(recipeName,
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


    public int    uploadDatatoDatabaseFromJava(String name,
                                              int type,
                                              int cookTime,
                                              int serveCount,
                                              int calory,
                                              int rating,
                                              String author,
                                              List<String> listIngr ,
                                              List<String> listDir,
                                              String img_path_box,
                                              String img_path_main)
    {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int serverResponseCode = 0;
        DataOutputStream  dos  = null;
        HttpURLConnection httpConn = null;

        printf("Upload data from java: Make sure you have added your ip address to remote mysql list");
        String dir_path = "";
        String query = "";
        try {
             Class.forName("com.mysql.jdbc.Driver") ;
             Connection conn = DriverManager.getConnection("jdbc:mysql://tatixtech.com:3306/sg_news", "jks", "Jithin123") ;
             Statement stmt = conn.createStatement() ;
 
             /* check if recipie is already present */
             if ( recipiePresentInDB(stmt, name) )
             {
                printf("Recipe already present \n");
                return 0 ;
             }
             else
             {
                 printf("JKS add it to database");
                 query = "SELECT max(id) from tbl_dishes";
                 ResultSet rs = stmt.executeQuery(query) ;
                 rs.next();
                 int id = rs.getInt("max(id)");
                 int nextId = id+1;
              
                 dir_path = "recipe_"+nextId;
                 name = name.replace("'", "''");
                 author = author.replace("'", "''");
                 query = "INSERT INTO tbl_dishes (type, dishname, img_path, calory, cooktimeinsec, serves, author, rating, numRating) VALUES ("+
                 				type+", '"+name+"', '"+ dir_path +"', "+calory+", "+cookTime+", "+serveCount+", '"+author+"', "+rating+", 1)";
                 stmt.executeUpdate(query) ;
                 printf(query);
                 for (int i = 0; i < listIngr.size(); i++)
                 {
                     String ingr = listIngr.get(i);
                     ingr = ingr.replace("'", "''");
                     query = "INSERT INTO tbl_ingredients (id, ingredient) VALUES ("+nextId+", '"+ingr+"')";
                     stmt.executeUpdate(query) ;
                     printf(query);
                 }
                 for (int i = 0; i < listDir.size(); i++)
                 {
                     String step = listDir.get(i);
                     step = step.replace("'", "''");
                     query = "INSERT INTO tbl_steps (id, stepno, step) VALUES ("+nextId + ","+ (i + 1) +",'"+ step +"')";
                     stmt.executeUpdate(query) ;
                     printf(query);
                 }
                 String upLoadServerUri = "http://tatixtech.com/cookery/upload/imgupload.php";
              
                 //Uploading image to web
                 String web_url;
              
                 web_url = upLoadServerUri ;
                 
                 try {
                      printf(web_url);
              
                      FileInputStream fileInputStream = new FileInputStream(img_path_box);
                      URL url = new URL(web_url);
                      
                      // Open a HTTP  connection to  the URL
                      httpConn = (HttpURLConnection) url.openConnection(); 
                      httpConn.setDoInput(true); // Allow Inputs
                      httpConn.setDoOutput(true); // Allow Outputs
                      httpConn.setUseCaches(false); // Don't use a Cached Copy
                      httpConn.setRequestMethod("POST");
                      httpConn.setRequestProperty("Connection", "Keep-Alive");
                      httpConn.setRequestProperty("ENCTYPE", "multipart/form-data");
                      httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
              
                      httpConn.setRequestProperty("image", "preview.jpg");
                      httpConn.setRequestProperty("image_food", "preview.jpg");
                      
                      dos = new DataOutputStream(httpConn.getOutputStream());
                      
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
                      serverResponseCode = httpConn.getResponseCode();
                      String serverResponseMessage = httpConn.getResponseMessage();
                      printf( "HTTP Response is : "
                                + serverResponseMessage + ": " + serverResponseCode);
                      BufferedReader in = new BufferedReader(
                                                  new InputStreamReader(httpConn.getInputStream()));
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
                     return 1;
                 }
                 return 1;
            }

       }
       catch (Exception e)
       {
           printf("Exception ");
           e.printStackTrace();
           return 1;
       }
    }

    public boolean recipiePresentInDB(Statement stmt, String name)
    {
        String query = "SELECT count(*) from tbl_dishes where dishname='"+name+"'";
        try
            {
            ResultSet rs = stmt.executeQuery(query) ;
            rs.next();
            int count = rs.getInt("count(*)");;
            if ( count != 0 )
            {
                /* already present */
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return true;// return true on error condition 
        }
    }
    
}
