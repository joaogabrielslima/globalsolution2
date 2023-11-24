use GS2;

create table criança(
                        criança_id int unique primary key,
                        nome varchar(100),
                        aniversario date,
                        altura float,
                        peso float,
                        emocional enum('Muito feliz', 'Feliz', 'Neutro', 'Chateado', 'Triste')
);

create table responsavel(
                            responsavel_id int unique primary key,
                            email varchar(100) unique,
                            senha varchar(100),
                            nome varchar(100),
                            criança_id int,
                            foreign key (criança_id) references criança(criança_id)
);
