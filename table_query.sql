--Creation of schema and table

create schema Library;


create table BOOK
(Book_id char(10) primary key not null,
Title varchar(60));


create table BOOK_AUTHORS
(Book_id char(10) not null,
Author_name varchar(60) not null,
Fname varchar(60),
Minit varchar(60),
Lname varchar(60),
primary key(Book_id,Author_name),
foreign key (Book_id) references BOOK(Book_id));

create table LIBRARY_BRANCH
(Branch_id char(1) primary key not null,
Branch_name varchar(60),
Address varchar(100),
);

create table BOOK_COPIES
(Book_id char(10) not null,
Branch_id char(1) not null,
No_of_copies int,
primary key(Book_id,Branch_id),
foreign key (Book_id) references BOOK(Book_id),
foreign key (Branch_id) references LIBRARY_BRANCH(Branch_id)
);

create table BORROWER
(card_no CHAR(4) not null ,
Fname varchar(60),
Lname varchar(60),
Address varchar(200),
city varchar(10),
State char(2),
Phone char(14),
primary key(card_no)
);

create table BOOK_LOANS
(Loan_id int not null auto_increment,
Book_id char(10) not null,
Branch_id char(1) not null,
card_no CHAR(4) not null ,
Date_out DATE,
Due_date DATE,
Date_in DATE,
primary key(loan_id),
foreign key (Book_id) references BOOK(Book_id),	
foreign key (Branch_id) references LIBRARY_BRANCH(Branch_id),
foreign key (card_no) references BORROWER(card_no)
);

create table fine
(Loan_id int not null primary key,
Fine_amt float(20,2),
paid boolean default false,
foreign key (loan_id) references BOOK_LOANS(loan_id));

create view BOOK_COUNT AS 
select bc.book_id,bc.branch_id, bc.no_of_copies as Available_COUNT, count(bl.book_id) AS LOANED_COUNT, count(bl.book_id)+no_of_copies as TOTAL_COUNT
from book_copies bc left join book_loans bl on bc.book_id = bl.book_id and bc.branch_id=bl.branch_id
group by bc.book_id,bc.branch_id;

--Loading data into the tables

LOAD LOCAL DATA INFILE '/home/Anupama/Downloads/Anupama_project/book1.csv'
INTO TABLE BOOK 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

LOAD LOCAL DATA INFILE '/home/Anupama/Downloads/Anupama_project/book2.csv'
INTO TABLE BOOK_AUTHORS 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

LOAD LOCAL DATA INFILE '/home/Anupama/Downloads/Anupama_project/book3.csv'
INTO TABLE BOOK_COPIES 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

LOAD LOCAL DATA INFILE '/home/Anupama/Downloads/Anupama_project/book4.csv'
INTO TABLE BORROWER 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

LOAD LOCAL DATA INFILE '/home/Anupama/Downloads/Anupama_project/book5.csv'
INTO TABLE LIBRARY_BRANCH 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

--Commands for modifying the table

Alter table BOOK_AUTHORS drop column fname;
Alter table BOOK_AUTHORS drop column lname;
Alter table BOOK_AUTHORS drop column minit;
alter table book_authors add column Type int;




