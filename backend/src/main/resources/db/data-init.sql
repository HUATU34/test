delete from `Administrator`;
delete from `cashier`;
delete from `account`;
delete from `card`;
delete from `property`;
delete from `demand_deposit`;
delete from `fixed_deposit`;
delete from `statement`;
delete from `rate`;

insert into `administrator` (username, password, salt) values
('testadmin', '4739ee3bd29e4f415da8ba9298a087e0fdc9c61378420ba8fbbab298bd74c4df','123');

insert into `cashier`(username, password, authority,salt)values
('testcashier', '4739ee3bd29e4f415da8ba9298a087e0fdc9c61378420ba8fbbab298bd74c4df',1,'123');

insert into `account`(id,name, phonenumber, citizenid ,card_id, password, salt) values
(000000000001,'testaccount','131313131',210204199802011234,1000000000000000,'4739ee3bd29e4f415da8ba9298a087e0fdc9c61378420ba8fbbab298bd74c4df','123');

insert into `card`(id, type, accountid)values
(1000000000000000,1,000000000001);


Insert into `rate`values
(0.2,1.15,1.35,1.45,1.65,1.95,2.00);
