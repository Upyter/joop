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

package joop.shape.gui;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import joop.event.keyboard.Press;
import joop.event.mouse.InputHardware;
import joop.shape.Rect;
import joop.shape.Shape;
import joop.shape.Text;
import unit.area.Adjustment;
import unit.area.Area;
import unit.color.RGBA;
import unit.pos.SoftPos;

/**
 * @since 0.68
 */
public class TextField implements Shape {
    private final Area area;
    private final Shape shape;
    private final Shape text;

    public TextField(final Area area) {
        final var text = new Object() {
            private String string = "A";

            public String getValue() {
                return string;
            }

            public void add(final char c) {
                if (c == KeyEvent.VK_BACK_SPACE) {
                    if (!string.isEmpty()) {
                        string = string.substring(0, string.length() - 1);
                    }
                } else {
                    this.string += c;
                }
            }
        };
        this.shape = new Rect(
            area,
            new RGBA(50, 203, 43),
            new Press(text::add)
        );
        this.text = new Text(
            text::getValue,
            new SoftPos(
                () -> Area.result(
                    area,
                    (x, y, w, h) -> Math.min(0, w - width(text.getValue(), area))
                ),
                0
            )
        );
        this.area = area;
    }

    private static int width(String string, Area area) {
        Font font = new Font("Times new Roman", Font.PLAIN, 25);
        final var bounds = Area.result(
            area,
            (x, y, w, h) -> font.createGlyphVector(
                new Canvas()
                    .getFontMetrics(font)
                    .getFontRenderContext(),
                string
            ).getPixelBounds(null, x, y)
        );
        return bounds.width;
    }

    @Override
    public final void draw(final Graphics graphics) {
        Area.applyOn(this.area, graphics::setClip);
        this.shape.draw(graphics);
        this.text.draw(graphics);
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.shape.registerFor(source);
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        return this.shape.adjustment(adjustment);
    }
}
