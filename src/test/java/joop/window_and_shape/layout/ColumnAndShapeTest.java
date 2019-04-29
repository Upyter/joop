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

package joop.window_and_shape.layout;

import joop.matcher.CorrectContent;
import joop.shape.Rect;
import joop.shape.layout.Column;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import unit.area.SoftArea;
import unit.color.RGBA;

/**
 * Tests for the combined use of {@link joop.window.BaseWindow}, {@link Column}
 * and different shapes.
 * @since 0.30
 */
public final class ColumnAndShapeTest {
    /**
     * Using a single {@link Rect} on a column should draw the rect as if there
     * wouldn't be any layout.
     */
    @Test
    public void rect() {
        // @checkstyle LocalFinalVariableName (2 lines)
        final int x = 50;
        final int y = 200;
        final int width = 100;
        final int height = 50;
        // @checkstyle LocalFinalVariableName (2 lines)
        final int windowWidth = 500;
        final int windowHeight = 500;
        MatcherAssert.assertThat(
            new Column(
                new Rect(x, y, width, height)
            ),
            new CorrectContent(
                "column_and_shape/singleRect.png",
                windowWidth,
                windowHeight
            )
        );
    }

    /**
     * Using multiple {@link Rect}s in a column with everyone having y = 0 must
     * put them one below another in a column.
     */
    @Test
    public void rectsOnZeroY() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final int x1 = 0;
        final int x2 = 50;
        final int x3 = 100;
        final int y = 0;
        final int width = 100;
        // @checkstyle LocalFinalVariableName (3 lines)
        final int height1 = 50;
        final int height2 = 100;
        final int height3 = 150;
        // @checkstyle LocalFinalVariableName (2 lines)
        final int windowWidth = 500;
        final int windowHeight = 500;
        final int red = 255;
        final int green = 255;
        final int blue = 255;
        MatcherAssert.assertThat(
            new Column(
                new Rect(
                    new SoftArea(x1, y, width, height1),
                    new RGBA(red, 0, 0)
                ),
                new Rect(
                    new SoftArea(x2, y, width, height2),
                    new RGBA(0, green, 0)
                ),
                new Rect(
                    new SoftArea(x3, y, width, height3),
                    new RGBA(0, 0, blue)
                )
            ),
            new CorrectContent(
                "column_and_shape/rectsOnZeroY.png",
                windowWidth,
                windowHeight
            )
        );
    }
}
