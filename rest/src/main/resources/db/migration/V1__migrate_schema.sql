CREATE TABLE comments
(
    id               UUID         NOT NULL,
    content          VARCHAR(255) NOT NULL,
    first_name       VARCHAR(255) NOT NULL,
    last_name        VARCHAR(255) NOT NULL,
    publish_date     TIMESTAMP WITHOUT TIME ZONE,
    last_edited_date TIMESTAMP WITHOUT TIME ZONE,
    last_edited_by   UUID,
    room_id          UUID         NOT NULL,
    user_id          UUID         NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);


INSERT INTO comments (id, content, first_name, last_name, publish_date, last_edited_date, last_edited_by, room_id, user_id)
VALUES
    ('1b4a2d8a-5f15-4c7d-9ad1-e5db3e1b6f2d', 'This is a test comment 1', 'John', 'Doe', NOW(), NULL, NULL, '923364b0-4ed0-4a7e-8c23-ceb5c238ceee', '8eabb4ff-df5b-4e39-8642-0dcce375798c'),
    ('2c3a4d9e-2d4c-4e67-8f61-f1f0a7e5e4d1', 'This is a test comment 2', 'Jane', 'Smith', NOW(), '2024-08-10 12:34:56', '8eabb4ff-df5b-4e39-8642-0dcce375798c', '923364b0-4ed0-4a7e-8c23-ceb5c238ceee', '8eabb4ff-df5b-4e39-8642-0dcce375798c'),
    ('3e4d5e6f-8b7f-4c9d-a0b9-5d12f8b9f1d3', 'This is a test comment 3', 'Alice', 'Johnson', NOW(), NULL, NULL, '923364b0-4ed0-4a7e-8c23-ceb5c238ceee', '8eabb4ff-df5b-4e39-8642-0dcce375798c'),
    ('4f6a7b8c-9d8f-4d3e-b0a7-6f23e8d5b3f1', 'This is a test comment 4', 'Bob', 'Brown', NOW(), '2024-08-11 14:20:30', '8eabb4ff-df5b-4e39-8642-0dcce375798c', '923364b0-4ed0-4a7e-8c23-ceb5c238ceee', '8eabb4ff-df5b-4e39-8642-0dcce375798c');
