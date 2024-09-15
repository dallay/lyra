create table tags
(
  id         uuid not null primary key,
  name       text not null,
  color      text not null check (
    color in (
              'default',
              'purple',
              'pink',
              'red',
              'blue',
              'yellow'
      )
    ),
  created_at timestamp with time zone default now(),
  updated_at timestamp with time zone default now()
);

create table subscriber_tags
(
  subscriber_id uuid not null,
  tag_id uuid not null,
  primary key (subscriber_id, tag_id),
  constraint fk_subscriber_id foreign key (subscriber_id) references subscribers (id) on delete cascade,
  constraint fk_tag_id foreign key (tag_id) references tags (id) on delete cascade,
  created_at    timestamp with time zone default now(),
  updated_at    timestamp with time zone default now()
);
