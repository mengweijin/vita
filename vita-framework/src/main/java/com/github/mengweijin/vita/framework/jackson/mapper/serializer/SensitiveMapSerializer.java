package com.github.mengweijin.vita.framework.jackson.mapper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.mengweijin.vita.framework.jackson.mapper.SensitiveObjectMapper;
import org.dromara.hutool.core.text.CharSequenceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author mengweijin
 */
public class SensitiveMapSerializer extends MapSerializer {

    private static final JavaType KEY_TYPE = TypeFactory.defaultInstance().constructSimpleType(String.class, null);
    private static final JavaType VALUE_TYPE = KEY_TYPE;

    private static final TypeSerializer VTS = null;

    private static final JsonSerializer<?> KEY_SERIALIZER = new StringSerializer();
    private static final JsonSerializer<?> VALUE_SERIALIZER = new StringSerializer();

    public SensitiveMapSerializer() {
        super(null, null, KEY_TYPE, VALUE_TYPE, false, VTS, KEY_SERIALIZER, VALUE_SERIALIZER);
    }

    public SensitiveMapSerializer(MapSerializer src, Object filterId, boolean sortKeys) {
        super(src, filterId, sortKeys);
    }

    public SensitiveMapSerializer(SensitiveMapSerializer sensitiveMapSerializer, BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, Set<String> ignored, Set<String> included) {
        super(sensitiveMapSerializer, property, keySerializer, valueSerializer, ignored, included);
    }

    @Override
    public void serialize(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        super.serialize(this.processSensitive(value), gen, provider);
    }

    /**
     *  impl removed code：_ensureOverride("withResolved");
     */
    @Override
    public MapSerializer withResolved(BeanProperty property,
                                      JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer,
                                      Set<String> ignored, Set<String> included, boolean sortKeys) {
        SensitiveMapSerializer ser = new SensitiveMapSerializer(this, property, keySerializer, valueSerializer, ignored, included);
        if (sortKeys != ser._sortKeys) {
            ser = new SensitiveMapSerializer(ser, _filterId, sortKeys);
        }
        return ser;
    }

    private Map<?, ?> processSensitive(Map<?, ?> map) {
        Map<Object, Object> result = new HashMap<>();
        map.forEach((k, v) -> {
            if (v instanceof CharSequence && CharSequenceUtil.containsAnyIgnoreCase(CharSequenceUtil.toString(k), SensitiveObjectMapper.SENSITIVE_KEY)) {
                result.put(k, SensitiveObjectMapper.HIDE_VALUE);
            } else {
                result.put(k, v);
            }
        });
        return result;
    }
}
