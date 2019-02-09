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

package joop.window;

import java.util.function.Consumer;
import javax.swing.JFrame;
import joop.shape.Shape;
import unit.area.Area;

/**
 * A pen to create windows. It's necessary for classes that rely on data of
 * other classes. Since most interfaces don't allow retrieving this information,
 * one must take the information and create the instance by itself to bypass
 * this problem. Example:
 * <pre>{@code
 * // normally:
 * public void someMethod(Window window) {
 *     window.area.result( ... ) // doesn't work, because window takes area but
 *     // doesn't offer it in its interface.
 * }
 *
 * // alternative:
 * public void someMethod(window.Pen pen, Area area, ...) {
 *     pen.window(area, ...);
 *     area.result( ... );
 * }
 * }
 * </pre>
 * @since 0.24
 */
@FunctionalInterface
public interface Pen {
    /**
     * Returns a window that has the given settings.
     * @param area The area of the window to return.
     * @param feature The feature that the window must have.
     * @param shape The shape that the window must contain.
     * @return A window with the given settings.
     */
    Showable window(Area area, Consumer<JFrame> feature, Shape shape);
}
