-- Flyway migration: add run and fecha_nacimiento columns and direcciones table

-- Add columns separately for portability
ALTER TABLE usuarios ADD run VARCHAR(20);
ALTER TABLE usuarios ADD fecha_nacimiento DATE;

CREATE TABLE IF NOT EXISTS direcciones (
  id VARCHAR(200) PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  direccion VARCHAR(500),
  region VARCHAR(200),
  comuna VARCHAR(200),
  CONSTRAINT fk_direcciones_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);
