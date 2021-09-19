# Tajawal Code Challenge

This code is written as part of mobile automation code challenge for Seera.
https://github.com/tajawal/code-challenge/blob/master/QA_NativeApps.md

***Pre-requisites:**

Maven should be readily installed.
Android SDK tools and configurations should be set.
Appium Desktop/Server should be installed and running.
Real device or simulator should be attached and running.

***Ways to run tests***

You can run the test cases in two ways.
1- Command Line
2- IDE (Intellij IDEA)

1- Command Line:

run the command mvn clean test -DsuiteXmlFile=book_flight_testng.xml

- Please go into project where Maven file (POM) and run the following command. Also there will be ***book_flight_testng.xml*** file.

***Command To Run***
mvn clean test -DsuiteXmlFile=book_flight_testng.xml 

Reports can be found the following location with file name as date adn time. Each time test cases runs new report file created with new name.
