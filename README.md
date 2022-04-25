# table-cleaner
A tool which is able to create scripts which are able to clean up database tables in the right order respecting data integrity rules.

## Requirement

* Java 11+
* Maven 3.5.4+
* Archimedes 2.0.1+ (checked out and build locally - Take note of Archimedes readme file)


## Build

Build the project with 

```
mvn clean install
```

via a console.

**Note:** Build will only be possible if you have build the archimedes-legacy project on your local machine before 
("archimedes-legacy" could by cloned on https://github.com/greifentor/archimedes-legacy; read the note of this project 
to build Archimedes).


## Start

After building the project start with:

```
java -jar console/target/table-cleaner-console.jar --file=console/src/test/resources/models/Integration-Test.xml
```

via console and get script:

```
UPDATE TABLE_NULLABLE_REF SET NULLABLE_REF = NULL;
UPDATE ANOTHER_TABLE_NULLABLE_REF SET NULLABLE_REF = NULL;
DELETE FROM B_TABLE;
DELETE FROM TABLE_NOT_NULLABLE_REF;
DELETE FROM ANOTHER_TABLE_NULLABLE_REF;
DELETE FROM TABLE_NULLABLE_REF;
DELETE FROM A_TABLE;
```

in the console output.


## Parameters

### file

A the parameter ``--file=[filename]`` to the program call to define which file should be use. This file must be an
Archimedes Data Model in XML format.


### deleteOperationPattern

Could be used to pass an alternative pattern for deletes (e. g. ``--deleteOperationPattern=deleteFrom("${TableName}")``).
Default is ``DELETE FROM ${TableName};``. 
The place holder ``${TableName}`` is substituted by a specific table name.


### setNullOperationPattern

Could be used to pass an alternative pattern for set null operations (e. g. ``--setNullOperationPattern=setNull("${TableName}", "${ColumnName}")``).
Default is ``UPDATE ${TableName} SET ${ColumnName} = NULL;``.
The place holder ``${TableName}`` is substituted by a specific table name.
The place holder ``${ColumnName}`` is substituted by a specific column name.


## Table Model Option

### TABLE_CLEANER_IGNORE

Mark table which should not be cleaned with option ``TABLE_CLEANER_IGNORE`` in the Archimedes Data Model.