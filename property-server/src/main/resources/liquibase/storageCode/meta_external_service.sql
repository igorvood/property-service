create table meta_external_service
(
    id                varchar2(64)  not null,
    constraint meta_external_service_pk primary key (id)
        using index tablespace jp_idx,
    name              varchar2(255) not null,
    global_timeout    number        not null,
    disabled_at       date,
    date_create       timestamp(6),
    reprocessing_bean varchar2(255)
)
/
comment on table meta_external_service
    is 'Справочник различных сервисов для запуска из во флоу @Editable@'
/

comment on column meta_external_service.id
    is 'Идетнтификатор'
/

comment on column meta_external_service.name
    is 'Наименование'
/

comment on column meta_external_service.global_timeout
    is 'таймаут, сколько ждем ответа, после этого JP прокисает'
/

comment on column meta_external_service.disabled_at
    is 'disabled_at '
/

comment on column meta_external_service.reprocessing_bean
    is 'Метод запускаемый для репроцессинга'
/
comment on column meta_external_service.date_create
    is 'Дата создания'

/