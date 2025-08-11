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
-- Insert sample data
insert into invoices (date, customer, room_id, unitPrice, hour, day, type) values
('2023-01-01 10:00:00', 'John Doe', '101', 100.00, 5, null, 'hourly'),
('2023-01-02 11:00:00', 'Jane Smith', '102', 200.00, null, 3, 'daily'),
('2023-01-03 12:00:00', 'Alice Johnson', '103', 150.00, 8, null, 'hourly'),
('2023-01-04 13:00:00', 'Bob Brown', '104', 250.00, null, 10, 'daily');
go
select * from invoices;
go