create table accounts (
                             id varchar(36) primary key,
                             client_id varchar(10),
                             account_id varchar(12),
                             balance int,
                             is_blocked boolean
);


create table transfers (
                           id varchar(36) primary key,
                           source_account_id varchar(36),
                           target_account_id varchar(36),
                           amount int,
                           message varchar(255),
                           foreign key (source_account_id) references accounts(id),
                           foreign key (target_account_id) references accounts(id)
);

insert into accounts (id, client_id, account_id, balance, is_blocked) values ('552077e6-2caa-47fe-89f3-168ac81a94b1', '0000000001', '000000000111', 500, false),
                                                                               ('d2bd47e4-c3ca-4b83-8ca8-209d158330b1', '0000000002', '000000000112', 1000, false );


insert into transfers (id,  source_account_id, target_account_id, amount, message) values  ('bde76ffa-f133-4c23-9bca-03618b2a94b2', '552077e6-2caa-47fe-89f3-168ac81a94b1', 'd2bd47e4-c3ca-4b83-8ca8-209d158330b1', 100, 'Тестовый перевод'),
                                                                                           ('32ebb2eb-ed35-4baa-b500-b7f6535e4c88', 'd2bd47e4-c3ca-4b83-8ca8-209d158330b1', '552077e6-2caa-47fe-89f3-168ac81a94b1', 50, 'Обратный тестовый перевод');