insert into cliente values (1, '1990-09-01', "2022-09-21", "exemple@gmail.com", "Gabriel", "1234", "119123456");



-- junta tabela cliente e usuario conectando-os por id e exibindo seu nome;
select c.nome, c.id from cliente as c inner join usuario as u
on c.id = u.cliente_id;

insert into usuario(cpf, senha, id, cliente_id) values ("123456", "314314", 1, 1);
insert into cliente values(1, "2004-12-04", null, "Gabriel", "12345", "119123456", "123456", 1);


-- Exemplo de Update:

-- update Cliente c set c.telefone = "9113691215" where c.id = 6;

-------------------  Atualizado (Código ABAIXO)    -----------------------------------

create database clinicatcc;
use clinicatcc;

show tables;

select * from usuario;
select * from cliente;


select c.nome, c.id from cliente as c inner join usuario as u
on c.id = u.cliente_id;

insert into usuario values ("12345678910", "12345");
insert into cliente values(1, "2004-12-04", null, "Gabriel", "119123456", "12345678910");

select c.id as "id usuario",c.nome, c.usuario_cpf from cliente c inner join usuario u
on c.usuario_cpf = u.cpf;

update Cliente c set c.telefone = "9113691215" where c.id = 6;

drop table usuario;
drop table cliente;
drop database clinicatcc;

describe cliente;
describe usuario;