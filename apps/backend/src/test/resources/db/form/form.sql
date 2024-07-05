INSERT INTO organizations (id, name, user_id, created_at, updated_at)
VALUES ('7a27728a-8ef3-4070-b615-1d5ddf9a7863', 'Test: My First Organization',
        'efc4b2b8-08be-4020-93d5-f795762bf5c9', '2024-06-02 11:00:08.251',
        '2024-06-02 11:00:08.281');


INSERT INTO forms (id, name, header, description, input_placeholder, button_text,
                   button_color, background_color, text_color, button_text_color,
                   organization_id, created_at, updated_at)
VALUES ('1659d4ae-402a-4172-bf8b-0a5c54255587', 'Programming newsletter v1', 'Juan''s Newsletter',
        '🟢 Some description 🔴', 'Enter your email', 'Subscribe', '2C81E5', 'DFD150', '222222',
        'FFFFFF', '7a27728a-8ef3-4070-b615-1d5ddf9a7863',
        '2024-04-21 19:56:07.632', '2024-04-21 19:56:07.711');
