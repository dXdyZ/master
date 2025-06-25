create table users (
    id serial primary key,
    username text not null
);

create table profiles (
    user_id integer primary key references users(id),
    bio text
);

