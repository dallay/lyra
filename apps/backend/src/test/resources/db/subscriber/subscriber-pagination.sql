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
          "planet": "earth",
          "initials": "B."
        }'),
       ('15a3f1bf-6236-40bd-83d4-69a9539ab1ea', 'jean-paul.sartre@test.com', 'Jean-Paul', 'Sartre',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-02-22 15:44:03.616085+01',
        '2024-01-22 15:28:55.736027+01',
        '{
          "planet": "earth",
          "initials": "J. S."
        }'),
       ('5616e0ed-2305-4d6f-88f2-9ef3d6d72a69', 'jean-francois.lyotard@test.com', 'Jean-François',
        'Lyotard', 'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb',
        '2023-03-02 15:44:03.616085+01', '2023-11-20 15:28:55.736027+01',
        '{
          "planet": "earth",
          "initials": "J. L."
        }'),
       ('30b14a48-ddf4-413d-a638-1d26beb52ae2', 'franz.brentano@test.com', 'Franz', 'Brentano',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-11 15:44:03.616085+01',
        '2023-05-10 15:28:55.736027+02',
        '{
          "planet": "earth",
          "initials": "F. B."
        }'),
       ('7b868e1e-d2d1-4b8a-9949-59bc4b775d0f', 'bonaventure@test.com', 'Bonaventure', '',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-04 15:44:03.616085+01',
        '2024-01-13 15:28:55.736027+01',
        '{
          "planet": "earth",
          "initials": "B."
        }'),
       ('a72d3adb-7f07-4837-b592-0be854d20a67', 'maurice.henry@test.com', 'Maurice',
        'Henry', 'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-13 15:44:03.616085+01',
        '2024-02-14 15:28:55.736027+01', '{
         "planet": "earth",
         "initials": "M. M."
       }'),
       ('dd3ff9d4-aee0-4a89-930f-bdd020845f92', 'emmanuel.henry@test.com', 'Emmanuel', 'Henry',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-02 15:44:04.616085+01',
        '2024-01-03 15:28:55.736027+01',
        '{
          "planet": "earth",
          "initials": "E. L."
        }'),
       ('b88f202b-964d-4f44-b080-3aaa6ef03052', 'rene.descartes@test.com', 'Rene', 'Descartes',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-02-28 15:44:03.616085+01',
        '2023-11-25 15:28:55.736027+01',
        '{
          "planet": "earth",
          "initials": "R. D."
        }'),
       ('b8f2317a-686f-4ace-a218-5c98a05cb88a', 'ralph.waldo.emerson@test.com', 'Ralph',
        'Waldo Emerson', 'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb',
        '2023-02-21 15:44:03.616085+01',
        '2024-01-30 15:28:55.736027+01', '{
         "planet": "earth",
         "initials": "R. W. E."
       }'),
       ('e3839fed-f18f-4461-9e86-5bf2d5868ead', 'bertrand.russell@test.com', 'Bertrand', 'Russell',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-02-22 15:44:03.616085+01',
        '2023-12-09 15:28:55.736027+01',
        '{
          "planet": "earth",
          "initials": "B. R."
        }');
INSERT INTO subscribers (id, email, firstname, lastname, status, organization_id, created_at,
                         updated_at,
                         attributes)
VALUES ('a4053f51-ddee-4abc-bf5d-767d7588b711', 'michel.henry@test.com', 'Michel', 'Henry',
        'ENABLED', '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-16 15:44:03.616085+01',
        '2023-10-26 15:28:55.736027+02',
        '{
          "planet": "earth",
          "initials": "M. H."
        }'),
       ('96bd3f93-7be8-4ac6-9e68-1d2a50650cc6', 'maimonides@test.com', 'Maimonides', '', 'ENABLED',
        '1b423df9-d6fc-4fd9-904b-4bb40dc88aeb', '2023-03-14 15:44:03.616085+01',
        '2024-01-04 15:28:55.736027+01',
        '{
          "planet": "earth",
          "initials": "M."
        }');
