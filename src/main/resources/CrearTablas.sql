
DROP SCHEMA IF EXISTS hogar_arbol; 

CREATE USER '123'@'localhost' IDENTIFIED BY '456';
GRANT ALL PRIVILEGES ON hogar_arbol.* TO '123'@'localhost';
FLUSH PRIVILEGES;

CREATE SCHEMA hogar_arbol;
USE hogar_arbol;

-- Tabla: rol
-- Propósito: Almacena los roles de los usuarios (ej. ADMIN, USUARIO)
CREATE TABLE rol (
  id_rol INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (id_rol)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Tabla: usuario
-- Propósito: Almacena información de los usuarios, incluyendo credenciales y datos personales
CREATE TABLE usuario (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  cedula VARCHAR(20) NOT NULL UNIQUE, 
  password VARCHAR(512) NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  apellidos VARCHAR(80) NOT NULL,
  telefono VARCHAR(15),
  padecimiento VARCHAR(255), 
  activo BOOLEAN DEFAULT TRUE,
  id_rol INT NOT NULL,
  PRIMARY KEY (id_usuario),
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Tabla: taller
-- Propósito: Almacena información de los talleres ofrecidos
CREATE TABLE taller (
  id_taller INT NOT NULL AUTO_INCREMENT,
  codigo VARCHAR(10) NOT NULL UNIQUE,
  nombre VARCHAR(100) NOT NULL,
  descripcion VARCHAR(255),
  horario VARCHAR(100),
  icono VARCHAR(50),
  precio DECIMAL(10,2) NOT NULL,
  activo BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (id_taller)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Tabla: carrito
-- Propósito: Representa el carrito de compras de un usuario para talleres seleccionados
CREATE TABLE carrito (
  id_carrito INT NOT NULL AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_carrito),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Tabla: carrito_item
-- Propósito: Almacena los talleres individuales añadidos al carrito de un usuario (tiempo real
CREATE TABLE carrito_item (
  id_carrito_item INT NOT NULL AUTO_INCREMENT,
  id_carrito INT NOT NULL,
  id_taller INT NOT NULL,
  cantidad INT DEFAULT 1,
  PRIMARY KEY (id_carrito_item),
  FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito),
  FOREIGN KEY (id_taller) REFERENCES taller(id_taller)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Tabla: factura_taller
-- Propósito: Almacena las facturas de las compras de talleres
CREATE TABLE factura_taller (
  id_factura_taller INT NOT NULL AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(10,2),
  estado VARCHAR(20) DEFAULT 'PAGADO',
  PRIMARY KEY (id_factura_taller),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Tabla: reserva_taller
-- Propósito: Almacena las reservas de talleres asociadas a una factura
CREATE TABLE reserva_taller (
  id_reserva_taller INT NOT NULL AUTO_INCREMENT,
  id_factura_taller INT NOT NULL,
  id_taller INT NOT NULL,
  nombre_taller VARCHAR(100),
  horario VARCHAR(100),
  precio DECIMAL(10,2),
  cantidad INT DEFAULT 1,
  codigo_participacion VARCHAR(32),
  fecha_programada DATE,
  PRIMARY KEY (id_reserva_taller),
  FOREIGN KEY (id_factura_taller) REFERENCES factura_taller(id_factura_taller),
  FOREIGN KEY (id_taller) REFERENCES taller(id_taller)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Inserta roles iniciales (ADMIN y USUARIO)
INSERT INTO rol (nombre) VALUES ('ADMIN'), ('USUARIO');

-- Inserta usuarios iniciales (administradores y usuarios regulares)
INSERT INTO usuario (cedula, password, nombre, apellidos, telefono, padecimiento, id_rol) VALUES
('AdminT', '$2a$10$kfKToxrwe8t4vLgX0Ongg.p9r/qTKWkzpSYjYhMuM1hT5UojH3ySm', 'Tyler', 'Morales Muir', '8569-7170', NULL, 1),
('AdminB', '$2a$10$z.SXIzlHBnSphMlSubcfYeDBGoC4x2cbvmVMyZBtMpDsFKy6AFK9S', 'Brenda', 'Losilla Sanchez', '7012-2206', NULL, 1),
('AdminY', '$2a$10$HhQ.9jque1NQWfMlQGMzLeb05yqNkVURjjN9fMv0xn4WxtyjlUo5K', 'Yong', 'Tao Chen', '8585-9889', NULL, 1),
('AdminJ', '$2a$10$aD9APji5Zisg9ynA2peQAuYyUDoNQQUMr7LtIGTWQ0yafi/JKSkzm', 'Jefferson', 'Vargas Reynosa', '6384-2174', NULL, 1),
('706540621', '$2a$10$y8Vp3mxMzROoG2UYPQF32e7lsary34S7wHO1tds94NRi99X5Vo.UK', 'Juan', 'Perez Mora', '8564-9642', 'Cáncer de Páncreas', 2),
('105620154', '$2a$10$xad.M8Bai8vbPks4xO74heYUDCJjr9ETxdpaux0PymvZXEhk8c196', 'Fabiola', 'Ureña Urbina', '6436-1463', 'Alergias Leves', 2),
('605350125', '$2a$10$YuTIEkfRydiq./pg.5a.B.BMjknvPd7nj.sr3bm7RSfGAjJ8Z66cG', 'Luis', 'Garcia Garcia', '7562-4821', 'Fiebres', 2),
('708240396', '$2a$10$uAzTwuLlWKji61EbaSeB4uz9E.T0fjFffXk0LcCMLAnatUztfW/Rq', 'Ana', 'Rodriguez Perez', '6891-2345', 'Hipertensión', 2),
('309870214', '$2a$10$l6u/L.tM3eAdrBVFIACKcOZDq/jTofbZksmFYCgvmU1N1K5wfqNxW', 'Carlos', 'Mendez Lopez', '8321-5678', 'Diabetes', 2),
('502140987', '$2a$10$3av5dx5SlLljBWsaxQnpVewg.OGKeNCAhKpibfi1TslosUDxXwzyC', 'Maria', 'Sanchez Castro', '7412-8963', 'Asma', 2),
('406780321', '$2a$10$qJdvFrzmAVr6IPoukSLgfuQ813cp40VkfUPdh6.0Vt/sCzs5w7mtS', 'Jose', 'Ramirez Torres', '6789-1234', 'Migrañas', 2),
('807650432', '$2a$10$lCDH6CpfG5DUuBozCpTXHehOvx/qGTi9SXZrCcwgAjQIqZuDRZFlS', 'Laura', 'Vega Morales', '8543-2109', 'Alergias', 2),
('204560789', '$2a$10$hfAwto7Qygp1d0HCY5Ftu.Q/mcorQk4sI5lRxtfyse9tiSjiWTCCe', 'Pedro', 'Cruz Fernandez', '7234-5890', 'Artritis', 2),
('901230547', '$2a$10$URaKwN7yWIPWoeCGMMCOQ..JEh2oYlC2KJY.7kQGRDqKnO9GIh6Wy', 'Sofia', 'Diaz Ruiz', '6954-3218', 'Gastritis', 2);

-- Inserta talleres iniciales con sus detalles
INSERT INTO taller (codigo, nombre, descripcion, horario, icono, precio, activo) VALUES
('001', 'Taller de pintura', 'Explora tu lado artístico con técnicas básicas.', 'Lunes, 10:00 am – 11:30 am', 'fa-palette', 9000.00, TRUE),
('002', 'Taller de yoga', 'Mejora tu flexibilidad y reduce el estrés.', 'Martes, 9:00 am – 10:00 am', 'fa-person-walking', 5000.00, TRUE),
('003', 'Taller de música', 'Sesiones grupales con instrumentos, canto y bailes.', 'Miércoles, 3:00 pm – 4:30 pm', 'fa-music', 15000.00, TRUE),
('004', 'Taller de memoria', 'Ejercita tu mente con juegos y actividades cognitivas.', 'Jueves, 10:00 am – 11:30 am', 'fa-brain', 15000.00, TRUE),
('005', 'Ejercicio', 'Mantente activo con ejercicios de bajo impacto en personas mayores.', 'Viernes, 8:30 am – 11:00 am', 'fa-dumbbell', 10000.00, TRUE),
('006', 'Taller de lectura', 'Comparte historias y participa en discusiones.', 'Sábado, 1:00 pm – 3:00 pm', 'fa-book-open', 15000.00, TRUE);


