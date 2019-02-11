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

import java.util.function.Function;
import joop.event.Event;

/**
 * Gives instances of shapes. This is necessary for classes that need
 * information from shape to fulfill their job. Because shapes for example
 * don't offer their area through their interface one cannot create a
 * labeled rectangle. That's because the label would need the area of the
 * rectangle to position itself into it. With pen this is possible like this:
 * <pre>{@code
 * public class Labeled implements Shape {
 *     public Labeled(Pen pen, Area area, String text) {
 *          shape = new Shapes(
 *              pen.shape(area, new NoEvent()),
 *              new Text(text, area)
 *          );
 *     }
 * }
 * }</pre>
 * @param <S> The type of the shape. This way it's possible to create shapes
 *  with additional properties.
 * @param <A> The type of the area. This is necessary because it depends on the
 *  shape to be created which area needs to be used.
 * @see joop.shape.gui.Button
 * @since 0.38
 */
public interface Pen<S extends Shape, A> {
    /**
     * Returns a shape.
     * @param area The area of the shape.
     * @param event The event function to create the event for the shape. It
     *  takes the shape to make it possible to access the shape from the event.
     * @return The shape with the given properties.
     */
    S shape(A area, Function<S, Event> event);
}
