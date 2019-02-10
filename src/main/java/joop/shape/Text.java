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
import unit.color.Black;
import unit.color.Color;
import unit.pos.Pos;
import unit.tuple.Tuple;

/**
 * A text.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments.</p>
 * @since 0.13
 */
public class Text implements Shape {
    /**
     * The area of the rect.
     */
    private final String content;

    /**
     * The position of the text.
     */
    private final Pos pos;

    /**
     * The color of the rect.
     */
    private final Color color;

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     */
    public Text(final String content, final Pos pos) {
        this(content, pos, new Black());
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     * @param color The color of the rect.
     */
    public Text(final String content, final Pos pos, final Color color) {
        this.content = content;
        this.pos = pos;
        this.color = color;
    }

    @Override
    public final Optional<Shape> draw(final Graphics graphics) {
        graphics.setColor(this.color.result(java.awt.Color::new));
        Tuple.applyOn(
            this.pos,
            // @checkstyle ParameterName (1 line)
            (x, y) -> graphics.drawString(this.content, x, y)
        );
        return Optional.of(this);
    }
}
