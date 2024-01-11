package com.example.helper;

import ch.qos.logback.core.spi.ErrorCodes;
import com.example.exception.ApplicationException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class ResultHelper {
    public <T> List<T> getResultList(Query query, Class<T> type) {
        List<Object[]> datas = query.getResultList();
        return map(type, datas);

    }

    private <T> List<T> map(Class<T> type, List<Object[]> datas) {
        List<T> result = new LinkedList<>();
        for (var data : datas) {
            result.add(map(type, data));
        }
        return result;
    }

    private <T> T map(Class<T> type, Object[] tuple) {
        List<Class<?>> tupleType = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            tupleType.add(field.getType());
        }
        try {
            Constructor<T> ctor = type.getConstructor(tupleType.toArray(new Class<?>[tuple.length]));
            return ctor.newInstance(tuple);
        } catch (Exception e) {
            throw new ApplicationException(5002, e.getMessage(), e);
        }
    }
}
