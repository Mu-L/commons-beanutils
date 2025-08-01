/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.beanutils2.sql.converters;

import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.beanutils2.converters.AbstractDateConverterTest;
import org.junit.jupiter.api.Test;

/**
 * Test Case for the {@link SqlTimeConverter} class.
 */
class SqlTimeConverterTest extends AbstractDateConverterTest<Time> {

    /**
     * Gets the expected type
     *
     * @return The expected type
     */
    @Override
    protected Class<Time> getExpectedType() {
        return Time.class;
    }

    /**
     * Create the Converter with no default value.
     *
     * @return A new Converter
     */
    @Override
    protected SqlTimeConverter makeConverter() {
        return new SqlTimeConverter();
    }

    /**
     * Create the Converter with a default value.
     *
     * @param defaultValue The default value
     * @return A new Converter
     */
    @Override
    protected SqlTimeConverter makeConverter(final Time defaultValue) {
        return new SqlTimeConverter(defaultValue);
    }

    /**
     * Test default String to java.sql.Time conversion
     */
    @Override
    @Test
    public void testDefaultStringToTypeConvert() {
        // Create & Configure the Converter
        final SqlTimeConverter converter = makeConverter();
        converter.setUseLocaleFormat(false);

        // Valid String --> java.sql.Time Conversion
        final String testString = "15:36:21";
        final Object expected = toType(testString, "HH:mm:ss", null);
        validConversion(converter, expected, testString);

        // Invalid String --> java.sql.Time Conversion
        invalidConversion(converter, "15:36");
    }

    /**
     * Test Date Converter with no default value
     */
    @Override
    @Test
    public void testLocale() {
        // Re-set the default Locale to Locale.US
        final Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        final String pattern = "h:mm a"; // SHORT style time format for US Locale

        // Create & Configure the Converter
        final SqlTimeConverter converter = makeConverter();
        converter.setUseLocaleFormat(true);

        // Valid String --> Type Conversion
        final String testString = "3:06 pm";
        final Object expected = toType(testString, pattern, null);
        validConversion(converter, expected, testString);

        // Invalid Conversions
        invalidConversion(converter, null);
        invalidConversion(converter, "");
        invalidConversion(converter, "13:05");
        invalidConversion(converter, "11:05 p");
        invalidConversion(converter, "11.05 pm");
        invalidConversion(converter, Integer.valueOf(2));

        // Test specified Locale
        converter.setLocale(Locale.UK);
        invalidConversion(converter, testString); // Test previous value now fails
        validConversion(converter, expected, "15:06"); // UK Short style is "HH:mm"

        // Restore the default Locale
        Locale.setDefault(defaultLocale);
    }

    /**
     * Convert from a Calendar to the appropriate Date type
     *
     * @param value The Calendar value to convert
     * @return The converted value
     */
    @Override
    protected Time toType(final Calendar value) {
        return new Time(getTimeInMillis(value));
    }

}
