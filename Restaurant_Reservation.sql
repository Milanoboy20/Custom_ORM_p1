create schema restaurant;

drop table customer cascade ;
create table customer(
    customerID smallserial primary key,
	firstname varchar(50),
	lastname varchar(50),
	phone varchar(10),
	email varchar(50)
);


drop table tabletop cascade ;
create table tabletop(
	tableID smallserial primary key,
	tablesize smallint,
	tableside varchar(10) check (tableside in ('Upper', 'Lower')),
	available bool default true
);


drop table reservation;
create table reservation(
	reservationID smallserial primary key,
	customerID smallint references customer(customerID) on delete cascade,
	tableID smallint references tabletop(tableID) on delete cascade,
	reservationdate bigint
);



insert into customer values(default, 'Man', 'Woman', 1111112223,'man&woman@worlddomination.com' );
insert into customer values(default, 'child', 'baby', 114526223,'man&woman@worlddomination.com' );

insert into tabletop  values(default, 3, 'Upper', true);
update tabletop set available=true where tableid=1;

insert into reservation  values(default, 1, 1, 1643000400000);
insert into reservation  values(default, 2, 3, 1643000400000);
insert into reservation  values(default, 2, 2, 1643000400000);

select * from customer where customerid=1 and firstname='Man' and lastname='Woman'and phone='1111112223' and email='man&woman@worlddomination.com';