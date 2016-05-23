# Warehouse NC project #

### First step - download Deploy/WareHouse.war ###

### MySQL database ###

1. Install and Configure [MySQL](https://www.mysql.com/) Database
2. Create new database
3. Run script
```

CREATE TABLE area(
	area_id int(6) default 0,
    area_name varchar(45),
    rem_space int(11),
    primary key(area_id)
);

CREATE TABLE box(
	id int(11) auto_increment,
    area_id int(6),
    box_size int(11),
    x int(2),
    y int(2),
    primary key (id),
    unique box_id_pk (id),
    foreign key (area_id) references area (area_id) ON DELETE SET NULL

);

INSERT INTO area(area_id, area_name, rem_space)
VALUES(0, 'Area 1', 42);

INSERT INTO area(area_id, area_name, rem_space)
VALUES(1, 'Area 2', 42);

INSERT INTO area(area_id, area_name, rem_space)
VALUES(2, 'Area 3', 42);

```

Configure src/resource/connection.properties
```
DRIVER_CLASS=com.mysql.jdbc.Driver
URL=jdbc:mysql://localhost:3306/YourDatabaseName
USER=login
PASSWORD=password

```

### Tomcat ###
1. Install and configure [Tomcat](http://tomcat.apache.org/)
2. Edit sript bin/startup.bat and run
3. Open http://localhost:8080/.war fileName/

P.S Avoid white spaces in paths and filenames
