INSERT INTO organizations (id, name, user_id, created_at, updated_at)
VALUES ('27172d5a-b88e-451c-9787-312706f4570d', 'Test: My First Organization',
        'efc4b2b8-08be-4020-93d5-f795762bf5c9', '2024-06-02 11:00:08.251',
        '2024-06-02 11:00:08.281');

INSERT INTO forms (id, name, header, description, input_placeholder, button_text,
                   button_color, background_color, text_color, button_text_color,
                   organization_id, created_at, updated_at)
VALUES ('1659d4ae-402a-4172-bf8b-0a5c54255587', 'Programming newsletter', 'Astrum''s Newsletter',
        'The best programming newsletter', 'Enter your email', 'Subscribe', '2C81E5', 'DFD150',
        '222222', 'FFFFFF', '27172d5a-b88e-451c-9787-312706f4570d', '2024-04-21 19:56:07.632',
        '2024-04-21 19:56:07.711');
INSERT INTO forms (id, name, header, description, input_placeholder, button_text,
                   button_color, background_color, text_color, button_text_color,
                   organization_id, created_at, updated_at)
VALUES ('f8df2049-f29a-45f2-bf99-c960cf038cae', 'Generic Cotton Pizza',
        'Generic Cotton Pizza''s Newsletter',
        'Generic Cotton Pizza', 'Enter your email', 'Subscribe', '2C81E5', 'DFD150', '222222',
        'FFFFFF', '27172d5a-b88e-451c-9787-312706f4570d', '2024-04-22 19:56:07.632', '2024-04-22 19:56:07.711');

INSERT INTO forms (id, name, header, description, input_placeholder, button_text,
                   button_color, background_color, text_color, button_text_color, organization_id, created_at, updated_at)
VALUES ('f8df2049-f29a-45f2-bf99-c550cf038cae', 'Kerluke - Bashirian',
        'Kerluke - Bashirian''s Newsletter', 'Kerluke - Bashirian', 'Enter your email', 'Subscribe',
        '2C81E5', 'DFD150', '222222', 'FFFFFF', '27172d5a-b88e-451c-9787-312706f4570d', '2024-04-23 19:56:07.632',
        '2024-04-23 19:56:07.711');

INSERT INTO forms (id, name, header, description, input_placeholder, button_text,
                   button_color, background_color, text_color, button_text_color, organization_id, created_at, updated_at)
VALUES ('f8df2049-f28a-45f2-bf99-c960cf038cbe', 'Dare Group', 'Dare Group''s Newsletter',
        'Dare Group', 'Enter your email', 'Subscribe', '2C81E5', 'DFD150', '222222', 'FFFFFF',
        '27172d5a-b88e-451c-9787-312706f4570d', '2024-04-24 19:56:07.632', '2024-04-24 19:56:07.711');

INSERT INTO forms (id, name, header, description, input_placeholder, button_text,
                   button_color, background_color, text_color, button_text_color,
                   organization_id, created_at, updated_at)
VALUES ('f7df2049-f29a-45f2-bf88-c860cf037cbe', 'Farrell, Weber and Romaguera',
        'Farrell, Weber and Romaguera''s Newsletter', 'Farrell, Weber and Romaguera',
        'Enter your email',
        'Subscribe', '2C81E5', 'DFD150', '222222', 'FFFFFF',
        '27172d5a-b88e-451c-9787-312706f4570d', '2024-04-25 19:56:07.632', '2024-04-25 19:56:07.711');
