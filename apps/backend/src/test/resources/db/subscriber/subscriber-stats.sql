INSERT INTO organizations (id, name, user_id, created_at, updated_at)
VALUES ('1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', 'Test: My First Organization',
        'da440485-3807-414b-bfc2-01b99b249088', '2024-06-02 11:00:08.251',
        '2024-06-02 11:00:08.281');

INSERT INTO subscribers (id, email, firstname, lastname, status, organization_id, created_at,
                         updated_at,
                         attributes)
VALUES ('bbf25966-6f2d-4cc5-a6da-19e79dbaba1e', 'boethius@test.com', 'Boethius', '', 'ENABLED',
        '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-02-18 15:44:03.616085+01',
        '2023-12-22 15:28:55.736027+01',
        '{
             "tags": ["tag1", "tag6"]
           }'),
       ('15a3f1bf-6236-40bd-83d4-69a9539ab1ea', 'jean-paul.sartre@test.com', 'Jean-Paul', 'Sartre',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-02-22 15:44:03.616085+01',
        '2024-01-22 15:28:55.736027+01',
        '{
          "tags": ["tag18", "tag61","tag21", "tag63","tag14", "tag51","tag41", "tag46"]
        }'),
       ('5616e0ed-2305-4d6f-88f2-9ef3d6d72a69', 'jean-francois.lyotard@test.com', 'Jean-François',
        'Lyotard', 'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb',
        '2023-03-02 15:44:03.616085+01', '2023-11-20 15:28:55.736027+01',
        '{
          "tags": ["tag89", "tag99"]
        }'),
       ('30b14a48-ddf4-413d-a638-1d26beb52ae2', 'franz.brentano@test.com', 'Franz', 'Brentano',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-11 15:44:03.616085+01',
        '2023-05-10 15:28:55.736027+02',
        '{
          "tags": ["tag1", "tag3"]
        }'),
       ('7b868e1e-d2d1-4b8a-9949-59bc4b775d0f', 'bonaventure@test.com', 'Bonaventure', '',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-04 15:44:03.616085+01',
        '2024-01-13 15:28:55.736027+01',
        '{
             "tags": ["tag3", "tag2"]
           }'),
       ('a72d3adb-7f07-4837-b592-0be854d20a67', 'maurice.henry@test.com', 'Maurice',
        'Henry', 'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-13 15:44:03.616085+01',
        '2024-02-14 15:28:55.736027+01', '{
         "tags": ["tag1", "tag5"]
       }'),
       ('dd3ff9d4-aee0-4a89-930f-bdd020845f92', 'emmanuel.henry@test.com', 'Emmanuel', 'Henry',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-02 15:44:04.616085+01',
        '2024-01-03 15:28:55.736027+01',
        '{
          "tags": ["tag1", "tag6"]
        }'),
       ('b88f202b-964d-4f44-b080-3aaa6ef03052', 'rene.descartes@test.com', 'Rene', 'Descartes',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-02-28 15:44:03.616085+01',
        '2023-11-25 15:28:55.736027+01',
        '{
          "tags": ["tag1", "tag7"]
        }'),
       ('b8f2317a-686f-4ace-a218-5c98a05cb88a', 'ralph.waldo.emerson@test.com', 'Ralph',
        'Waldo Emerson', 'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb',
        '2023-02-21 15:44:03.616085+01',
        '2024-01-30 15:28:55.736027+01', '{
         "tags": ["tag1", "tag2"]
       }'),
       ('e3839fed-f18f-4461-9e86-5bf2d5868ead', 'bertrand.russell@test.com', 'Bertrand', 'Russell',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-02-22 15:44:03.616085+01',
        '2023-12-09 15:28:55.736027+01',
        '{
          "tags": ["tag4", "tag5"]
        }');
INSERT INTO subscribers (id, email, firstname, lastname, status, organization_id, created_at,
                         updated_at,
                         attributes)
