drop table if exists fina_stat_dto;
create table fina_stat_dto(
    id int AUTO_INCREMENT,
    crno varchar(32),
    biz_year varchar(8),
    enp_crtm_npf varchar(64),
    enp_tcpt_amt varchar(64),
    fncl_dcd_nm varchar(32),
    primary key (id)
);