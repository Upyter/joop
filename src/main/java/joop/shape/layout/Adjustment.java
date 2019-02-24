/*
 * Copyright 2019 Upyter
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package joop.shape.layout;

import unit.area.Area;
import unit.functional.QuadConsumer;

/**
 * An adjustment to the area and the color of a shape. Whether a shape uses the
 * adjustment, is up to the shape. This interface is meant to be used by
 * interfaces to adjust their shapes.
 * @since 0.25
 */
public interface Adjustment {
    /**
     * Applies the given area.
     * @param area The area of the shape to adjust.
     * @param target The adjusted application of the area.
     */
    void adjustedApply(
        Area area,
        QuadConsumer<Integer, Integer, Integer, Integer> target
    );
}
