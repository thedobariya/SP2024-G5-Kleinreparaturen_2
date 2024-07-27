
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Vihar ', 'Kheni', '2001-06-19');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Jane', 'Smith', '1985-10-25');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Michael', 'Johnson', '1978-03-08');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Emily', 'Williams', '1995-12-12');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('David', 'Brown', '1980-08-20');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Sarah', 'Jones', '1992-06-30');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Matthew', 'Taylor', '1975-04-18');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Jessica', 'Wilson', '1987-09-05');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Christopher', 'Miller', '1998-01-22');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('Amanda', 'Martinez', '1983-11-10');
INSERT INTO customer (first_name, last_name, date_of_birth) VALUES ('prit', 'dobariya', '2000-09-27');

-- Set initial availability for existing coworkers (optional)
UPDATE coworker SET available = TRUE;


-- SQL statements to insert data into the item table
-- Flickschusterei
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Leather Shoe', TRUE, 1, 'Heels replacement', 2);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Hiking Boots', FALSE, 2, 'Soles replacement', 3);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Dress Shoe', TRUE, 3, 'Stitching repair', 1);

-- Nähservice
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Winter Coat', TRUE, 1, 'Buttons replaced', 2);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Denim Jacket', FALSE, 2, 'Stitching repair', 3);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Kids Pants', TRUE, 3, 'Patch applied', 1);

-- Schlüsseldienst
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('House Key', TRUE, 1, 'Duplicate made', 2);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Office Key', FALSE, 2, 'Engraved nameplate', 3);

-- Textilservice
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Laundry Bag', TRUE, 1, 'Laundry service', 2);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Suit', FALSE, 2, 'Dry cleaning', 3);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Leather Jacket', TRUE, 3, 'Leather cleaning', 1);

-- Elektrowerkstatt
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Extension Cord', TRUE, 1, 'Cable replacement', 2);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Headphones', FALSE, 2, 'Cable soldering', 3);

-- Metallarbeiten
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Scissors', TRUE, 1, 'Sharpening', 2);
INSERT INTO Item (name, is_open, status, description, "condition") VALUES ('Kitchen Knife', FALSE, 2, 'Blade sharpening', 3);

INSERT INTO workingstation (name, price) VALUES ('Schuhreparatur-Station', 100.0);
INSERT INTO workingstation (name, price) VALUES ('Näh-Station', 110.0);
INSERT INTO workingstation (name, price) VALUES ('Schlüsseldienst-Station', 120.0);
INSERT INTO workingstation (name, price) VALUES ('Textilservice-Station', 130.0);
INSERT INTO workingstation (name, price) VALUES ('Elektrowerkstatt-Station', 140.0);
INSERT INTO workingstation (name, price) VALUES ('Metallarbeiten-Station', 150.0);
INSERT INTO workingstation (name, price) VALUES ('Lederbearbeitung-Station', 160.0);
INSERT INTO workingstation (name, price) VALUES ('Stoffbearbeitung-Station', 170.0);
INSERT INTO workingstation (name, price) VALUES ('Schlüsselkopier-Station', 180.0);
INSERT INTO workingstation (name, price) VALUES ('Wäscherei-Station', 190.0);

INSERT INTO coworker (name, hourly_rate) VALUES ('Alice', 20.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Bob', 22.5);
INSERT INTO coworker (name, hourly_rate) VALUES ('Charlie', 19.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Diana', 25.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Eve', 18.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Frank', 21.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Grace', 23.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Heidi', 20.5);
INSERT INTO coworker (name, hourly_rate) VALUES ('Ivan', 22.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Judy', 24.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Karl', 19.5);
INSERT INTO coworker (name, hourly_rate) VALUES ('Laura', 26.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Mallory', 21.5);
INSERT INTO coworker (name, hourly_rate) VALUES ('Niaj', 20.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Oscar', 23.5);
INSERT INTO coworker (name, hourly_rate) VALUES ('Peggy', 24.5);
INSERT INTO coworker (name, hourly_rate) VALUES ('Rupert', 18.5);
INSERT INTO coworker (name, hourly_rate) VALUES ('Sybil', 19.0);
INSERT INTO coworker (name, hourly_rate) VALUES ('Trent', 22.5);
INSERT INTO coworker (name, hourly_rate) VALUES ('Victor', 25.0);

-- FLICKSCHUSTEREI
INSERT INTO material (name, stock, price, unit) VALUES ('Nadeln', 100, 1.5, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Faden', 500, 2.0, 'Meter');
INSERT INTO material (name, stock, price, unit) VALUES ('Lederreste', 5, 30.0, 'Kilogramm');
INSERT INTO material (name, stock, price, unit) VALUES ('Schuhsohlen', 50, 5.0, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Schuhcreme', 30, 4.0, 'Liter');

-- NAEHSERVICE
INSERT INTO material (name, stock, price, unit) VALUES ('Stoff', 200, 5.0, 'Meter');
INSERT INTO material (name, stock, price, unit) VALUES ('Nähmaschinennadeln', 50, 1.0, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Reißverschlüsse', 100, 2.0, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Knöpfe', 2, 25.0, 'Kilogramm');
INSERT INTO material (name, stock, price, unit) VALUES ('Elastisches Band', 100, 1.5, 'Meter');

-- SCHLUESSELDIENST
INSERT INTO material (name, stock, price, unit) VALUES ('Schlüsselrohlinge', 200, 3.0, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Schmieröl', 20, 4.0, 'Liter');
INSERT INTO material (name, stock, price, unit) VALUES ('Schlüsselanhänger', 300, 0.5, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Schließzylinder', 50, 15.0, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Metallspäne', 10, 5.0, 'Kilogramm');

-- TEXTILSERVICE
INSERT INTO material (name, stock, price, unit) VALUES ('Waschmittel', 100, 6.0, 'Liter');
INSERT INTO material (name, stock, price, unit) VALUES ('Weichspüler', 80, 4.0, 'Liter');
INSERT INTO material (name, stock, price, unit) VALUES ('Bügeleisen', 10, 20.0, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Fleckenentferner', 50, 5.0, 'Liter');
INSERT INTO material (name, stock, price, unit) VALUES ('Wäschenetze', 100, 2.0, 'Stück');

-- ELEKTROWERKSTATT
INSERT INTO material (name, stock, price, unit) VALUES ('Kabel', 500, 2.0, 'Meter');
INSERT INTO material (name, stock, price, unit) VALUES ('Schrauben', 5, 20.0, 'Kilogramm');
INSERT INTO material (name, stock, price, unit) VALUES ('Elektronische Bauteile', 300, 5.0, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Lötkolben', 20, 25.0, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Isolierspray', 50, 3.0, 'Liter');

-- METALLARBEITEN
INSERT INTO material (name, stock, price, unit) VALUES ('Metallstangen', 100, 8.0, 'Meter');
INSERT INTO material (name, stock, price, unit) VALUES ('Schleifpapier', 200, 2.5, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Lötmaterial', 50, 10.0, 'Kilogramm');
INSERT INTO material (name, stock, price, unit) VALUES ('Schweißelektroden', 500, 0.5, 'Stück');
INSERT INTO material (name, stock, price, unit) VALUES ('Kühlmittel', 30, 20.0, 'Liter');