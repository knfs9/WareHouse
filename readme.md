# Warehouse NC project #

### First step - clone project ###

### MySQL database ###

1. Install and Configure MySQL Database
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
