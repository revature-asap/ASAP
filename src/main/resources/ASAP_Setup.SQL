drop table if exists post_tags;
drop table if exists tags;
drop table if exists posts;
drop table if exists user_assets;
drop table if exists assets;
drop table if exists users;

CREATE TABLE users(

	user_id serial,
	username varchar(25) unique not null,
	password varchar not null,
	email varchar(256) unique not null,
	first_name varchar(25) not null,
	last_name varchar(25) not null,
	user_role varchar(10) not null default 'Basic',
	account_confirmed bool not null default false,
	
	constraint users_pk primary key(user_id)
);


create table assets(

	asset_id serial,
	asset_name varchar(256) not null,
	ticker varchar(25) not null,
	asset_image_url text,
	market_cap numeric,
	share_outstanding numeric,
	industry_category varchar(25) not null default 'Other',
	website_url text,
	last_touched_timestamp date not null default current_date,
	
	constraint assets_pk primary key(asset_id)
	
);

create table posts(

	post_id serial,
	author_id int,
	asset_id int,
	title varchar(256) not null,
	text_content text not null,
	image_content bytea,
	creation_timestamp timestamp not null default now(),
	parent_post_id int,
	
	constraint posts_pk primary key(post_id),
	constraint author_id_fk foreign key(author_id) references users,
	constraint asset_id_fk foreign key(asset_id) references assets,
	constraint parent_post_id_fk foreign key(parent_post_id) references posts
);

create table tags(

	tag_id serial,
	name varchar(256) unique not null,
	
	constraint tags_pk primary key(tag_id)
);

create table post_tags(

	post_id int,
	tag_id int,
	
	constraint post_tags_pk primary key(post_id, tag_id),
	constraint post_id_fk foreign key(post_id) references posts,
	constraint tag_id_fk foreign key(tag_id) references tags
);


insert into users (username, password, email, first_name, last_name) 
values
	('user1', 'pass1', 'user1@gmail.com', 'Bob', 'Dylan'),
	('user2', 'pass2', 'user2@gmail.com', 'Jimi', 'Hendrix'),
	('user3', 'pass3', 'user3@gmail.com', 'David', 'Bowie');


insert into assets(asset_name, ticker, asset_image_url, market_cap, share_outstanding, website_url)
values
	('Demo Asset', 'ZZ-ZZ', 'https://atelierlks.com/wp-content/uploads/2020/10/99gen_circlein.png', 1515993, 5375.47998046875, 'https://revature.com/');

insert into posts(author_id, asset_id, title, text_content)
values 
	(1, 1, 'ZZZBest', 'DEMO-POST #zz-zz Cannot believe this asset. It''s the gift that keeps on giving. Unbelievable returns, this has got to be the most underrated find.');

insert into tags (name)
values 
	('zz-zz');

insert into post_tags 
values (1, 1);


