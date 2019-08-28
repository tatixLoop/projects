
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
import java.util.StringTokenizer;
import java.util.Scanner;
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
        int subType =0;
        String dish = "";

        if (args.length < 2)
        {
            printf("Usage : run  <Type id of database> <subType id > <food catagory name>");
            return;
        }
        /* get first argument, type */
        type = Integer.parseInt(args[0]);
        /* get second argument, subtype */
        subType = Integer.parseInt(args[1]);

        /* get search key word */
        for(int i = 2; i < args.length; i++)
        {
            searchData += args[i] + " ";
        }

printf("Input arguments type ="+type +" subType = "+subType + " key = "+searchData);

        /* create an object for crawling */
        CreateFoodListCrawl crawlObj = new CreateFoodListCrawl();

        count = 1; /* count for pages in search result */
        int addedCount = 0;
        /* continue to fetch data until 100 recipe is added to database */
        while ( addedCount < 50)
        {

             printf("Find recipe for "+searchData);
             /* get the web page which contains the listing of recipes */
             Document recipePage = crawlObj.getCrawlData(searchData, count);
             if (recipePage != null)
             {
                 printf("Page is loaded get the data");
//                 crawlObj.getRecipeNameList (recipePage, listNames);
                 /* Get the data from list of searched data */
                 addedCount += crawlObj.getLinkList (recipePage, type, listNames, subType);
             }
             else
             {
                 printf("JKS error in accessing catagory page");
                 break;
             }
             printf("JKS added "+ addedCount + " recipes");
             count++;
        }
printf("100 items added to database \n");
/*

        for (int i = 0; i < listNames.size(); i++)
        {
//            printf(""+listNames.get(i));
//            String command = "./getRecipieInfo.sh "+type+ " '"+listNames.get(i)+"'";
            String command = "java -cp .:/home/jithin/code/webCrawl/cookery_crawl/jsoup-1.11.3.jar cookery_crawl "+type+ " '"+listNames.get(i)+"'";
/*
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
*/
//        }

    }

    static void printf(String str)
    {
        System.out.println(str);
    }

}

class CreateFoodListCrawl
{

    String subTypeStringArray[] = {
    "none",
    "Cocktails",
    "Juice",
    "Lemonade",
    "Mocktails",
    "Coffee",
    "Smoothies",
    "Tea Drinks",
    "Shakes",
    "Hot chocolate",
    "Wines",
    };
    static int subTypeFromDB;
    static int idFromDB;
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
    public int getLinkList(Document doc, int type, List<String> list, int subType)
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


