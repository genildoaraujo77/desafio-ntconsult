create table voto (
id bigint not null auto_increment,
voto boolean not null,
pauta_id bigint not null,
associado_id bigint not null,
sessao_votacao_id bigint not null,
data_envio datetime not null,

primary key (id)
);

alter table voto add constraint fk_voto_pauta foreign key(pauta_id) references pauta(id);
alter table voto add constraint fk_voto_associado foreign key(associado_id) references associado(id);
alter table voto add constraint fk_voto_sessao_votacao foreign key (sessao_votacao_id) references sessao_votacao (id);