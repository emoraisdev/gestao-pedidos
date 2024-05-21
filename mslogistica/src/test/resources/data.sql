INSERT INTO endereco (id, bairro,cep,cidade,estado,numero,pais,rua) VALUES
    (101, 'Cruzeiro','12345-321','Curitiba','PR','121','Brasil','Rua XV'),
    (102, 'Afonso Pena','83040-010','São José dos Pinhais','PR','121','Brasil','Rua Acre');

INSERT INTO entregador (id, idade,nome,endereco_id) VALUES
    (101, 35,'Marco Silva',101),
    (102, 25,'Joana Pires',102);

INSERT INTO entrega (id,latitude,longitude,pedido_id,status,destino_id,entregador_id) VALUES
    (101, NULL,NULL,'27a7755d-9dbc-4a19-b868-13b3e3c639de',0,102,101),
    (102, NULL,NULL,'1ab77397-feab-4737-9b5f-242bc78764b1',0,101,101);
