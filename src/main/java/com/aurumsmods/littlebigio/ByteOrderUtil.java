/*
 * The MIT License
 *
 * Copyright 2022 Aurum.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.aurumsmods.littlebigio;

import java.nio.ByteOrder;
import java.util.Objects;

/**
 * This utility class provides methods to deal with {@code ByteOrder}s, such as methods to convert byte orders to BOMs and
 * vice-versa.
 * 
 * @author Aurum
 * @since 1.0
 */
public final class ByteOrderUtil {
    private ByteOrderUtil() { throw new IllegalStateException(); }
    
    /**
     * Returns the opposite of the specified {@code ByteOrder}. The result will be as follows:
     * <ul>
     * <li>{@code ByteOrder.LITTLE_ENDIAN} if {@code other} == {@code ByteOrder.BIG_ENDIAN}
     * <li>{@code ByteOrder.BIG_ENDIAN} if {@code other} == {@code ByteOrder.LITTLE_ENDIAN}
     * </ul>
     * 
     * @param other the byte order from which the inverse should be derived.
     * @return the opposite {@code ByteOrder}.
     */
    public static ByteOrder inverse(ByteOrder other) {
        return Objects.requireNonNull(other) == ByteOrder.BIG_ENDIAN ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
    }
    
    /**
     * Creates and returns a byte array containing the UTF-16 byte order mark (BOM) for the given {@code ByteOrder}. The array
     * contains two bytes. The result's contents will be as follows:
     * <ul>
     * <li>{ 0xFE, 0xFF } if {@code endian} == {@code ByteOrder.BIG_ENDIAN}
     * <li>{ 0xFF, 0xFE } if {@code endian} == {@code ByteOrder.LITTLE_ENDIAN}
     * </ul>
     * 
     * @param endian the byte order.
     * @return a byte array containing the UTF-16 BOM.
     */
    public static byte[] toUTF16BOM(ByteOrder endian) {
        if (Objects.requireNonNull(endian) == ByteOrder.BIG_ENDIAN) {
            return new byte[] { (byte)0xFE, (byte)0xFF };
        }
        else {
            return new byte[] { (byte)0xFF, (byte)0xFE };
        }
    }
    
    /**
     * Returns the {@code ByteOrder} that corresponds to the byte order mark (BOM) found inside the specified buffer.
     * 
     * @param arr the buffer containing the BOM.
     * @param off the offset in the data array.
     * @return the {@code ByteOrder} that corresponds to the BOM.
     * @throws IllegalArgumentException if the buffer does not contain a valid UTF-16 BOM.
     */
    public static ByteOrder fromUTF16BOM(byte[] arr, int off) {
        Objects.checkFromIndexSize(off, 2, arr.length);
        int hi = arr[off] & 0xFF;
        int lo = arr[off + 1] & 0xFF;
        
        if (hi == 0xFE && lo == 0xFF) {
            return ByteOrder.BIG_ENDIAN;
        }
        else if (hi == 0xFF && lo == 0xFE) {
            return ByteOrder.LITTLE_ENDIAN;
        }
        
        throw new IllegalArgumentException("Array does not contain valid BOM.");
    }
}
