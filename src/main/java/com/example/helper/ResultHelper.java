package com.example.helper;

import ch.qos.logback.core.spi.ErrorCodes;
import com.example.convert.LocalDateConverter;
import com.example.exception.ApplicationException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ResultHelper {

    private static <T> List<T> map(Class<T> type, List<Object> records) {
        List<T> result = new LinkedList<>();
        for (Object rc : records) {
            if (rc.getClass().isArray()) {
                result.add(map(type, (Object[]) rc));
            } else if (type.isAssignableFrom(rc.getClass())) {
                @SuppressWarnings("unchecked")
                T v = (T) rc;
                result.add(v);
            }
        }
        return result;
    }

    private static <T> T map(Class<T> type, Object[] tuple) {
        List<Class<?>> tupleTypes = new ArrayList<>();
        var x = type.getDeclaredFields();
        for (Field field : type.getDeclaredFields()) {
            tupleTypes.add(field.getType());
        }
        for (int i = 0; i < x.length; i++) {
            var convertAnnotation = x[i].getAnnotation(jakarta.persistence.Convert.class);
            if (convertAnnotation != null && tuple[i] instanceof Date date && convertAnnotation.converter().equals(LocalDateConverter.class)) {
                tuple[i] = new LocalDateConverter().convertToEntityAttribute(date);
            }

        }
        try {
            Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tuple.length]));
            return ctor.newInstance(tuple);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 SecurityException e) {
            throw new ApplicationException(500, e.getMessage(), e);
        }
    }

    public static <T> List<T> getResultList(Query query, Class<T> type) {
        @SuppressWarnings("unchecked")
        List<Object> records = query.getResultList();
        return map(type, records);
    }

    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    public static <T> Set<T> getResultSet(Query query, Class<T> type) {
        @SuppressWarnings("unchecked")
        List<Object[]> records = query.getResultList();
        return mapAsSet(type, records);
    }

    private static <T> Set<T> mapAsSet(Class<T> type, List<Object[]> records) {
        Set<T> result = new HashSet<>();
        for (Object[] rc : records) {
            result.add(map(type, rc));
        }
        return result;
    }
}
