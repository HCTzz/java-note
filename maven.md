1、将本地jar包安装到本地maven仓库
mvn install:install-file -Dfile=lucene-queryparser-4.6.1.jar -DgroupId=org.apache.lucene -DartifactId=lucene-queryparser -Dversion=4.6.1 -Dpackaging=jar


//第一步,从Maven本地仓库删除jar
//清除某个jar
mvn dependency:purge-local-repository -DmanualInclude="groupId:artifactId"
 
//清除多个属于不同groupId的jar
mvn dependency:purge-local-repository -DmanualInclude="groupId1:artifactId1,groupId2:artifactId2,..."
 
//第二步,阻止Maven对已删除的jar进行reResolve
mvn dependency:purge-local-repository -DreResolve=false