VALUES ('a4053f51-ddee-4abc-bf5d-767d7588b711', 'michel.henry@test.com', 'Michel', 'Henry',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-16 15:44:03.616085+01',
        '2023-10-26 15:28:55.736027+02',
        '{
          "tags": ["tag11", "tag25"]
        }'),
       ('96bd3f93-7be8-4ac6-9e68-1d2a50650cc6', 'maimonides@test.com', 'Maimonides', '', 'ENABLED',
        '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-14 15:44:03.616085+01',
        '2024-01-04 15:28:55.736027+01',
        '{
          "tags": ["tag5", "tag5"]
        }');
INSERT INTO subscribers (id, email, firstname, lastname, status, organization_id, created_at, updated_at, attributes)
VALUES
  ('09f7d2b5-118e-4e11-b074-b2ddf8e4fced', 'augustine@test.com', 'Augustine', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-01 12:44:03.616085+01', '2024-01-15 13:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('88fc395f-9673-4b95-9c57-ec7a9a0b97a4', 'diogenes@test.com', 'Diogenes', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-05 11:44:03.616085+01', '2023-12-30 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('772a4124-2040-4f1b-a88a-097fd50b6e96', 'pythagoras@test.com', 'Pythagoras', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-10 14:44:03.616085+01', '2023-12-05 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('c1e10d6d-e7ad-4a5a-a29d-90e53f6a3783', 'heraclitus@test.com', 'Heraclitus', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-11 15:44:03.616085+01', '2023-11-22 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('a4e8be45-63c6-43d1-9d1d-91c8b504db26', 'socrates@test.com', 'Socrates', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-12 16:44:03.616085+01', '2023-12-01 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('b1ac63f9-faea-4217-9144-c9a5647e5972', 'plato@test.com', 'Plato', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-13 13:44:03.616085+01', '2024-01-19 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('cb19a605-42a9-4855-b5f5-92301281d2e8', 'aristotle@test.com', 'Aristotle', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-15 14:44:03.616085+01', '2023-12-13 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('e8c6f569-8a97-4e44-81e8-0d353b4dc46f', 'empedocles@test.com', 'Empedocles', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-18 10:44:03.616085+01', '2024-01-28 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('fd4d34a9-48b6-45ad-8447-2cc4d0f38e33', 'zeno@test.com', 'Zeno', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-19 12:44:03.616085+01', '2023-11-30 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}'),
  ('a2f55b11-4e48-4d21-8ad0-1e1f610c6a76', 'parmenides@test.com', 'Parmenides', '', 'DISABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-21 12:44:03.616085+01', '2024-01-10 15:28:55.736027+01', '{"tags": ["tag3", "tag4"]}');

INSERT INTO subscribers (id, email, firstname, lastname, status, organization_id, created_at, updated_at, attributes)
VALUES
  ('b8fcb28a-229e-4e12-a99c-4d9d88cf2cfc', 'hegel@test.com', 'Georg', 'Hegel', 'BLOCKLISTED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-25 12:44:03.616085+01', '2023-12-15 15:28:55.736027+01', '{"tags": ["tag5"]}'),
  ('d95a339d-b621-45bb-81d6-4e1f567acdcb', 'spinoza@test.com', 'Baruch', 'Spinoza', 'BLOCKLISTED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-28 11:44:03.616085+01', '2024-01-22 15:28:55.736027+01', '{"tags": ["tag5"]}'),
  ('31d1271c-77ef-4705-8fbb-22e2397034a7', 'david.hume@test.com', 'David', 'Hume', 'BLOCKLISTED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-04-30 14:44:03.616085+01', '2023-11-25 15:28:55.736027+01', '{"tags": ["tag5"]}'),
  ('aac8e18e-03de-4142-bb10-3b673cdff83e', 'kierkegaard@test.com', 'Soren', 'Kierkegaard', 'BLOCKLISTED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-05-02 15:44:03.616085+01', '2024-01-05 15:28:55.736027+01', '{"tags": ["tag5"]}'),
  ('5739be6c-e495-49b4-9e98-62dd9d1e5b93', 'nietzsche@test.com', 'Friedrich', 'Nietzsche', 'BLOCKLISTED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-05-05 11:44:03.616085+01', '2024-01-12 15:28:55.736027+01', '{"tags": ["tag5"]}');
