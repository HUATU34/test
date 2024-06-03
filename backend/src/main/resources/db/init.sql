drop table if exists `Administrator`;
drop table if exists `Cashier`;
drop table if exists `Card`;
drop table if exists `Demand_deposit`;
drop table if exists `Fixed_deposit`;
drop table if exists `Statement`;
drop table if exists `rate`;
drop table if exists `Property`;
drop table if exists `Account`;

create table `Administrator` (
    `id` bigint(10)   zerofill not null auto_increment ,
    `username` varchar(63) not null,
    `password` varchar(255) not null,
    `salt` varchar(255) not null,
    primary key (`id`)
)  AUTO_INCREMENT=2000000000 engine=innodb charset=utf8mb4;

create table `Cashier` (
    `id` bigint(10)   zerofill not null auto_increment ,
    `username` varchar(63) not null,
    `password` varchar(255) not null,
    `authority` int not null default 0,
    `salt` varchar(255) not null,
     primary key (`id`),
     unique (`username`, `password`),
    check(authority in (0,1,2,3))
)  AUTO_INCREMENT=1000000000 engine=innodb charset=utf8mb4;

create table `Account` (
    `id` bigint(12)   zerofill not null auto_increment ,
    `name` varchar(63) not null,
    `phonenumber` varchar(12) not null,
    `citizenid` varchar(18) not null,
    `status` int not null default 1,
    `card_id` bigint(14)   zerofill not null,
    `password` varchar(255) not null,
    `salt` varchar(255) not null,
    primary key (`id`),
    unique(card_id),
    check ( `status` in (1,2,3))
) engine=innodb charset=utf8mb4;

create table `Card` (
    `id` bigint(16)   zerofill not null auto_increment ,
    `type` int not null,
    `accountid`  bigint(12)   zerofill not null,
    primary key (`id`),
    foreign key (accountid)references Account(id) on delete cascade on update cascade,
    check (`type` in (1,2) )
) AUTO_INCREMENT=1000000000000000 engine=innodb charset=utf8mb4;

create table `Property` (
    `id` bigint   not null auto_increment,
    `accountid` bigint(12)   zerofill not null,
    `type` int not null,
    primary key (`id`),
    foreign key (accountid)references Account(id) on delete cascade on update cascade,
    check ( `type` in (1, 2) )
) engine=innodb charset=utf8mb4;

create table `Demand_deposit` (
    `propertyid` bigint   not null ,
    `accountid`  bigint(12)   zerofill not null,
    `amount` decimal(15,2) not null default 0.00,
    `date` bigint not null,
    `base` decimal(15,2) not null default 0.00,
    check(amount >= 0),
    foreign key (`propertyid`) references `Property`(`id`) on delete cascade on update cascade,
    foreign key (`accountid`) references `Account`(`id`) on delete cascade on update cascade
) engine=innodb charset=utf8mb4;

create table `Fixed_deposit` (
    `propertyid` bigint   not null ,
    `accountid`  bigint(12)   zerofill not null,
    `amount` decimal(15,2) not null default 0.00,
    `date` bigint not null,
    `length` int not null,
    `interestrate` decimal(4,2) not null default 0.00,
    `autocontinue` bool default true,
    check(amount >= 0),
    foreign key (`propertyid`) references `Property`(`id`) on delete cascade on update cascade,
    foreign key (`accountid`) references `Account`(`id`) on delete cascade on update cascade
) engine=innodb charset=utf8mb4;

create table `Statement` (
    `accountid`  bigint(12)   zerofill not null,
    `amount` decimal(15,2) not null default 0.00,
    `date` bigint not null,
    `type` int not null default 0,
    `traced` bigint not null,
    check ( `type` in (1,2,3,4,5,6,7,8) ),
    foreign key (`accountid`) references `Account`(`id`) on delete cascade on update cascade
) engine=innodb charset=utf8mb4;

create table `Rate`(
    `demand_rate` decimal(3,2),
    `_3month_rate` decimal(3,2),
    `_6month_rate` decimal(3,2),
    `_1year_rate`decimal(3,2),
    `_2year_rate`decimal(3,2),
    `_3year_rate`decimal(3,2),
    `_5year_rate`decimal(3,2)
)engine=innodb charset=utf8mb4;