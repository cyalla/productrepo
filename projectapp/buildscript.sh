#!/bin/sh
export JAVA_HOME=/scratch/cyalla/jdk1.8.0_361
export GRADLE_HOME=/scratch/cyalla/gradle-8.7
export MAVEN_HOME=/scratch/cyalla/apache-maven-3.9.6
export PATH=$GRADLE_HOME/bin:$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH
gradle clean build
