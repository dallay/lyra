-- Clean All Data After run tests
DELETE FROM forms;
DELETE FROM organizations WHERE name LIKE 'Test: %';
