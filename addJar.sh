mvn install:install-file \
   -Dfile=lib/utils.jar \
   -DgroupId=com.geta \
   -DartifactId=utils \
   -Dversion=1.0 \
   -Dpackaging=jar

mvn install:install-file \
   -Dfile=lib/MyDAO.jar \
   -DgroupId=com.geta \
   -DartifactId=MyDAO \
   -Dversion=1.0 \
   -Dpackaging=jar