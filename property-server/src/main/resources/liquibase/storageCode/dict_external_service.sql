create table dict_external_service
(
    id                 varchar2(20) not null,
    constraint dict_external_service_pk primary key (id)
        using index tablespace jp_idx,
    deleted_at         date,
    date_create        timestamp,
    reprocessing_count number
)
/
comment on table dict_external_service
    is 'Справочник внешних сервисов @Editable@'
/
comment on column dict_external_service.id
    is 'Идентификатор'
/
comment on column dict_external_service.deleted_at
    is 'Признак уделения'
/
comment on column dict_external_service.date_create
    is 'Дата создания'
/
comment on column dict_external_service.reprocessing_count
    is 'количество попыток репроцессинга'
/

