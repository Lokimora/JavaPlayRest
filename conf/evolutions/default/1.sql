# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table public.roles (
  id                        bigserial not null,
  name                      varchar(255),
  constraint pk_roles primary key (id))
;

create table public.users (
  id                        bigserial not null,
  name                      varchar(255),
  login                     varchar(255),
  password                  varchar(255),
  constraint pk_users primary key (id))
;


create table public.user_roles (
  user_id                        bigint not null,
  role_id                        bigint not null,
  constraint pk_public.user_roles primary key (user_id, role_id))
;



alter table public.user_roles add constraint fk_public.user_roles_public.u_01 foreign key (user_id) references public.users (id);

alter table public.user_roles add constraint fk_public.user_roles_public.r_02 foreign key (role_id) references public.roles (id);

# --- !Downs

drop table if exists public.roles cascade;

drop table if exists public.user_roles cascade;

drop table if exists public.users cascade;

