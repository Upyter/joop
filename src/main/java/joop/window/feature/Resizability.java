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

package joop.window.feature;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Consumer;
import javax.swing.JFrame;
import unit.area.Area;
import unit.functional.Lazy;
import unit.scalar.FixScalar;
import unit.scalar.SoftScalar;
import unit.size.Size;
import unit.size.SoftSize;

/**
 * Makes a window resizable if its size is soft. Otherwise it will be
 * non-resizable.
 * <p>This class is immutable and thread-safe.</p>
 * @since 0.59
 */
public class Resizability implements Consumer<JFrame> {
    /**
     * The size of the window.
     */
    private final Lazy<Size> size;

    /**
     * Ctor.
     * @param area The area of the window to get the size from.
     */
    public Resizability(final Area area) {
        this(
            () -> new SoftSize(
                area.cleanW().isFix() ? new FixScalar(area.w()) : new SoftScalar(area.cleanW().cleanValue()),
                area.cleanH().isFix() ? new FixScalar(area.h()) : new SoftScalar(area.cleanH().cleanValue())
            )
        );
    }

    /**
     * Ctor.
     * @param size The size of the window.
     */
    public Resizability(final Size size) {
        this(() -> size);
    }

    /**
     * Ctor.
     * @param size The size of the window.
     */
    public Resizability(final Lazy<Size> size) {
        this.size = size;
    }

    @Override
    public final void accept(final JFrame frame) {
        final var current = this.size.value();
        if (current.cleanW().isFix() && current.cleanH().isFix()) {
            frame.setResizable(false);
            System.out.println("FIX");
        } else {
            frame.setResizable(true);
            frame.addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent event) {
                        final var current = Resizability.this.size.value();
                        final int width;
                        if (current.cleanW().isFix()) {
                            width = (int) current.w();
                        } else {
                            width = frame.getWidth();
                        }
                        final int height;
                        if (current.cleanH().isFix()) {
                            height = (int) current.h();
                        } else {
                            height = frame.getHeight();
                        }
                        frame.setSize(width, height);
                        super.componentResized(event);
                    }
                }
            );
        }
    }
}
