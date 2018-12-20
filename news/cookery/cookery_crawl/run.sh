# argument 1 is type id used in arpo database
# value 1 for typeid is Breakfast
# value 2 for typeid is Lunch
# value 3 for typeid is Sncaks
# value 4 for typeid is cookies
# value 5 for typeid is juices
# value 6 for typeid is todays special
# value 7 for typeid is omlet
# value 8 for typeid is egg
# value 9 for typeid is chicken
# value 10 for typeid is coffee
# value 11 for typeid is deserts
# value 12 for typeid is healhty
# value 13 for typeid is icecream
# value 14 for typeid is pizza
# value 15 for typeid is Sea food
# value 16 for typeid is Indian
# value 17 for typeid is Chinese
# value 18 for typeid is American 
# value 19 for typeid is Salads

# argument 2 is type name
echo "java -cp .:/home/jithin/code/webCrawl/cookery_crawl/jsoup-1.11.3.jar:/home/jithin/tatixcode/git/projects/news/cookery/cookery_crawl/mysql-connector-java.jar  createFoodList $1 $2 "
java -cp .:/home/jithin/code/webCrawl/cookery_crawl/jsoup-1.11.3.jar:/home/jithin/tatixcode/git/projects/news/cookery/cookery_crawl/mysql-connector-java.jar  createFoodList $1 $2
