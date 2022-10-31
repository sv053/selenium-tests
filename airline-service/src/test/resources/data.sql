INSERT INTO airlines(name, description, owner, image_data)
VALUES ('name1', 'description1', 'owner1', 'image1'),
       ('name2', 'description2', 'owner2', 'image2'),
       ('name3', 'description3', 'owner3', 'image3'),
       ('name4', 'description4', 'owner4', 'image4');

INSERT INTO flights(airline_id, from_country, from_airport, from_gate,
                    to_country, to_airport, to_gate, date_time)
VALUES (1, 'country', 'airport', '1', 'country', 'airport', '1', '2022-10-31T21:51:36.867100600'),
       (2, 'country', 'airport', '1', 'country', 'airport', '1', '2022-10-31T21:51:36.867100600'),
       (3, 'country', 'airport', '1', 'country', 'airport', '1', '2022-10-31T21:51:36.867100600'),
       (4, 'country', 'airport', '1', 'country', 'airport', '1', '2022-10-31T21:51:36.867100600');
