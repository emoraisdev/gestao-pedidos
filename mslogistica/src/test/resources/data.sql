INSERT INTO endereco (id, bairro,cep,cidade,estado,numero,pais,rua) VALUES
    (101, 'Cruzeiro','12345-321','Curitiba','PR','121','Brasil','Rua XV'),
    (102, 'Afonso Pena','83040-010','São José dos Pinhais','PR','121','Brasil','Rua Acre');

INSERT INTO entregador (id, idade,nome,endereco_id) VALUES
    (101, 35,'Marco Silva',101),
    (102, 25,'Joana Pires',102);

INSERT INTO entrega (id,latitude,longitude,pedido_id,status,destino_id,entregador_id,origem_id) VALUES
    (101, NULL,NULL,2,0,102,101,101),
    (102, NULL,NULL,3,0,101,101,102);
