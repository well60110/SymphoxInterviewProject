DROP TABLE IF EXISTS Employee;

create table Employee
(
   eId  bigint auto_increment primary key,
   eName nvarchar(50) not null,
   departmentId varchar(4) not null,
   gender nvarchar(1) not null,
   phone varchar(20),
   address nvarchar(200),
   age varchar(3),
   createTime varchar(20) not null,
   lastModifyTime varchar(20) not null,
   isLeaveType varchar(1) DEFAULT 'N' NOT NULL
);

DROP TABLE IF EXISTS Department;

create table Department
(
   dId varchar(2) not null primary key,
   dName nvarchar(50) not null,
   isDelType varchar(1) DEFAULT 'N' NOT NULL
);




ALTER TABLE Employee ADD FOREIGN KEY (departmentId)  REFERENCES Department(dId);