        /* go through each link in the page */
        for (Element link : links) {
            if (!(link.attr("href").contains("print")) && !(link.attr("href").contains("video")) && (link.attr("href").contains("/recipe/")))
            {
                /* this is a link to recipe */
                if( set == 0) /* why this condition check ? */
                {
                    printf("\nlink : " + link.attr("href"));
                    set =1;
                    try
                    {
                    /* load recipe page */
                    Document recipePage = Jsoup.connect(link.attr("href")).get();
                    /* 1. get recipe name */
                    String recipeName = getRecipeName(recipePage);
                    if (!recipePage.equals(""))
                    {
                        printf("Recipe Name = "+recipeName);
                        /* 2. get author name */
                        String recipieAuthorName = getAuthor(recipePage);
                        if (! recipieAuthorName.equals(""))
                        {
                            printf("Author : "+ recipieAuthorName);
                            /* 2. get calory */
                            String calory = getCalory(recipePage);
                            calory = calory.replace(" calories;","");
                            int cal = Integer.parseInt(calory);
                            if (cal != 0)
                            {
                                List<String> listIngr = new ArrayList<>();
                                printf("JKS calory = "+cal);
                                /* 3. get list of ingredients */
                                int numIngredient = getIngredients(recipePage, listIngr);
                                if (numIngredient != 0)
                                {
                                    List<String> listDir  = new ArrayList<>();
                                    /* 4. get list of directions */
                                    int numDirections = getDirection(recipePage, listDir);
                                    if (numDirections != 0)
                                    {
                                         /* 5. Get cook time  */
                                         String cukTime = getCookTime(recipePage);
//                                         cookTime = covertCookTimeToSec(cukTime);
                                         if ( !cukTime.equals(""))
                                         {
                                             /* 6. Get servings count */
                                             serveCount = getServings(recipePage);
                                             printf("Serve Count = "+serveCount);
                                             if (serveCount != 0)
                                             {
                                                 /* 7. get image for recipe */
                                                 String sourceFileNameForUpload = getPreviewImage(recipePage);
                                                 if ( !sourceFileNameForUpload.equals(""))
                                                 {
                                                     int upload = 1;
                                                     if(upload == 1)
                                                     {
                                                     /* uplod the date to databse */
                                                     added += uploadDatatoDatabaseFromJava(recipeName,
                                                                          type, subType,
                                                                          cookTime,
                                                                          serveCount,
                                                                          cal,
                                                                          rating,
                                                                          recipieAuthorName,
                                                                          listIngr ,
                                                                          listDir,
                                                                          sourceFileNameForUpload,
                                                                          sourceFileNameForUpload,
                                                                          cukTime);
                                                     }
                                                     else 
                                                     {
                                                         printf("JKS not uploading data\n");
                                                     }
                                                 }
                                                 else
                                                 {
                                                     printf("JKS could not grab image");
                                                 }
                                             }
                                             else
                                             {
                                                 printf("JKS problem with serve count");
                                             }
                                         }
                                         else
                                         {
                                             printf("JKS invalid cookTime");
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
                ex.printStackTrace();
                return "";
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

    public int covertCookTimeToSec (String time)
    {
        int sec = 0;
        StringTokenizer st = new StringTokenizer(time, "h");

        if ( time.contains("h"))
        {
            while (st.hasMoreElements()) {
                String data = st.nextElement().toString();
                sec = Integer.parseInt(data) * 60 * 60;
                printf("Length = "+data.length());
                break;
            }
        }

        StringTokenizer st2 = new StringTokenizer(time, "m");

        while (st2.hasMoreElements()) {
            System.out.println(st2.nextElement());
        }
        return 2000;
    }
    public String getCookTime (Document doc)
    {
        String cookTime = "";
        Elements recipe = doc.select("span.ready-in-time");
        cookTime = recipe.text();
        printf("cookTime = "+cookTime);
        return cookTime;

    }
    public int getServings (Document doc)
    {
        Elements recipe = doc.select("meta[itemprop=recipeYield]");

        return Integer.parseInt(recipe.attr("content"));
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
                                              int type, int subType,
                                              int cookTime,
                                              int serveCount,
                                              int calory,
                                              int rating,
                                              String author,
                                              List<String> listIngr ,
                                              List<String> listDir,
                                              String img_path_box,
                                              String img_path_main,
                                              String cookTimeString)
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
             /* Connect to databse */
             Class.forName("com.mysql.jdbc.Driver") ;
             Connection conn = DriverManager.getConnection("jdbc:mysql://tatixtech.com:3306/sg_news", "jks", "Jithin123") ;
             Statement stmt = conn.createStatement() ;
 
             /* check if recipie is already present */
             if ( recipiePresentInDB(stmt, name) )
             {
                printf("Recipe already present \n");
                /* get subType from db and id from db of existing recipe and store it in subTypeFromDB and idFromDB */
                getSubTypenId(stmt, name);
                if (subType != subTypeFromDB)
                {
                    printf("JKS subType of recipe "+name +" ( "+ getSubTypeFromId(subTypeFromDB)+" ) is differnt than requested("+getSubTypeFromId(subType)+")");
                    printf("JKS do you want to update type ?Press 1/0");
/*
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine();
                    int inp = Integer.parseInt(input);
*/
                    int inp = 1;
                    if ( inp == 1)
                    {
                        printf("*******JKS update subType***********");
                        query = "UPDATE tbl_dishes set subtype="+subType+" Where id="+idFromDB;
                 stmt.executeUpdate(query) ;
                 printf(query);
                    }
                    else
                    {
                        printf("JKS this recipe is already present in the database");
                    }
                } 
                return 0 ;
             }
             else
             {
                 printf("********JKS add new recipe to database to database************");

                 query = "SELECT max(id) from tbl_dishes";
                 ResultSet rs = stmt.executeQuery(query) ;
                 rs.next();
                 int id = rs.getInt("max(id)");
                 int nextId = id+1;
              
                 dir_path = "recipe_"+nextId;
                 name = name.replace("'", "''");
                 author = author.replace("'", "''");
                 query = "INSERT INTO tbl_dishes (type, dishname, img_path, calory, cooktimeinsec, serves, author, rating, numRating, cuktime, subtype) VALUES ("+
                 				type+", '"+name+"', '"+ dir_path +"', "+calory+", "+cookTime+", "+serveCount+", '"+author+"', "+rating+", 1, '"+cookTimeString+"', "+subType+")";
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
                 name = name.replace("'", "''");
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

    public void getSubTypenId(Statement stmt, String name )
    {
                 name = name.replace("'", "''");
        String query = "SELECT id, subtype from tbl_dishes where dishname='"+name+"'";
        try
        {
            ResultSet rs = stmt.executeQuery(query) ;
            rs.next();
            subTypeFromDB = rs.getInt("subtype");
            idFromDB = rs.getInt("id");
printf("id is "+idFromDB + " subTypeFrom DB is "+subTypeFromDB);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getSubTypeFromId(int id)
    {
        return subTypeStringArray[id];
    }
    
}
