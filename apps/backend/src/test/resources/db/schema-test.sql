-- Define ENUM types
DROP TYPE IF EXISTS subscriber_status CASCADE;
CREATE TYPE subscriber_status AS ENUM ('ENABLED', 'DISABLED', 'BLOCKLISTED');
DROP TYPE IF EXISTS role_type CASCADE;
CREATE TYPE role_type AS ENUM ('OWNER', 'EDITOR');

-- Create Organizations table
DROP TABLE IF EXISTS organizations CASCADE;
CREATE TABLE organizations
(
  id UUID         NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  user_id          UUID         NOT NULL, -- Owner of the organization
  created_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Teams Table
DROP TABLE IF EXISTS teams CASCADE;
CREATE TABLE teams
(
  team_id           UUID         NOT NULL PRIMARY KEY,
  organization_id   UUID         NOT NULL,
  team_name         VARCHAR(100) NOT NULL,
  created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  CONSTRAINT fk_organization_id FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE
);

-- Team Members Table
DROP TABLE IF EXISTS team_members CASCADE;
CREATE TABLE team_members
(
  team_id      UUID      NOT NULL,
  user_id      UUID      NOT NULL,
  role         role_type NOT NULL, -- Use ENUM type for roles
  created_at   TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at   TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  PRIMARY KEY (team_id, user_id),
  CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES teams (team_id) ON DELETE CASCADE
);

-- Create subscribers table
DROP TABLE IF EXISTS subscribers CASCADE;
CREATE TABLE subscribers
(
  id              UUID              NOT NULL,
  email           TEXT              NOT NULL UNIQUE,
  firstname       TEXT              NOT NULL,
  lastname        TEXT,
  status          subscriber_status NOT NULL DEFAULT 'ENABLED',
  attributes      JSON              NULL,
  organization_id UUID              NOT NULL,
  created_at      TIMESTAMP WITH TIME ZONE   DEFAULT NOW(),
  updated_at      TIMESTAMP WITH TIME ZONE   DEFAULT NOW(),
  CONSTRAINT pk_subscriber_id PRIMARY KEY (id),
  CONSTRAINT fk_organization_id FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE
);

-- Create indexes
DROP INDEX IF EXISTS idx_subs_email;
CREATE UNIQUE INDEX idx_subs_email ON subscribers (LOWER(email));
DROP INDEX IF EXISTS idx_subs_status;
CREATE INDEX idx_subs_status ON subscribers (status);
DROP INDEX IF EXISTS idx_subs_created_at;
CREATE INDEX idx_subs_created_at ON subscribers (created_at);
DROP INDEX IF EXISTS idx_subs_email_organization;
CREATE UNIQUE INDEX idx_subs_email_organization ON subscribers (LOWER(email), organization_id);

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
  organization_id   UUID NOT NULL,
  created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  CONSTRAINT pk_form_id PRIMARY KEY (id),
  CONSTRAINT fk_form_organization_id FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE
);

-- Create indexes
DROP INDEX IF EXISTS idx_forms_organization;
CREATE INDEX idx_forms_organization ON forms (organization_id);
DROP INDEX IF EXISTS idx_forms_created_at;
CREATE INDEX idx_forms_created_at ON forms (created_at);

-- Enable Row Level Security for subscribers and forms
ALTER TABLE subscribers
  ENABLE ROW LEVEL SECURITY;
ALTER TABLE forms
  ENABLE ROW LEVEL SECURITY;

-- Create policies for Row Level Security
CREATE POLICY subscriber_policy ON subscribers
  FOR SELECT USING (organization_id = current_setting('lyra.current_organization')::UUID);

CREATE POLICY form_policy ON forms
  FOR SELECT USING (organization_id = current_setting('lyra.current_organization')::UUID);
