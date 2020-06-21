CREATE TABLE ordem_servico (
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	cliente_id BIGINT NOT NULL,
	descricao TEXT NOT NULL,
	preco DECIMAL (10,2) NOT NULL,
	status VARCHAR (20) NOT NULL,
	data_abertura DATETIME NOT NULL,
	data_finalizacao DATETIME

);

ALTER TABLE ordem_servico ADD CONSTRAINT fk_ordem_servico_cliente
FOREIGN KEY (cliente_id) REFERENCES cliente(id);