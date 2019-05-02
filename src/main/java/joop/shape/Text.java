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

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import joop.event.mouse.InputHardware;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.MixArea;
import unit.color.Black;
import unit.color.Color;
import unit.functional.Lazy;
import unit.pos.Pos;
import unit.pos.SoftPos;
import unit.size.FixSize;

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
     * The area of the text.
     */
    private final Area area;

    /**
     * The color of the rect.
     */
    private final java.awt.Color color;

    /**
     * The font of the text.
     */
    private final Font font;

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
     * @param content The source of the text.
     */
    public Text(final IntSupplier content) {
        this(content, new SoftPos());
    }

    /**
     * Ctor. Uses (0|0) as its position.
     * @param content The characters of the text.
     */
    public Text(final String content) {
        this(() -> content, new SoftPos());
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     */
    public Text(final int content, final Pos pos) {
        this(() -> Integer.toString(content), pos, new Black());
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
        final Supplier<String> content,
        final Pos pos,
        final Color color
    ) {
        this(
            content,
            pos,
            color,
            new Font("Arial", Font.PLAIN, 20)
        );
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param pos The position of the text.
     * @param color The color of the rect.
     * @param font The font to be used.
     */
    public Text(
        final Supplier<String> content,
        final Pos pos,
        final Color color,
        final Font font
    ) {
        this(
            content,
            area -> new MixArea(pos, new FixSize(area.w(), area.h())),
            color,
            font
        );
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param area The area of the text.
     * @param color The color of the rect.
     */
    public Text(
        final Supplier<String> content,
        final Function<Area, Area> area,
        final Color color
    ) {
        this(
            content,
            area,
            color,
            new Font("Arial", Font.PLAIN, 20)
        );
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param area The area of the text.
     * @param color The color of the rect.
     * @param font The font to be used.
     */
    public Text(
        final Supplier<String> content,
        final Function<Area, Area> area,
        final Color color,
        final Font font
    ) {
        this(
            content,
            area.apply(
                ((Lazy<Area>) () -> {
                    final var bounds = font.createGlyphVector(
                        new Canvas()
                            .getFontMetrics(font)
                            .getFontRenderContext(),
                        content.get()
                    ).getLogicalBounds();
                    return new MixArea(
                        new SoftPos(),
                        new FixSize(bounds.getWidth(), bounds.getHeight())
                    );
                }).value()
            ),
            color,
            font
        );
    }

    /**
     * Ctor.
     * @param content The characters of the text.
     * @param area The area of the text.
     * @param color The color of the rect.
     */
    private Text(
        final Supplier<String> content,
        final Area area,
        final Color color,
        final Font font
    ) {
        this.content = content;
        this.area = area;
        this.color = color.result(java.awt.Color::new);
        this.font = font;
    }

    @Override
    public final void draw(final Graphics graphics) {
        graphics.setColor(this.color);
        graphics.setFont(this.font);
        graphics.drawString(
            this.content.get(),
            (int) this.area.x(),
            (int) (this.area.y() + this.area.h())
        );
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        this.area.adjustment(adjustment);
        return this.area;
    }

    @Override
    public final void registerFor(final InputHardware source) {
        // currently no implementation
    }
}
