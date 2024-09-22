-- This script depends on the following scripts:
--  - db/subscriber/subscriber.sql (Create the organization and the subscriber)


-- Insert tags
INSERT INTO tags
(id, name, color, organization_id, created_at, updated_at)
VALUES ('16f86bda-45ac-4f9f-9658-4b359a1b98bf'::uuid, 'Test: Free', 'purple',
        'a0654720-35dc-49d0-b508-1f7df5d915f1'::uuid, '2024-09-15 21:04:16.833',
        '2024-09-15 21:04:16.861');
INSERT INTO tags
(id, name, color, organization_id, created_at, updated_at)
VALUES ('d667bf8b-69d7-4e32-9488-8ad9865fc644'::uuid, 'Test: Pay', 'blue',
        'a0654720-35dc-49d0-b508-1f7df5d915f1'::uuid, '2024-09-15 21:06:17.829',
        '2024-09-15 21:06:17.850');
INSERT INTO tags
(id, name, color, organization_id, created_at, updated_at)
VALUES ('331afd9a-b3b4-47b3-83cf-3fcb3ab9f926'::uuid, 'Test: Premium', 'red',
        'a0654720-35dc-49d0-b508-1f7df5d915f1'::uuid, '2024-09-18 00:14:13.156',
        '2024-09-18 00:14:13.176');

-- Insert subscriber tags
INSERT INTO subscriber_tags
(tag_id, subscriber_id, created_at, updated_at)
VALUES ('16f86bda-45ac-4f9f-9658-4b359a1b98bf'::uuid, 'd73e2961-ec29-4f19-b5c4-b9c2dc7f1dee'::uuid,
        '2024-09-15 21:04:16.833', '2024-09-15 21:04:16.861');
INSERT INTO subscriber_tags
(tag_id, subscriber_id, created_at, updated_at)
VALUES ('d667bf8b-69d7-4e32-9488-8ad9865fc644'::uuid, 'd73e2961-ec29-4f19-b5c4-b9c2dc7f1dee'::uuid,
        '2024-09-15 21:06:17.829', '2024-09-15 21:06:17.850');
INSERT INTO subscriber_tags
(tag_id, subscriber_id, created_at, updated_at)
VALUES ('331afd9a-b3b4-47b3-83cf-3fcb3ab9f926'::uuid, 'd73e2961-ec29-4f19-b5c4-b9c2dc7f1dee'::uuid,
        '2024-09-18 00:14:13.156', '2024-09-18 00:14:13.176');
INSERT INTO subscriber_tags
(tag_id, subscriber_id, created_at, updated_at)
VALUES ('16f86bda-45ac-4f9f-9658-4b359a1b98bf'::uuid, 'd73e2961-ec29-4f19-b5c4-b9c2dc7f1def'::uuid,
        '2024-09-15 21:04:16.833', '2024-09-15 21:04:16.861');
