INSERT INTO organizations (id, name, user_id, created_at, updated_at)
VALUES ('a0654720-35dc-49d0-b508-1f7df5d915f1', 'Test: My First Workspace',
        'efc4b2b8-08be-4020-93d5-f795762bf5c9', '2024-06-02 11:00:08.251',
        '2024-06-02 11:00:08.281');

INSERT INTO subscribers
(id, email, firstname, lastname, status, attributes, organization_id, created_at, updated_at)
VALUES ('d73e2961-ec29-4f19-b5c4-b9c2dc7f1dee'::uuid, 'john.doe@test.com', 'John',
        'Doe', 'ENABLED'::"subscriber_status", '{}', 'a0654720-35dc-49d0-b508-1f7df5d915f1',
        '2024-01-11 16:02:06.643', '2024-01-11 16:02:06.645');
INSERT INTO subscribers
(id, email, firstname, lastname, status, attributes, organization_id, created_at, updated_at)
VALUES ('d73e2961-ec29-4f19-b5c4-b9c2dc7f1def'::uuid, 'jana.doe@test.com', 'Jana',
        'Doe', 'ENABLED'::"subscriber_status", '{}', 'a0654720-35dc-49d0-b508-1f7df5d915f1',
        '2024-01-11 16:02:06.643', '2024-01-11 16:02:06.645');
