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

import java.util.function.Consumer;
import javax.swing.JFrame;
import unit.area.Area;
import unit.functional.Lazy;
import unit.size.Size;

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
        this(() -> area.result((pos, size) -> size));
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
        frame.setResizable(!this.size.value().isFix());
    }
}
