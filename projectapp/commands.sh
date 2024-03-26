gradle clean build

cp  build/libs/productapp.war /Users/cyalla/Downloads/apache-tomcat-9.0.84/webapps

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-18.0.1.1.jdk/Contents/Home 
export GRADLE_HOME=/Users/cyalla/Documents/Orahack2024/stage/gradle-8.7   
export PATH=$GRADLE_HOME/bin:$PATH
