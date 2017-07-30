# postfix

## Assumptions and prerequisites:
- System data source is embedded and displayed when the program is executed.  It is saved in a **Map** object where each column is the key
and the value is a list containing each of the row
- Java minium version is 1.8
- Apache Maven 3.2.2 used to build the jar file

## Implementation details:
The program expects to parse an input file containing the reference column/row cell from the data source like in a spreadsheet, number, and operands.
The contents of the input file will be processed each line and parsed again for each value divided by the comma delimeter.
The postfix calculation is evaluated then for each of these value by saving it in a stack which supports LIFO.
<br>
To run the program, generate the jar file first using maven: 
<code>mvn clean install -DskipTests</code>

Navigate to the target directory and execute the program:
```
java -jar postfix-1.0.jar /tmp/input.txt /tmp/output.txt
``` 
The output will be written in the file as provided in the second parameter.

## Limitations:
- Current data source has a limited value of columns A to C and rows 1 to 5 (A1..C5)
- CSV input file can only have whole number if it is not the column/row from the data source or an operand
