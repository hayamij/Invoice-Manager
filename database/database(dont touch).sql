use master;
go
drop database if exists invoice;
go
create database invoice;
go

drop table if exists invoices;
create table invoices (
    id int primary key not null IDENTITY(1,1),
    date datetime default CURRENT_TIMESTAMP,
    customer varchar(255),
    room_id varchar(255),
    unitPrice decimal(10, 2),
    hour int,
    day int,
    type varchar(50)
);
go
select * from invoices;
go