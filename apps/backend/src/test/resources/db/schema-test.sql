-- Eliminar los triggers existentes
drop trigger if exists update_organizations_updated_at on organizations;
drop trigger if exists update_teams_updated_at on teams;
drop trigger if exists update_team_members_updated_at on team_members;
drop trigger if exists update_subscribers_updated_at on subscribers;
drop trigger if exists update_forms_updated_at on forms;

-- Eliminar las funciones existentes
drop function if exists update_updated_at_column;

-- Eliminar políticas de seguridad a nivel de fila
drop policy if exists subscriber_policy on subscribers;
drop policy if exists form_policy on forms;

-- Deshabilitar la seguridad a nivel de fila
alter table if exists subscribers disable row level security;
alter table if exists forms disable row level security;

-- Eliminar índices existentes
drop index if exists idx_subs_email;
drop index if exists idx_subs_status;
drop index if exists idx_subs_created_at;
drop index if exists idx_subs_email_organization;
drop index if exists idx_forms_organization;
drop index if exists idx_forms_created_at;

-- Eliminar las tablas existentes
drop table if exists team_members cascade;
drop table if exists teams cascade;
drop table if exists forms cascade;
drop table if exists subscribers cascade;
drop table if exists organizations cascade;

-- Eliminar los tipos de datos enumerados existentes
drop type if exists role_type;
drop type if exists subscriber_status;

-- Crear los tipos de datos enumerados
create type subscriber_status as enum ('ENABLED', 'DISABLED', 'BLOCKLISTED');
create type role_type as enum ('OWNER', 'EDITOR');

-- Crear la tabla organizations
create table organizations
(
  id         uuid not null primary key,
  name       text not null,
  user_id    uuid not null, -- Owner of the organization
  created_at timestamp with time zone default now(),
  updated_at timestamp with time zone default now()
);

-- Crear la tabla teams
create table teams
(
  team_id         uuid not null primary key,
  organization_id uuid not null,
  name            text not null,
  created_at      timestamp with time zone default now(),
  updated_at      timestamp with time zone default now(),
  constraint fk_organization_id foreign key (organization_id) references organizations (id) on delete cascade
);

-- Crear la tabla team_members
create table team_members
(
  team_id    uuid      not null,
  user_id    uuid      not null,
  role       role_type not null,
  created_at timestamp with time zone default now(),
  updated_at timestamp with time zone default now(),
  primary key (team_id, user_id),
  constraint fk_team_id foreign key (team_id) references teams (team_id) on delete cascade
);

-- Crear la tabla subscribers
create table subscribers
(
  id              uuid              not null primary key,
  email           text              not null unique,
  firstname       text              not null,
  lastname        text,
  status          subscriber_status not null default 'ENABLED',
  attributes      json,
  organization_id uuid              not null,
  created_at      timestamp with time zone   default now(),
  updated_at      timestamp with time zone   default now(),
  constraint fk_organization_id foreign key (organization_id) references organizations (id) on delete cascade
);

-- Crear índices para la tabla subscribers
create unique index idx_subs_email on subscribers using btree (lower(email));
create index idx_subs_status on subscribers using btree (status);
create index idx_subs_created_at on subscribers using btree (created_at);
create unique index idx_subs_email_organization on subscribers using btree (lower(email), organization_id);

-- Crear la tabla forms
create table forms
(
  id                uuid not null primary key,
  name              text not null,
  header            text,
  description       text,
  input_placeholder text,
  button_text       text,
  button_color      text,
  background_color  text,
  text_color        text,
  button_text_color text,
  organization_id   uuid not null,
  created_at        timestamp with time zone default now(),
  updated_at        timestamp with time zone default now(),
  constraint fk_form_organization_id foreign key (organization_id) references organizations (id) on delete cascade
);

-- Crear índices para la tabla forms
create index idx_forms_organization on forms using btree (organization_id);
create index idx_forms_created_at on forms using btree (created_at);

-- Habilitar seguridad a nivel de fila en las tablas
alter table subscribers enable row level security;
alter table forms enable row level security;

-- Crear políticas de seguridad a nivel de fila
create policy subscriber_policy on subscribers for
  select
  to public using (
  organization_id = current_setting('lyra.current_organization')::uuid
  );

create policy form_policy on forms for
  select
  to public using (
  organization_id = current_setting('lyra.current_organization')::uuid
  );

-- Crear la función para actualizar la columna updated_at
create or replace function update_updated_at_column() returns trigger as '
  BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
  END;
' language plpgsql;

-- Crear triggers para actualizar la columna updated_at
create trigger update_organizations_updated_at
  before update on organizations
  for each row execute function update_updated_at_column();

create trigger update_teams_updated_at
  before update on teams
  for each row execute function update_updated_at_column();

create trigger update_team_members_updated_at
  before update on team_members
  for each row execute function update_updated_at_column();

create trigger update_subscribers_updated_at
  before update on subscribers
  for each row execute function update_updated_at_column();

create trigger update_forms_updated_at
  before update on forms
  for each row execute function update_updated_at_column();
