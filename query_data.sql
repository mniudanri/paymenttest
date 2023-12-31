-- separate sql insert to avoid data overwrite and will be always eptied when start application

INSERT INTO 
    payment (amount, payment_type_id, date, customer_id)
VALUES
    (13000, 1, '2023-08-28', 1),
    (10000, 1, '2023-08-27', 1),
    (120, 1, '2023-08-26', 1),
    (8820, 1, '2023-08-28', 1),
    (3000, 1, '2023-08-27', 1),
    (2000, 1, '2023-08-26', 1),
    (13000, 1, '2023-08-28', 1),
    (1200, 1, '2023-08-27', 1),
    (4500, 1, '2023-08-26', 1),
    (89000000, 1, '2023-08-28', 1),
    (67850, 1, '2023-08-27', 1),
    (13000, 1, '2023-08-28', 2),
    (10000, 1, '2023-08-27', 2),
    (120, 1, '2023-08-26', 2),
    (8820, 1, '2023-08-28', 2),
    (3000, 1, '2023-08-27', 2),
    (2000, 1, '2023-08-26', 2),
    (13000, 1, '2023-08-28', 2),
    (1200, 1, '2023-08-27', 2),
    (4500, 1, '2023-08-26', 2),
    (89000000, 1, '2023-08-28', 2),
    (67850, 1, '2023-08-27', 2),
    (13000, 1, '2023-08-28', 3),
    (10000, 1, '2023-08-27', 3),
    (120, 1, '2023-08-26', 3),
    (8820, 1, '2023-08-28', 3),
    (3000, 1, '2023-08-27', 3),
    (2000, 1, '2023-08-26', 3),
    (13000, 1, '2023-08-28', 3),
    (1200, 1, '2023-08-27', 3),
    (4500, 1, '2023-08-26', 3),
    (89000000, 1, '2023-08-28', 3),
    (67850, 1, '2023-08-27', 3);

INSERT INTO 
    payment_type (type_name)
VALUES
    ('BALANCE'),
    ('DEBIT_CARD'),
    ('CREDIT_CARD'),
    ('COD'),
    ('COUPON');


INSERT INTO 
    inventory (item_name, quantity, price)
VALUES
    ('Man Shoes', 1000, 500000),
    ('Woman Shoes', 1000, 230000),
    ('Man Backpack', 1000, 540000),
    ('Woman Backpack', 1000, 530000),
    ('Man Shirt', 1000, 45000),
    ('Woman Shirt', 1000, 100000),
    ('Man Sandals', 1000, 15000),
    ('Woman Sandals', 1000, 10000),
    ('Man Pants', 1000, 150000),
    ('Woman Pants', 1000, 350000),
    ('Man Hat', 1000, 75000),
    ('Woman Hat', 1000, 90000),
    ('Chocolatos', 1000, 6000),
    ('Bang Bang Choco', 1000, 3000),
    ('Chewy Choco', 1000, 3000),
    ('Chocolate Bar', 1000, 15000),
    ('Choco mints', 1000, 2500);
