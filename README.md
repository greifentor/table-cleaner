# table-cleaner
A tool which is able to create scripts which are able to clean up database tables in the right order respecting data integrity rules.

## Requirement

* Java 11+
* Maven 3.5.4+
* Archimedes 2.0.1+ (checked out and build locally - Take note of Archimedes readme file)


## Parameters

### file

A the parameter ``--file=[filename]`` to the program call to define which file should be use. This file must be an
Archimedes Data Model in XML format.


## Table Model Option

### TABLE_CLEANER_IGNORE

Mark table which should not be cleaned with option ``TABLE_CLEANER_IGNORE`` in the Archimedes Data Model.