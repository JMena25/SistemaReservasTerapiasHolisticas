CREATE DATABASE holisticasdb1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE holisticasdb1;

CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  rol ENUM('ADMIN','CLIENTE') NOT NULL
);

INSERT INTO usuarios (id, username, password, rol) VALUES
(1, 'admin10', 'admin', 'ADMIN'),
(2, 'admin20', 'admin', 'ADMIN'),
(3, 'cliente1', 'abcd', 'CLIENTE'),
(4, 'cliente2', 'abcd', 'CLIENTE'),
(5, 'cliente3', 'abcd', 'CLIENTE'),
(6, 'cliente4', 'abcd', 'CLIENTE'),
(7, 'cliente5', 'abcd', 'CLIENTE'),
(8, 'cliente6', 'abcd', 'CLIENTE'),
(9, 'cliente7', 'abcd', 'CLIENTE'),
(10, 'cliente8', 'abcd', 'CLIENTE');


CREATE TABLE terapistas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL
);

INSERT INTO terapistas (id, nombre, especialidad) VALUES
(1, 'Ana López', 'Reiki'),
(2, 'Carlos Pérez', 'Masajes'),
(3, 'María Torres', 'Aromaterapia'),
(4, 'Luis Gómez', 'Yoga'),
(5, 'Sofía Herrera', 'Meditación'),
(6, 'Pedro Díaz', 'Acupuntura'),
(7, 'Laura Sánchez', 'Reflexología'),
(8, 'Andrés Castro', 'Musicoterapia'),
(9, 'Valeria Ortiz', 'Shiatsu'),
(10, 'Jorge Ramírez', 'Terapia Holística');

CREATE TABLE terapias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

INSERT INTO terapias (id, nombre, descripcion) VALUES
(1, 'Reiki', 'Sanación energética'),
(2, 'Masaje relajante', 'Masaje corporal completo'),
(3, 'Aromaterapia', 'Uso de aceites esenciales'),
(4, 'Yoga', 'Clases de yoga'),
(5, 'Meditación guiada', 'Sesiones de meditación'),
(6, 'Acupuntura', 'Terapia con agujas'),
(7, 'Reflexología', 'Masaje en pies y manos'),
(8, 'Musicoterapia', 'Uso de música para sanar'),
(9, 'Shiatsu', 'Masaje japonés'),
(10, 'Terapia Holística', 'Tratamiento integral cuerpo-mente');

CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    terapista_id INT NOT NULL,
    terapia_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,

    FOREIGN KEY (cliente_id) REFERENCES usuarios(id),
    FOREIGN KEY (terapista_id) REFERENCES terapistas(id),
    FOREIGN KEY (terapia_id) REFERENCES terapias(id)
);

INSERT INTO reservas (id, cliente_id, terapista_id, terapia_id, fecha, hora) VALUES
(1, 3, 1, 1, '2026-01-15', '10:00'),
(2, 4, 2, 2, '2026-01-15', '11:00'),
(3, 5, 3, 3, '2026-01-16', '09:00'),
(4, 6, 4, 4, '2026-01-16', '14:00'),
(5, 7, 5, 5, '2026-01-17', '15:00'),
(6, 8, 6, 6, '2026-01-17', '16:00'),
(7, 9, 7, 7, '2026-01-18', '10:00'),
(8, 10, 8, 8, '2026-01-18', '11:00'),
(9, 3, 9, 9, '2026-01-19', '12:00'),
(10, 4, 10, 10, '2026-01-19', '13:00');

CREATE VIEW v_reservas_completas AS
SELECT r.id, u.username AS cliente, t.nombre AS terapia, te.nombre AS terapista,
       r.fecha, r.hora
FROM reservas r
JOIN usuarios u ON r.cliente_id = u.id
JOIN terapias t ON r.terapia_id = t.id
JOIN terapistas te ON r.terapista_id = te.id;

CREATE VIEW v_admins AS
SELECT id, username
FROM usuarios
WHERE rol = 'ADMIN';

DELIMITER //
CREATE PROCEDURE sp_insert_usuario(
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(100),
    IN p_rol ENUM('ADMIN','CLIENTE')
)
BEGIN
    INSERT INTO usuarios (username, password, rol)
    VALUES (p_username, p_password, p_rol);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_get_usuarios()
BEGIN
    SELECT id, username, rol
    FROM usuarios;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_update_usuario(
    IN p_id INT,
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(100),
    IN p_rol ENUM('ADMIN','CLIENTE')
)
BEGIN
    UPDATE usuarios
    SET username = p_username,
        password = p_password,
        rol = p_rol
    WHERE id = p_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_delete_usuario(
    IN p_id INT
)
BEGIN
    DELETE FROM usuarios WHERE id = p_id;
END //
DELIMITER ;

select * from usuarios;
select * from terapias;
select * from reservas;
select * from terapistas;

DELETE FROM terapias WHERE idterapias = 7;