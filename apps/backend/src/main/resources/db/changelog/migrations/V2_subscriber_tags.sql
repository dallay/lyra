create table tags
(
  id              uuid not null primary key,
  name            text not null,
  color           text not null check (
    color in (
              'default',
              'purple',
              'pink',
              'red',
              'blue',
              'yellow'
      )
    ),
  organization_id uuid not null,
  created_at      timestamp with time zone default now(),
  updated_at      timestamp with time zone default now(),

  constraint fk_tags_organization_id foreign key (organization_id) references organizations (id) on delete cascade
);

create table subscriber_tags
(
  subscriber_id uuid not null,
  tag_id        uuid not null,
  primary key (subscriber_id, tag_id),
  constraint fk_subscriber_id foreign key (subscriber_id) references subscribers (id) on delete cascade,
  constraint fk_tag_id foreign key (tag_id) references tags (id) on delete cascade,
  created_at    timestamp with time zone default now(),
  updated_at    timestamp with time zone default now()
);

alter table tags
  enable row level security;

create policy tags_policy on tags for
  select
  to public using (
  organization_id = current_setting('lyra.current_organization')::uuid
  );
