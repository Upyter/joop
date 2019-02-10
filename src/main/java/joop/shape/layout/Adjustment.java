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
import unit.color.Color;

/**
 * An adjustment to the drawing of shapes. This can be used by layouts (which
 * are also shapes) to adjust their contained shapes in the desired way.
 * @since 0.25
 */
@FunctionalInterface
public interface Adjustment {
    /**
     * Applies the given drawing consumer to draw the shape with the intended
     * adjustment.
     * @param area The area of the shape to adjust.
     * @param color The color of the shape to adjust.
     * @param drawing The drawing operation that will get the adjusted area
     *  and
     */
    void drawAdjusted(Area area, Color color, Drawing drawing);
}
