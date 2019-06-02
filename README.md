# Simple SQL Parser
**Simple SQL Parser** is a simple parser which fulfills a challenge of parsing SQL queries.
It supports currently four different statements:
- USE statement
- INSERT statement
- SELECT statement
- DELETE statement

**Simple SQL Parser** is a maven project, therefore, we can build and run by using normal maven command.

### Compile and build project
Using command 'mvn clean package'

### Run project
Using command 'mvn exec:java -Dexec.args="<file-path>"'
The parsed SQL statements will be represented in an AST.
  
### Example
Parsing the SQL statement 'SELECT d.DepartmentId Id, d.DepartmentName AS Name FROM Department d;' gives us an AST tree as below:
'''
SELECT
  \- COLUMNS
   \-\- Column: DepartmentName
    \-\-\- Alias: Name
    \-\-\- Table: d
   \-\- Column: DepartmentId
    \-\-\- Alias: Id
    \-\-\- Table: d
  \- FROM
   \-\- Table: Department
    \-\-\- Alias: d
'''
  
