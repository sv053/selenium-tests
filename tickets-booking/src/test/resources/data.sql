BEGIN TRANSACTION;

INSERT INTO booking(id, user_id, flight_id, booking_datetime)
VALUES (1, 'user1', 'flight1', 2016-06-12),
       (2, 'user1', 'flight2', 2016-06-12),
       (3, 'user1', 'flight3', 2018-08-15),
       (4, 'user1', 'flight4', 2017-03-02),
       (5, 'user1', 'flight5', 2020-05-11),
       (6, 'user1', 'flight6', 2021-06-30);

COMMIT;

