#!/bin/bash

echo 'test00'
java -jar target/apptbook.jar
java -jar target/apptbook.jar -host
java -jar target/apptbook.jar -host localhost
java -jar target/apptbook.jar -host localhost -port 
java -jar target/apptbook.jar -host localhost -port 8080
java -jar target/apptbook.jar -host localhost -port 9090

java -jar target/apptbook.jar -print "Dave" "PRINT" "1/1/2019" "1:00" "am" "1/10/2019" "2:00" "am"

java -jar target/apptbook.jar -README

java -jar target/apptbook.jar -host localhost -port 8080 "Dave" "Teach Java class" "1/1/2019" "1:00" "am" "1/ZZ/2019" "2:00" "am"

java -jar target/apptbook.jar -host localhost -port 8080 "Dave" "Teach Java class" "1/1/2019" "1:00" "am" "1/2/2018" "2:00" "am"

java -jar target/apptbook.jar -host localhost -port 8080 "Teach Java class" "1/1/2019" "1:00" "am" "1/2/2018" "2:00" "am"

echo 'test0'
java -jar target/apptbook.jar -host localhost -port 8080 "Dave" "Teach Java class" "1/1/2019" "1:00" "am" "1/2/2019" "2:00" "am"

java -jar target/apptbook.jar -host localhost -port 8080 "Dave" "Good Java class" "1/1/2019" "1:00" "am" "1/2/2019" "2:00" "am"
java -jar target/apptbook.jar -host localhost -port 8080 "Dave" "Teach Java Class - right" "10/19/2019" "6:00" "pm" \
"10/19/2019" "9:00" "pm"
java -jar target/apptbook.jar -host localhost -port 8080 "Dave" "Teach Java Class - fake" "10/19/2019" "10:00" "pm" \
"10/19/2019" "11:00" "pm"
java -jar target/apptbook.jar -host localhost -port 8080 "Dave" "Teach Java Class" "10/19/2019" "6:00" "am" \
"10/19/2019" "9:00" "am"
java -jar target/apptbook.jar -host localhost -port 8080 "Haeyoon" "Go To Java Class" "10/19/2019" "6:00" "pm" \
"10/19/2019" "9:00" "pm"


echo 'test1'
java -jar target/apptbook.jar -host localhost -port 8080 "Dave"
java -jar target/apptbook.jar -host localhost -port 8080 "Haeyoon"
java -jar target/apptbook.jar -host localhost -port 8080 "Steve"
java -jar target/apptbook.jar -host localhost -port 8080 

echo 'test2 - search'

java -jar target/apptbook.jar -host localhost -port 8080 -search "Dave" "10/19/2019" "6:00" "pm" "10/19/2019" "11:00" "pm"
java -jar target/apptbook.jar -host localhost -port 8080 -search "Dave" "12/20/2019" "5:00" "pm" "12/20/2019" "6:00" "pm"
java -jar target/apptbook.jar -host localhost -port 8080 -search "Steve" "10/19/2019" "6:00" "pm" "10/19/2019" "11:00" "pm"
