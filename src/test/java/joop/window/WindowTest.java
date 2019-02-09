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

import java.awt.Rectangle;
import java.awt.Robot;
import joop.shape.Rect;
import joop.shape.Shape;
import net.avh4.util.imagecomparison.hamcrest.ImageComparisonMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import unit.area.Area;
import unit.area.AreaOf;

/**
 * Tests for {@link Window}.
 * @since 0.21
 */
public final class WindowTest {
    /**
     * {@link Window#Window(String, Area, Shape)} must create a window
     * with a title, the given area and the given shape on it.
     * @throws Exception if the robot fails to make the screen shot, if the
     *  image comparison fails to load the image or if Thread.sleep fails.
     */
    @Test
    public void titleWithShape() throws Exception {
        final var title = "some title";
        // @checkstyle LocalFinalVariable (2 lines)
        final var x = 123;
        final var y = 284;
        final var width = 580;
        final var height = 840;
        // @checkstyle LocalFinalVariable (2 lines)
        final var rectWidth = 340;
        final var rectHeight = 440;
        new Window(
            title,
            new AreaOf(x, y, width, height),
            new Rect(0, 0, rectWidth, rectHeight)
        ).show();
        final long sleep = 250L;
        Thread.sleep(sleep);
        MatcherAssert.assertThat(
            new Robot().createScreenCapture(
                new Rectangle(x, y, width, height)
            ),
            ImageComparisonMatchers.looksLike(
                "window/titleWindow.png"
            )
        );
    }
}
