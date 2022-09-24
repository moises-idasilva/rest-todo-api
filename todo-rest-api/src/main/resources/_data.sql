set foreign_key_checks = false;

DELETE FROM todo_item;
DELETE FROM todo_list;
DELETE FROM todo_directory;
DELETE FROM user;


INSERT INTO user (id, activation_code, email, first_name, last_name, password,
                              user_code, username, is_active, created_on, updated_on, todo_directory_id)
VALUES (1, 'asdfasdf', 'moises.dasirva+jdoe@gmail.com', 'John', 'Doe', 'John123$', 'ASDF','jdoe', false, utc_timestamp, utc_timestamp, 1);

INSERT INTO todo_directory (id, owner_code, owner_username, user_id)
VALUES (1, 'asdfasdf', 'jdoe', 1);

INSERT INTO todo_list (id, name, description, color, completed, created_on, updated_on, todo_directory_id)
VALUES  (1, 'Home', 'Home ToDo List', 'green', false, utc_timestamp, utc_timestamp, 1),
        (2, 'Work', 'Work ToDo List', 'red', false, utc_timestamp, utc_timestamp, 1);

INSERT INTO todo_item (id, name, description, color, completed, created_on, updated_on, todo_list_id)
VALUES  (1, 'Buy Milk', 'Skin milk 1%', 'green', false, utc_timestamp, utc_timestamp, 1),
        (2, 'Buy Rice', 'Jasmine type', 'green', false, utc_timestamp, utc_timestamp, 1),
        (3, 'Doctor Appointment', '3PM with Dr Smith', 'green', false, utc_timestamp, utc_timestamp, 1),
        (4, 'Buy paper', 'A5 and Legal size paper', 'red', false, utc_timestamp, utc_timestamp, 2),
        (5, 'Buy pencil', 'Blue and red', 'red', false, utc_timestamp, utc_timestamp, 2),
        (6, 'Call client Mary', 'phone 305.325.8965', 'red', false, utc_timestamp, utc_timestamp, 2);

set foreign_key_checks = true;
