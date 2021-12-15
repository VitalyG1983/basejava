package com.urise.webapp.util.gsonConv;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public interface RestletJsonConverter {
    <T> String toJson(T obj);

    <T> T fromJson(Reader reader, Type valueType);

    default <T> T fromJson(Reader reader, Class<T> valueClass) {
        return fromJson(reader, (Type) valueClass);
    }

    default <T> T fromJson(InputStream is, Type valueType) {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return fromJson(reader, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T fromJson(InputStream is, Class<T> valueClass) {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return fromJson(reader, valueClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T fromJson(String json, Type valueType) {
        try (Reader reader = new StringReader(json)) {
            return fromJson(reader, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T fromJson(String json, Class<T> valueClass) {
        try (Reader reader = new StringReader(json)) {
            return fromJson(reader, valueClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
