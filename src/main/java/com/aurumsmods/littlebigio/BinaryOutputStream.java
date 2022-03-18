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

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * A binary data output stream that provides writing of primitive data types based on the specified {@link ByteOrder} (big
 * or little). This implements {@link DataOutput} but does not follow the exact specifications prescribed by the contract. This
 * is because {@code DataOutput} explicitly states the implementation of big-endian byte ordering.
 * 
 * @author Aurum
 * @since 1.0
 */
public final class BinaryOutputStream extends FilterOutputStream implements DataOutput {
    /**
     * The byte order in which the data is stored.
     * @see ByteOrder
     */
    private ByteOrder endianness;
    
    /**
     * A working buffer that stores temporarily written data. Fits up to 8 bytes.
     */
    private final byte[] buffer = new byte[8];
    
    /**
     * Creates a {@code BinaryOutputStream} that wraps the given output stream and uses the native byte order when writing data.
     * 
     * @param out the wrapped output stream.
     */
    public BinaryOutputStream(OutputStream out) {
        super(Objects.requireNonNull(out));
        endianness = ByteOrder.nativeOrder();
    }
    
    /**
     * Creates a {@code BinaryOutputStream} that wraps the given output stream and uses the specified {@code ByteOrder} when
     * writing data.
     * 
     * @param out the wrapped output stream.
     * @param endian the byte order of the data.
     */
    public BinaryOutputStream(OutputStream out, ByteOrder endian) {
        super(Objects.requireNonNull(out));
        endianness = Objects.requireNonNull(endian);
    }
    
    /**
     * Returns the byte order of this stream.
     * 
     * @return the byte order.
     */
    public ByteOrder order() {
        return endianness;
    }
    
    /**
     * Sets the byte order of this stream.
     * 
     * @param endian the byte order.
     */
    public void order(ByteOrder endian) {
    	endianness = endian;
    }

    /**
     * Writes a {@code boolean} value to the output stream. If the value is {@code true}, 1 is written to the output stream, 0
     * otherwise.
     * 
     * @param val the {@code boolean} value.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeBoolean(boolean val) throws IOException {
        out.write(val ? 1 : 0);
    }

    /**
     * Writes a {@code byte} value to the output stream.
     * 
     * @param val the {@code byte} value.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeByte(int val) throws IOException {
        out.write(val);
    }

    /**
     * Writes a {@code short} value which occupies 2 bytes to the output stream. It does this by storing the {@code short} in a
     * temporary buffer through {@link BitConverter} and then writing the buffer's contents to the stream.
     * 
     * @see BitConverter#putShort(byte[], int, java.nio.ByteOrder, short) 
     * @param val the {@code short} value.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeShort(int val) throws IOException {
        BitConverter.putShort(buffer, 0, endianness, (short)val);
        out.write(buffer, 0, 2);
    }

    /**
     * Writes a {@code char} value which occupies 2 bytes to the output stream. It does this by storing the {@code char} in a
     * temporary buffer through {@link BitConverter} and then writing the buffer's contents to the stream.
     * 
     * @see BitConverter#putChar(byte[], int, java.nio.ByteOrder, char) 
     * @param val the {@code char} value.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeChar(int val) throws IOException {
        BitConverter.putChar(buffer, 0, endianness, (char)val);
        out.write(buffer, 0, 2);
    }

    /**
     * Writes an {@code int} value which occupies 4 bytes to the output stream. It does this by storing the {@code int} in a
     * temporary buffer through {@link BitConverter} and then writing the buffer's contents to the stream.
     * 
     * @see BitConverter#putInt(byte[], int, java.nio.ByteOrder, int) 
     * @param val the {@code int} value.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeInt(int val) throws IOException {
        BitConverter.putInt(buffer, 0, endianness, val);
        out.write(buffer, 0, 4);
    }

    /**
     * Writes a {@code long} value which occupies 8 bytes to the output stream. It does this by storing the {@code long} in a
     * temporary buffer through {@link BitConverter} and then writing the buffer's contents to the stream.
     * 
     * @see BitConverter#putLong(byte[], int, java.nio.ByteOrder, long) 
     * @param val the {@code long} value.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeLong(long val) throws IOException {
        BitConverter.putLong(buffer, 0, endianness, val);
        out.write(buffer, 0, 8);
    }

    /**
     * Writes a {@code float} value which occupies 4 bytes to the output stream. It does this by storing the {@code float} in a
     * temporary buffer through {@link BitConverter} and then writing the buffer's contents to the stream.
     * 
     * @see BitConverter#putFloat(byte[], int, java.nio.ByteOrder, float) 
     * @param val the {@code float} value.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeFloat(float val) throws IOException {
        BitConverter.putFloat(buffer, 0, endianness, val);
        out.write(buffer, 0, 4);
    }

    /**
     * Writes a {@code double} value which occupies 8 bytes to the output stream. It does this by storing the {@code double} in
     * a temporary buffer through {@link BitConverter} and then writing the buffer's contents to the stream.
     * 
     * @see BitConverter#putDouble(byte[], int, java.nio.ByteOrder, double) 
     * @param val the {@code double} value.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeDouble(double val) throws IOException {
        BitConverter.putDouble(buffer, 0, endianness, val);
        out.write(buffer, 0, 8);
    }

    /**
     * Writes every character in the given string to the output stream. For every character, only the low-order eight bits are
     * written. The high-order eight bits of each character are ignored.
     * 
     * @param str the {@code String}.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeBytes(String str) throws IOException {
        for (char ch : str.toCharArray()) {
            out.write((byte)ch);
        }
    }

    /**
     * Writes every character in the given string to the output stream.
     * 
     * @see #writeChar(int)
     * @param str the {@code String}.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeChars(String str) throws IOException {
        for (char ch : str.toCharArray()) {
            writeChar(ch);
        }
    }

    /**
     * Writes two bytes of length information to the output stream, followed by the modified UTF-8 representation of every
     * character in the given string. Each character in the string is converted to a group of one, two, or three bytes,
     * depending on the value of the character.
     * 
     * @see DataOutputStream#writeUTF(java.lang.String) 
     * @param str the string.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    public void writeUTF(String str) throws IOException {
        ((DataOutputStream)out).writeUTF(str);
    }
}
