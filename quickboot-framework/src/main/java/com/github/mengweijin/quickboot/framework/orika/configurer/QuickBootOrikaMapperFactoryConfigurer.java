package com.github.mengweijin.quickboot.framework.orika.configurer;

import cn.hutool.core.date.DatePattern;
import com.github.mengweijin.quickboot.framework.orika.OrikaConverter;
import com.github.mengweijin.quickboot.framework.orika.OrikaMapperFactoryConfigurer;
import com.github.mengweijin.quickboot.framework.orika.converter.DateToLocalDateConverter;
import com.github.mengweijin.quickboot.framework.orika.converter.DateToLocalDateTimeConverter;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalDateTimeToStringConverter;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalDateToStringConverter;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalTimeToStringConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Component
public class QuickBootOrikaMapperFactoryConfigurer implements OrikaMapperFactoryConfigurer {

    /**
     * Note the registration sequence when registering multiple registers of the same class and different patterns.
     * @param mapperFactory
     */
    @Override
    public void configure(MapperFactory mapperFactory) {
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();

        // DateToLocalDateTimeConverter
        converterFactory.registerConverter(new DateToLocalDateTimeConverter());
        // DateToLocalDateConverter
        converterFactory.registerConverter(new DateToLocalDateConverter());
        // DateToLocalTimeConverter Barely able to use.

        // LocalDateTimeToStringConverter
        converterFactory.registerConverter(new LocalDateTimeToStringConverter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        // LocalDateToStringConverter
        converterFactory.registerConverter(new LocalDateToStringConverter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        // LocalTimeToStringConverter
        converterFactory.registerConverter(new LocalTimeToStringConverter(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));

        this.registerExtendConverterWithId(converterFactory);

    }

    /**
     * classMapBuilder
     *      .field("nameA", "nameB")
     *      // Manually specify converter by converterId
     *      .fieldMap("stringToDate", "stringToDate").converter(OrikaConverterId.DateToStringConverter_NORM_DATE_PATTERN.name()).add();
     * @param converterFactory converterFactory
     */
    private void registerExtendConverterWithId(ConverterFactory converterFactory) {
        converterFactory.registerConverter(OrikaConverter.LocalDateTimeToString_NORM_DATETIME_MS.getId(), new LocalDateTimeToStringConverter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN)));
        converterFactory.registerConverter(OrikaConverter.LocalDateTimeToString_UTC.getId(), new LocalDateTimeToStringConverter(DateTimeFormatter.ofPattern(DatePattern.UTC_PATTERN)));

        // DateToStringConverter - DateTime
        converterFactory.registerConverter(OrikaConverter.DateToString_NORM_DATETIME.getId(), new DateToStringConverter(DatePattern.NORM_DATETIME_PATTERN));
        // DateToStringConverter - UTC DateTime
        converterFactory.registerConverter(OrikaConverter.DateToString_UTC_SIMPLE.getId(), new DateToStringConverter(DatePattern.UTC_SIMPLE_PATTERN));
        converterFactory.registerConverter(OrikaConverter.DateToString_UTC.getId(), new DateToStringConverter(DatePattern.UTC_PATTERN));
        converterFactory.registerConverter(OrikaConverter.DateToString_UTC_MS.getId(), new DateToStringConverter(DatePattern.UTC_MS_PATTERN));

        // DateToStringConverter - Date
        converterFactory.registerConverter(OrikaConverter.DateToString_NORM_DATE.getId(), new DateToStringConverter(DatePattern.NORM_DATE_PATTERN));
        // DateToStringConverter - Time
        converterFactory.registerConverter(OrikaConverter.DateToString_NORM_TIME.getId(), new DateToStringConverter(DatePattern.NORM_TIME_PATTERN));

    }
}
