-- Clean All Data After run tests
DELETE FROM forms;
DELETE FROM workspaces WHERE workspace_name LIKE 'Test: %';
