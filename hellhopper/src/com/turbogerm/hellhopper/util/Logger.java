/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.util;

import com.badlogic.gdx.Gdx;

public final class Logger {
    
    private static final String TAG = "GermDefense";
    
    public static void debug(String message) {
        Gdx.app.debug(TAG, message);
    }
    
    public static void debug(String format, Object... args) {
        final String message = String.format(format, args);
        debug(message);
    }
    
    public static void info(String message) {
        Gdx.app.log(TAG, message);
    }
    
    public static void info(String format, Object... args) {
        final String message = String.format(format, args);
        info(message);
    }
    
    public static void error(String message) {
        Gdx.app.error(TAG, message);
    }
    
    public static void error(String format, Object... args) {
        final String message = String.format(format, args);
        error(message);
    }
}