INSERT INTO body_types (id, body_type) VALUES
(1, 'седан'),
(2, 'хетчбэк'),
(3, 'универсал'),
(4, 'кабриолет'),
(5, 'купе'),
(6, 'внедорожник'),
(7, 'фургон'),
(8, 'минивэн'),
(9, 'пикап'),
(10, 'микроавтобус');

INSERT INTO brands (id, brand_name) VALUES
(1, 'Audi'),
(2, 'BMW'),
(3, 'Ford'),
(4, 'Honda'),
(5, 'Hyundai'),
(6, 'Volkswagen'),
(7, 'ВАЗ (LADA)');

INSERT INTO models (model_name, brand_id) VALUES
('A3', 1),
('A4', 1),
('A5', 1),
('X3', 2),
('X5', 2),
('X6', 2),
('Focus', 3),
('Fusion', 3),
('Kuga', 3),
('Accord', 4),
('CR-V', 4),
('Civic', 4),
('Creta', 5),
('Elantra', 5),
('Genesis', 5),
('Golf', 6),
('Jetta', 6),
('Polo', 6),
('2112', 7),
('2113 Samara', 7),
('2114 Samara', 7);
