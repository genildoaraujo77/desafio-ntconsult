create table sessao_votacao (
id bigint not null auto_increment,
pauta_id bigint not null,
qtdvotos integer,
status varchar(20) not null,
data_abertura datetime not null,
data_finalizacao datetime,

primary key (id),
CONSTRAINT uc_pauta_id UNIQUE (pauta_id)
);

alter table sessao_votacao add constraint fk_sessao_votacao_pauta foreign key(pauta_id) references pauta(id);