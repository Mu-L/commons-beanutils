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

package org.apache.commons.beanutils2.converters;

import org.apache.commons.beanutils2.locale.converters.ShortLocaleConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Case for the ShortLocaleConverter class.
 */
class ShortLocaleConverterTest extends AbstractLocaleConverterTest<Short> {

    /**
     * Sets up instance variables required by this test case.
     */
    @Override
    @BeforeEach
    public void setUp() throws Exception {

        super.setUp();

        defaultValue = Short.valueOf("999");
        expectedValue = Short.valueOf(expectedIntegerValue);

    }

    /**
     * Test Converter() constructor
     *
     * Uses the default locale, no default value
     */
    @Test
    void testConstructor_2() {

        // Construct using default locale
        converter = ShortLocaleConverter.builder().get();

        // Perform Tests
        convertValueNoPattern(converter, defaultIntegerValue, expectedValue);
        convertValueWithPattern(converter, defaultIntegerValue, defaultIntegerPattern, expectedValue);
        convertInvalid(converter, null);
        convertNull(converter, null);

    }

    /**
     * Test Converter(locPattern) constructor
     *
     * Uses the default locale, no default value
     */
    @Test
    void testConstructor_3() {

        // Construct using localized pattern (default locale)
        converter = ShortLocaleConverter.builder().setLocalizedPattern(true).get();

        // Perform Tests
        convertValueNoPattern(converter, defaultIntegerValue, expectedValue);
        convertValueWithPattern(converter, defaultIntegerValue, defaultIntegerPattern, expectedValue);
        convertInvalid(converter, null);
        convertNull(converter, null);

    }

    /**
     * Test Converter(Locale) constructor
     */
    @Test
    void testConstructor_4() {

        // Construct using specified Locale
        converter = ShortLocaleConverter.builder().setLocale(localizedLocale).get();

        // Perform Tests
        convertValueNoPattern(converter, localizedIntegerValue, expectedValue);
        convertValueWithPattern(converter, localizedIntegerValue, defaultIntegerPattern, expectedValue);
        convertInvalid(converter, null);
        convertNull(converter, null);

    }

    /**
     * Test Converter(Locale, locPattern) constructor
     */
    @Test
    void testConstructor_5() {

        // Construct using specified Locale
        converter = ShortLocaleConverter.builder().setLocale(localizedLocale).setLocalizedPattern(true).get();

        // Perform Tests
        convertValueNoPattern(converter, localizedIntegerValue, expectedValue);
        convertValueWithPattern(converter, localizedIntegerValue, localizedIntegerPattern, expectedValue);
        convertInvalid(converter, null);
        convertNull(converter, null);

    }

    /**
     * Test Converter(Locale, pattern) constructor
     */
    @Test
    void testConstructor_6() {

        // Construct using specified Locale
        converter = ShortLocaleConverter.builder().setLocale(localizedLocale).setPattern(defaultIntegerPattern).get();

        // Perform Tests
        convertValueNoPattern(converter, localizedIntegerValue, expectedValue);
        convertValueWithPattern(converter, localizedIntegerValue, defaultIntegerPattern, expectedValue);
        convertInvalid(converter, null);
        convertNull(converter, null);

    }

    /**
     * Test Converter(Locale, pattern, locPattern) constructor
     */
    @Test
    void testConstructor_7() {

        // Construct using specified Locale
        converter = ShortLocaleConverter.builder().setLocale(localizedLocale).setPattern(localizedIntegerPattern).setLocalizedPattern(true).get();

        // Perform Tests
        convertValueNoPattern(converter, localizedIntegerValue, expectedValue);
        convertValueWithPattern(converter, localizedIntegerValue, localizedIntegerPattern, expectedValue);
        convertInvalid(converter, null);
        convertNull(converter, null);

    }

    /**
     * Test Converter(defaultValue) constructor
     */
    @Test
    void testConstructor_8() {

        // Construct using specified Locale
        converter = ShortLocaleConverter.builder().setDefault(defaultValue).get();

        // Perform Tests
        convertValueNoPattern(converter, defaultIntegerValue, expectedValue);
        convertValueWithPattern(converter, defaultIntegerValue, defaultIntegerPattern, expectedValue);
        convertInvalid(converter, defaultValue);
        convertNull(converter, defaultValue);

    }

    /**
     * Test Converter(defaultValue, locPattern) constructor
     */
    @Test
    void testConstructor_9() {

        // Construct using specified Locale
        converter = ShortLocaleConverter.builder().setDefault(defaultValue).setLocalizedPattern(true).get();

        // Perform Tests
        convertValueNoPattern(converter, defaultIntegerValue, expectedValue);
        convertValueWithPattern(converter, defaultIntegerValue, defaultIntegerPattern, expectedValue);
        convertInvalid(converter, defaultValue);
        convertNull(converter, defaultValue);

    }

    /**
     * Test Converter(defaultValue, locale, pattern, localizedPattern) constructor
     */
    @Test
    void testConstructorMain() {

        // Construct with localized pattern
        converter = ShortLocaleConverter.builder().setDefault(defaultValue).setLocale(localizedLocale).setPattern(localizedIntegerPattern)
                .setLocalizedPattern(true).get();

        convertValueNoPattern(converter, "(A)", localizedIntegerValue, expectedValue);
        convertValueWithPattern(converter, "(A)", localizedIntegerValue, localizedIntegerPattern, expectedValue);
        convertInvalid(converter, "(A)", defaultValue);
        convertNull(converter, "(A)", defaultValue);

        // **************************************************************************
        // Convert value in the wrong format - maybe you would expect it to throw an
        // exception and return the default - it doesn't, DecimalFormat parses it
        // quite happily turning "1,234" into "1"
        // I guess this is one of the limitations of DecimalFormat
        // **************************************************************************
        convertValueNoPattern(converter, "(B)", defaultIntegerValue, Short.valueOf("1"));

        // **************************************************************************
        // Convert with non-localized pattern - unlike the equivalent BigDecimal Test Case
        // it doesn't causes an exception in parse() - DecimalFormat parses it
        // quite happily turning "1,234" into "1"
        // Again this is one of the limitations of DecimalFormat
        // **************************************************************************
        convertValueWithPattern(converter, "(B)", localizedIntegerValue, defaultIntegerPattern, Short.valueOf("1"));

        // **************************************************************************
        // Convert with specified type
        //
        // BaseLocaleConverter completely ignores the type - so even if we specify
        // Double.class here it still returns a Short.
        // **** This has been changed due to BEANUTILS-449 ****
        // **************************************************************************
        // convertValueToType(converter, "(B)", Double.class, localizedIntegerValue, localizedIntegerPattern, expectedValue);

        // Construct with non-localized pattern
        converter = ShortLocaleConverter.builder().setDefault(defaultValue).setLocale(localizedLocale).setPattern(defaultIntegerPattern)
                .setLocalizedPattern(false).get();

        convertValueNoPattern(converter, "(C)", localizedIntegerValue, expectedValue);
        convertValueWithPattern(converter, "(C)", localizedIntegerValue, defaultIntegerPattern, expectedValue);
        convertInvalid(converter, "(C)", defaultValue);
        convertNull(converter, "(C)", defaultValue);

    }

}
