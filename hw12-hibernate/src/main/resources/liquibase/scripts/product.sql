

CREATE TABLE products
(
    id      BIGINT PRIMARY KEY,
    product VARCHAR(255),
    price   INT
);

CREATE TABLE persons
(
    id     BIGINT PRIMARY KEY,
    person VARCHAR(255)
--     id_person BIGINT
--     foreign key (id_person) references persons (id)
);

insert into persons (id, person)
values (1, 'Иван Иванов'),
       (2,'Семен Егоров'),
       (3,'Ольга Петрова'),
       (4,'Александр Матросов'),
       (5,'Наташа Фомина'),
       (6,'Евгений Матвеев'),
       (7,'Артем Яблоков'),
       (8,'Василий Путилин'),
       (9,'Ольга Петрова'),
       (10,'Екатерина Метелкина');

insert into products (id,product, price)
values (1,'хлеб', 46),
       (2,'молоко', 52),
       (3,'минтай', 340),
       (4,'пельмени', 350),
       (5,'сосиськи', 210),
       (6,'картофель', 42),
       (7,'макароны', 89),
       (8,'гречка', 115),
       (9,'сахар', 78),
       (10,'масло сливочное', 165),
       (11,'масло подсолнечное', 205)