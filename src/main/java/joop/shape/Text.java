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

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import joop.event.mouse.Mouse;
import joop.shape.layout.Adjustment;
import unit.area.Area;
import unit.area.AreaOf;
import unit.color.Black;
import unit.color.Color;
import unit.pos.Pos;
import unit.pos.PosOf;
import unit.size.SizeOf;
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
    private final Supplier<String> content;

    /**
     * The position of the text.
     */
    private final Pos pos;

    /**
     * The color of the rect.
     */
    private final Color color;

    /**
     * The area for adjustment.
     */
    private final Area adjusted;

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     */
    public Text(final String content, final Pos pos) {
        this(() -> content, pos);
    }

    /**
     * Ctor. Uses (0|0) as its position.
     * @param content The characters of the text.
     */
    public Text(final IntSupplier content) {
        this(content, new PosOf());
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     */
    public Text(final IntSupplier content, final Pos pos) {
        this(() -> Integer.toString(content.getAsInt()), pos, new Black());
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     */
    public Text(final Supplier<String> content, final Pos pos) {
        this(content, pos, new Black());
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     * @param color The color of the rect.
     */
    public Text(final String content, final Pos pos, final Color color) {
        this(
            () -> content,
            pos,
            color
        );
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     * @param color The color of the rect.
     */
    public Text(
        final Supplier<String> content, final Pos pos, final Color color
    ) {
        this.content = content;
        this.pos = pos;
        this.color = color;
        this.adjusted = new AreaOf(this.pos);
    }

    @Override
    public final void draw(
        final Graphics graphics
    ) {
        graphics.setColor(this.color.result(java.awt.Color::new));
        graphics.setFont(new Font("Times new Roman", Font.PLAIN, 25));
        final String current = this.content.get();
        Tuple.applyOn(
            this.pos,
            (x1, y1) -> adjustment.adjustedApply(
                new AreaOf(this.pos, new SizeOf(
                    0,
                    graphics.getFont().createGlyphVector(
                        ((Graphics2D) graphics).getFontRenderContext(), current
                    ).getPixelBounds(null, x1, y1).height
                )),
                // @checkstyle ParameterName (1 line)
                (x, y, width, height) -> graphics.drawString(
                    current,
                    x,
                    y + height
                )
            )
        );
    }

    private Rectangle getStringBounds(Graphics2D g2, String str,
        float x, float y)
    {
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, x, y);
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        // currently no implementation
    }
}
