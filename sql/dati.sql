-- Fornitori
INSERT INTO fornitore (nome, telefono, email) VALUES
('Esseti Informatica Srl', '081-1234567', 'info@essetinformatica.it'),
('MegaHardware Distribuzione', '02-9876543', 'ordini@megahardware.it'),
('TechParts Napoli', '081-5556677', 'vendite@techparts.it');

-- Processori
INSERT INTO componente (nome, prezzo, quantita, tipo, fornitore_id) VALUES
('Intel Core i5-12400F', 149.90, 25, 'PROCESSORE', 1),
('AMD Ryzen 5 7600',     209.00, 18, 'PROCESSORE', 2),
('Intel Core i7-13700K', 389.50, 10, 'PROCESSORE', 1);

INSERT INTO processore (componente_id, numero_core, socket) VALUES
(1, 6,  'LGA1700'),
(2, 6,  'AM5'),
(3, 16, 'LGA1700');

-- Schede Video
INSERT INTO componente (nome, prezzo, quantita, tipo, fornitore_id) VALUES
('NVIDIA RTX 5070',        649.00, 8,  'SCHEDA_VIDEO', 2),
('NVIDIA RTX 4060',        329.00, 20, 'SCHEDA_VIDEO', 2),
('AMD Radeon RX 7800 XT',  549.00, 12, 'SCHEDA_VIDEO', 3);

INSERT INTO scheda_video (componente_id, memoria_vram, chipset) VALUES
(4, 12, 'AD104'),
(5, 8,  'AD107'),
(6, 16, 'Navi 32');

-- Memorie RAM
INSERT INTO componente (nome, prezzo, quantita, tipo, fornitore_id) VALUES
('Corsair Vengeance 16GB DDR5',   64.90, 40, 'MEMORIA_RAM', 3),
('Kingston Fury Beast 32GB DDR4', 89.90, 30, 'MEMORIA_RAM', 1),
('G.Skill Trident Z5 32GB DDR5', 139.90, 15, 'MEMORIA_RAM', 3);

INSERT INTO memoria_ram (componente_id, capacita_gb, tipo_memoria) VALUES
(7, 16, 'DDR5'),
(8, 32, 'DDR4'),
(9, 32, 'DDR5');