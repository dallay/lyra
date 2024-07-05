-- Clean All Data After run tests
DELETE FROM subscribers
WHERE email LIKE '%@test.com';
DELETE FROM organizations WHERE name LIKE 'Test: %';
