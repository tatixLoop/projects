# argument 1 is type id used in arpo database
# argument 2 is type name
echo "java -cp .:/home/jithin/code/webCrawl/cookery_crawl/jsoup-1.11.3.jar:/home/jithin/tatixcode/git/projects/news/cookery/cookery_crawl/mysql-connector-java.jar  createFoodList $1 $2 "
java -cp .:/home/jithin/code/webCrawl/cookery_crawl/jsoup-1.11.3.jar:/home/jithin/tatixcode/git/projects/news/cookery/cookery_crawl/mysql-connector-java.jar  createFoodList $1 $2
