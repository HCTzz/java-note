1��������jar����װ������maven�ֿ�
mvn install:install-file -Dfile=lucene-queryparser-4.6.1.jar -DgroupId=org.apache.lucene -DartifactId=lucene-queryparser -Dversion=4.6.1 -Dpackaging=jar


//��һ��,��Maven���زֿ�ɾ��jar
//���ĳ��jar
mvn dependency:purge-local-repository -DmanualInclude="groupId:artifactId"
 
//���������ڲ�ͬgroupId��jar
mvn dependency:purge-local-repository -DmanualInclude="groupId1:artifactId1,groupId2:artifactId2,..."
 
//�ڶ���,��ֹMaven����ɾ����jar����reResolve
mvn dependency:purge-local-repository -DreResolve=false