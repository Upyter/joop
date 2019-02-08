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

package joop.window_and_shape;

import joop.matcher.CorrectContent;
import joop.shape.Text;
import joop.window.BaseWindow;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import unit.color.RGBA;
import unit.pos.PosOf;

/**
 * Tests for the combined use of {@link BaseWindow} and {@link Text}.
 * @since 0.13
 */
public class WindowAndTextTest {
    /**
     * {@link BaseWindow#show()} must show a black text for {@link BaseWindow}
     * with a black {@link Text} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * blackTextAndWhiteWindow.png is used as the expected image of the inner
     * area.
     */
    @Test
    public void black() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var windowWidth = 330;
        final var windowHeight = 150;
        final var x = 120;
        final var y = 50;
        MatcherAssert.assertThat(
            new Text("Some text", new PosOf(x, y)),
            new CorrectContent(
                "window_and_text/blackTextOnWhiteWindow.png",
                windowWidth,
                windowHeight
            )
        );
    }

    /**
     * {@link BaseWindow#show()} must show a colored text for {@link BaseWindow}
     * with a colored {@link Text} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * coloredTextAndWhiteWindow.png is used as the expected image of the inner
     * area.
     */
    @Test
    public void colored() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var windowWidth = 400;
        final var windowHeight = 200;
        final var x = 140;
        final var y = 160;
        final var green = 255;
        MatcherAssert.assertThat(
            new Text(
                "text",
                new PosOf(x, y),
                new RGBA(0, 0, green)
            ),
            new CorrectContent(
                "window_and_text/coloredTextOnWhiteWindow.png",
                windowWidth,
                windowHeight
            )
        );
    }
}
