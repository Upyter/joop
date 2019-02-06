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

package joop.shape;

import java.awt.Graphics;
import java.util.Optional;
import unit.area.Area;

/**
 * A rectangle using java2d.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments. Additionally whether this
 * class is thread-safe or not, depends on the given graphics instance for
 * {@link this#draw(Graphics)}.</p>
 * @since 2.1.0
 */
public class Rect implements Shape {
    /**
     * The area of the rect.
     */
    private final Area area;

    /**
     * Ctor.
     * @param area The area of the rect.
     */
    public Rect(final Area area) {
        this.area = area;
    }

    @Override
    public final Optional<Shape> draw(final Graphics graphics) {
        Area.applyOn(this.area, graphics::fillRect);
        return Optional.of(this);
    }
}
