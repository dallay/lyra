-- Clean All Data After run tests
DELETE FROM subscribers
where email like '%@test.com';
