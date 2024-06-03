-- Define ENUM types
DROP TYPE IF EXISTS subscriber_status CASCADE;
CREATE TYPE subscriber_status AS ENUM ('ENABLED', 'DISABLED', 'BLOCKLISTED');
DROP TYPE IF EXISTS role_type CASCADE;
CREATE TYPE role_type AS ENUM ('OWNER', 'COLLABORATOR');

-- Create Workspaces table
DROP TABLE IF EXISTS workspaces CASCADE;
CREATE TABLE workspaces
(
  workspace_id   UUID         NOT NULL PRIMARY KEY,
  workspace_name VARCHAR(100) NOT NULL,
  user_id        UUID         NOT NULL, -- Owner of the workspace
  created_at     TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at     TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Workspace Collaborators Table
DROP TABLE IF EXISTS workspace_collaborators CASCADE;
CREATE TABLE workspace_collaborators
(
  workspace_id UUID      NOT NULL,
  user_id      UUID      NOT NULL,
  role         role_type NOT NULL, -- Use ENUM type for roles
  added_at     TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  created_at   TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at   TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  PRIMARY KEY (workspace_id, user_id),
  CONSTRAINT fk_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspaces (workspace_id) ON DELETE CASCADE
);

-- Create subscribers table
DROP TABLE IF EXISTS subscribers CASCADE;
CREATE TABLE subscribers
(
  id           UUID              NOT NULL,
  email        TEXT              NOT NULL UNIQUE,
  firstname    TEXT              NOT NULL,
  lastname     TEXT,
  status       subscriber_status NOT NULL DEFAULT 'ENABLED',
  attributes   JSON              NULL,
  workspace_id UUID              NOT NULL,
  created_at   TIMESTAMP WITH TIME ZONE   DEFAULT NOW(),
  updated_at   TIMESTAMP WITH TIME ZONE   DEFAULT NOW(),
  CONSTRAINT pk_subscriber_id PRIMARY KEY (id),
  CONSTRAINT fk_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspaces (workspace_id) ON DELETE CASCADE
);

-- Create indexes
DROP INDEX IF EXISTS idx_subs_email;
CREATE UNIQUE INDEX idx_subs_email ON subscribers (LOWER(email));
DROP INDEX IF EXISTS idx_subs_status;
CREATE INDEX idx_subs_status ON subscribers (status);
DROP INDEX IF EXISTS idx_subs_created_at;
CREATE INDEX idx_subs_created_at ON subscribers (created_at);
DROP INDEX IF EXISTS idx_subs_email_workspace;
CREATE UNIQUE INDEX idx_subs_email_workspace ON subscribers (LOWER(email), workspace_id);

-- Forms
DROP TABLE IF EXISTS forms CASCADE;
CREATE TABLE forms
(
  id                UUID NOT NULL,
  name              TEXT NOT NULL,
  header            TEXT,
  description       TEXT,
  input_placeholder TEXT,
  button_text       TEXT,
  button_color      TEXT,
  background_color  TEXT,
  text_color        TEXT,
  button_text_color TEXT,
  workspace_id      UUID NOT NULL,
  created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  CONSTRAINT pk_form_id PRIMARY KEY (id),
  CONSTRAINT fk_form_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspaces (workspace_id) ON DELETE CASCADE
);

-- Create indexes
DROP INDEX IF EXISTS idx_forms_workspace;
CREATE INDEX idx_forms_workspace ON forms (workspace_id);
DROP INDEX IF EXISTS idx_forms_created_at;
CREATE INDEX idx_forms_created_at ON forms (created_at);

-- Enable Row Level Security for subscribers and forms
ALTER TABLE subscribers
  ENABLE ROW LEVEL SECURITY;
ALTER TABLE forms
  ENABLE ROW LEVEL SECURITY;

-- Create policies for Row Level Security
CREATE POLICY subscriber_policy ON subscribers
  FOR SELECT USING (workspace_id = current_setting('lyra.current_workspace')::UUID);

CREATE POLICY form_policy ON forms
  FOR SELECT USING (workspace_id = current_setting('lyra.current_workspace')::UUID);
