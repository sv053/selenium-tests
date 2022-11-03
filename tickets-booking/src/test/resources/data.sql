BEGIN TRANSACTION;

INSERT INTO booking(user_id, ticket_id, booking_datetime)
VALUES ('user1', 'flight1', '2015-08-02T00:29:53.949'),
       ('user2', 'flight2', '2015-08-02T00:29:53.949'),
       ('user3', 'flight3', '2015-08-02T00:29:53.949'),
       ('user4', 'flight4', '2015-08-02T00:29:53.949'),
       ('user5', 'flight5', '2015-08-02T00:29:53.949'),
       ('user6', 'flight6', '2015-08-02T00:29:53.949');

COMMIT;

