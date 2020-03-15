package ru.vood.property.server.dao;

import ru.vood.property.server.dao.abstraction.CommonDaoService;

@Deprecated
abstract class AbstractDaoServiceJava<T> implements CommonDaoService<T> {

    private final Class<T> clazz;

    public AbstractDaoServiceJava(Class<T> clazz) {
        this.clazz = clazz;
    }
}
