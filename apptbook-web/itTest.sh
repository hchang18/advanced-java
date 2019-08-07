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

echo 'test3 - search function in front'
java -jar target/apptbook.jar -search -host localhost -port 8080 "Dave" "10/19/2019" "6:00" "pm" "10/19/2019" "11:00" "pm"
java -jar target/apptbook.jar -search -host localhost -port 8080 "Dave" "12/20/2019" "5:00" "pm" "12/20/2019" "6:00" "pm"
java -jar target/apptbook.jar -search -host localhost -port 8080 "Steve" "10/19/2019" "6:00" "pm" "10/19/2019" "11:00" "pm"


echo '**** Test1: README for your client'
java -jar target/apptbook.jar -README

echo '**** Test2: Help for your client'
java -jar target/apptbook.jar

echo '**** Test3: Connect to port with no server'
java -jar target/apptbook.jar -host localhost -port 42346 Project4 "This is Test 3" 01/06/2019 6:00 pm 01/06/2019 6:52 pm

echo '**** Test 4: Querying an empty appointment book'
java -jar target/apptbook.jar -search -host localhost -port 8080 Project4 01/06/2019 5:00 pm 01/06/2019 7:00 pm

echo '**** Test 5: Adding an appointment'
java -jar target/apptbook.jar -host localhost -port 8080 -print Project4 "This is Test 5" 01/08/2019 8:00 am 01/08/2019 8:02 am 

echo '**** Test 6: Querying an Appointmnet Book (sould be empty)'
java -jar target/apptbook.jar -search -host localhost -port 8080 Project4 01/06/2019 5:00 pm 01/06/2019 7:00 pm

echo '**** Test 7: Adding another appointment'
java -jar target/apptbook.jar -host localhost -port 8080 Project4 "This is Test 7" 01/10/2019 10:00 am 01/10/2019 10:14 am

echo '**** Test 8: Adding a third appointment'
java -jar target/apptbook.jar -host localhost -port 8080 Project4 "This is Test 8" 01/12/2019 12:00 pm 01/12/2019 12:15 pm


echo '*****  Test 9: Querying an Appointment Book (two appointments)'

java -jar target/apptbook.jar -search -host localhost -port 8080 Project4 01/09/2019 1:00 pm 01/20/2019 1:23 pm


echo '*****  Test 10: Adding a fourth Appointment Book'

java -jar target/apptbook.jar -host localhost -port 8080 Project4 "This is Test 10" 01/31/2019 12:00 pm 01/31/2019 12:15 pm

echo '*****  Test 11: Final query (one appointment)'

java -jar target/apptbook.jar -search -host localhost -port 8080 Project4 01/30/2019 12:00 pm 02/01/2019 12:15 pm

echo '*****  Test 13: Creating a second Appointment Book'

java -jar target/apptbook.jar -host localhost -port 8080 -print Project4a "First appointment in second Book" 01/13/2019 8:00 am 01/13/2019 8:02 am


echo '****  Test 14: Adding appointment to second appointment book'

java -jar target/apptbook.jar -host localhost -port 8080 -print Project4a "Second appointment in second Book" 02/13/2019 9:00 am 02/13/2019 10:02 am









