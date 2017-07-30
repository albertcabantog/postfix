# postfix

Assumptions:
1.  System data source is embedded and displayed when the program is executed
2.  Java minium version is 1.8
3.  Apache Maven 3.2.2 used to build the jar file

Implementation details:
The program is executable using Java 1.8.  No other third-party library was used.
To run the program, generate the jar file first using maven: 
<code>mvn clean install -DskipTests</code>

Navigate to the target directory and execute the program:
```
java -jar postfix-1.0.jar /tmp/input.txt /tmp/output.txt
```

Limitations:
1.  Current data source only support columns A to C and rows 1 to 5 only (A1..C5)
<br>
| A  | B | C |
|--- |---|---|
|1 |6|11|
|2 |7|12|
|3 |8|13|
|4 |9|14|
|5 |10|15|
<br>
2.  CSV input file supports cell value of whole number only
