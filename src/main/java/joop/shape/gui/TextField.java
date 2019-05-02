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
import joop.shape.Shape;
import joop.shape.Text;
import joop.shape.UnfilledRect;
import org.apache.commons.lang3.mutable.Mutable;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.SoftArea;
import unit.color.Black;
import unit.pos.SoftPos;

/**
 * @since 0.68
 */
public class TextField implements Shape {
    private final Area area;
    private final Shape shape;
    private final Shape text;

    public TextField() {
        this(new SoftArea());
    }

    public TextField(final Area area) {
        this(area, new Mutable<>() {
            private String value = "";

            @Override
            public String getValue() {
                return this.value;
            }

            @Override
            public void setValue(final String s) {
                this.value = s;
            }
        });
    }

    public TextField(
        final Area area,
        final Mutable<String> target
    ) {
        this.shape = new UnfilledRect(
            area,
            new Black(),
            new Press(
                c -> {
                    var current = target.getValue();
                    if (c == KeyEvent.VK_BACK_SPACE) {
                        if (!current.isEmpty()) {
                            current = current.substring(
                                0, current.length() - 1
                            );
                        }
                    } else {
                        current += c;
                    }
                    target.setValue(current);
                }
            )
        );
        this.text = new Text(
            target::getValue,
            new SoftPos(
                () -> Math.min(1.0, area.w() - width(target.getValue(), area)),
                1
            )
        );
        this.area = area;
    }

    private static double width(String string, Area area) {
        Font font = new Font("Arial", Font.PLAIN, 20);
        final var bounds = font.createGlyphVector(
            new Canvas()
                .getFontMetrics(font)
                .getFontRenderContext(),
            string
        ).getLogicalBounds();
        return bounds.getWidth();
    }

    @Override
    public final void draw(final Graphics graphics) {
        final var backup = graphics.getClip();
        graphics.setClip(
            (int) this.area.x(),
            (int) this.area.y(),
            (int) this.area.w(),
            (int) this.area.h()
        );
        this.shape.draw(graphics);
        this.text.draw(graphics);
        graphics.setClip(backup);
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.shape.registerFor(source);
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        this.text.adjustment(adjustment);
        return this.shape.adjustment(adjustment);
    }
}
