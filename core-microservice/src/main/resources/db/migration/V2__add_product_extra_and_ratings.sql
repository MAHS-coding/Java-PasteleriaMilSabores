-- Flyway migration: add extra product columns and ratings table

-- Use simple ADD statements (avoid dialect-specific DEFAULT NULL)
ALTER TABLE productos ADD codigo VARCHAR(50);
ALTER TABLE productos ADD imagen VARCHAR(255);
ALTER TABLE productos ADD stock_critico INT;

CREATE TABLE IF NOT EXISTS ratings (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  producto_id BIGINT NOT NULL,
  userEmail VARCHAR(255) NOT NULL,
  userName VARCHAR(255),
  stars INT NOT NULL,
  comment TEXT,
  fecha DATETIME,
  CONSTRAINT fk_ratings_producto FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
);
