CREATE TABLE `clientes` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `nome` varchar(255) NOT NULL,
  `sobrenome` varchar(255) NOT NULL,
  `situacao` boolean NOT NULL
);


INSERT INTO `clientes` (`id`, `nome`, `sobrenome`, `situacao`) VALUES
(1, 'Anya', 'Taylor-Joy', 1),
(2, 'Damon', 'Salvatore', 1),
(3, 'Chris', 'Cornell', 1),
(4, 'Hermione', 'Granger', 1);


CREATE TABLE `produtos` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `nome` varchar(255) NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `situacao` boolean NOT NULL,
  `quantidade` int DEFAULT NULL
);


INSERT INTO `produtos` (`id`, `nome`, `valor`, `descricao`, `situacao`, `quantidade`) VALUES
(1, 'Mouse', '58.80', 'Bluetooth sem fio preto', 1, 85),
(2, 'Teclado', '69.80', 'Com fio barnco e rosa', 1, 70),
(3, 'Headphone', '63.20', 'Com fio preto e branco', 1, 85),
(4, 'Earphone', '20.90', 'Bluetooth sem fio branco', 1, 100),
(5, 'Mesa Digitalizadora', '6.80', 'One byWacom preto e vermelho', 0, 100);

CREATE TABLE `pedidos` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `pagamento` varchar(255) NOT NULL,
  `estado` varchar(255) NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date NOT NULL,
  `situacao` boolean NOT NULL,
  `id_cliente` int NOT NULL,
  `total_pedido` decimal(10,2) NOT NULL,
  FOREIGN KEY (`id_cliente`) REFERENCES `clientes`(`id`)
);

CREATE TABLE `itens` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `id_pedido` int NOT NULL,
  `id_produto` int NOT NULL,
  `quantidade` decimal(10,2) NOT NULL,
  `total_item` decimal(10,2) NOT NULL,
  `situacao` boolean NOT NULL,
  FOREIGN KEY (`id_pedido`) REFERENCES `pedidos`(`id`),
  FOREIGN KEY (`id_produto`) REFERENCES `produto`(`id`)
);

