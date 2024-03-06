-- Define ENUM types
DROP TYPE IF EXISTS subscriber_status CASCADE;
CREATE TYPE subscriber_status AS ENUM ('ENABLED', 'DISABLED', 'BLOCKLISTED');

-- Create subscribers table
DROP TABLE IF EXISTS subscribers CASCADE;
CREATE TABLE subscribers (
                           id uuid NOT NULL,
                           email TEXT NOT NULL UNIQUE,
                           firstname TEXT NOT NULL,
                           lastname TEXT,
                           status subscriber_status NOT NULL DEFAULT 'ENABLED',
                           attributes JSON NULL,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
                           updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
                           CONSTRAINT pk_emp_id PRIMARY KEY (id)
);

-- Create indexes
DROP INDEX IF EXISTS idx_subs_email;
CREATE UNIQUE INDEX idx_subs_email ON subscribers(LOWER(email));
DROP INDEX IF EXISTS idx_subs_status;
CREATE INDEX idx_subs_status ON subscribers(status);
DROP INDEX IF EXISTS idx_subs_created_at;
CREATE INDEX idx_subs_created_at ON subscribers(created_at);
