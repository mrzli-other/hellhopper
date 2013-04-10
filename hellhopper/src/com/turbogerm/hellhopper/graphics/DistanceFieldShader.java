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
package com.turbogerm.hellhopper.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.util.ExceptionThrower;

public final class DistanceFieldShader extends ShaderProgram {
    public DistanceFieldShader () {
        super(
            Gdx.files.internal(ResourceNames.DISTANCE_FIELD_VS),
            Gdx.files.internal(ResourceNames.DISTANCE_FIELD_FS));
        if (!isCompiled()) {
            ExceptionThrower.throwException("Shader compilation failed:\n" + getLog());
        }
    }
    
    /** @param smoothing a value between 0 and 1 */
    public void setSmoothing(float smoothing) {
        float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
        setUniformf("u_lower", 0.5f - delta);
        setUniformf("u_upper", 0.5f + delta);
    }
}
