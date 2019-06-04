1、将本地jar包安装到本地maven仓库
mvn install:install-file -Dfile=lucene-queryparser-4.6.1.jar -DgroupId=org.apache.lucene -DartifactId=lucene-queryparser -Dversion=4.6.1 -Dpackaging=jar