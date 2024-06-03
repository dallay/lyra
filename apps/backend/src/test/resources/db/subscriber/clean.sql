-- Clean All Data After run tests
DELETE FROM subscribers
WHERE email LIKE '%@test.com';
DELETE FROM workspaces WHERE workspace_name LIKE 'Test: %';
