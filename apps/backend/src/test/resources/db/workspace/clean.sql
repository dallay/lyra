-- Delete all data from the workspace tables to ensure a clean state for testing.
-- The test data starts in `workspace_name` with "Test: ".

DELETE FROM workspaces WHERE workspace_name LIKE 'Test: %';
