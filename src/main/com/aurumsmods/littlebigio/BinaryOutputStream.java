/*
 * The MIT License
 *
 * Copyright 2022 AurumsMods.
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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * A binary output stream provides methods to write primitive data types from an
 * underlying input stream. The order in which the bytes are interpreted can be
 * either little endian or big endian (see {@link ByteOrder}).
 * <p>
 * This implements {@link DataOutput} but intentionally violates the
 * specifications prescribed by the contract. This is because {@code DataInput}
 * explicitly states the implementation of big-endian byte ordering.
 * <p>
 * {@code BinaryOutputStream} is not necessarily thread-safe due to it using a
 * buffer to write multiple bytes at once.
 */
public class BinaryOutputStream extends FilterOutputStream implements DataOutput, ByteOrdered {
	/** Specifies in what order the bytes should be interpreted. */
	private ByteOrder byteOrder;

	/** Temporary working buffer that can hold up to 8 bytes. */
	private final byte[] buffer = new byte[8];

	/** Number of bytes written to the output stream. Caps at Integer.MAX_VALUE */
	protected int written;

	/**
	 * Creates a new {@link BinaryOutputStream} that uses the given underlying
	 * {@link OutputStream}. The system's native byte order will be used when
	 * writing data.
	 *
	 * @param out the {@link OutputStream} to write to.
	 * @see ByteOrder#nativeOrder()
	 */
	public BinaryOutputStream(OutputStream out) {
		super(Objects.requireNonNull(out));
		byteOrder = ByteOrder.nativeOrder();
	}

	/**
	 * Creates a new {@link BinaryOutputStream} that uses the given underlying
	 * {@link OutputStream}. The specified byte order will be used when writing
	 * data.
	 *
	 * @param out       the {@link OutputStream} to write to.
	 * @param byteOrder the {@link ByteOrder} that specifies how to write data.
	 * @throws NullPointerException if {@code byteOrder} is {@code null}.
	 */
	public BinaryOutputStream(OutputStream out, ByteOrder byteOrder) {
		super(Objects.requireNonNull(out));
		this.byteOrder = Objects.requireNonNull(byteOrder);
	}

	/**
	 * Returns the {@link ByteOrder}.
	 *
	 * @return the {@link ByteOrder}.
	 */
	@Override
	public ByteOrder getOrder() {
		return byteOrder;
	}

	/**
	 * Sets the {@link ByteOrder} to the specified value. If the value is
	 * {@code null}, a {@code NullPointerException} is thrown.
	 *
	 * @param byteOrder the {@link ByteOrder}.
	 * @throws NullPointerException if {@code byteOrder} is {@code null}.
	 */
	@Override
	public void setOrder(ByteOrder byteOrder) {
		this.byteOrder = Objects.requireNonNull(byteOrder);
	}

	/**
	 * Increases the counter of written bytes by the specified value. If the counter
	 * overflows, the value will be capped at Integer.MAX_VALUE.
	 *
	 * @param value the number to increase the counter by.
	 */
	protected void incWritten(int value) {
		int newVal = written + value;
		written = newVal >= 0 ? newVal : Integer.MAX_VALUE;
	}

	/**
	 * Returns the number of bytes written to this stream so far. If the value
	 * overflows, it will be capped at Integer.MAX_VALUE.
	 *
	 * @return the number of bytes written to this stream so far.
	 */
	public final int size() {
		return written;
	}

	/**
	 * Writes a {@code boolean} to the output stream. The value {@code true} is
	 * written out as {@code (byte) 1}; the value {@code false} is written out as
	 * {@code (byte) 0}. If no exception is thrown, {@code written} is incremented
	 * by 1.
	 *
	 * @param v the {@code boolean} value to be written.
	 * @throws IOException if an I/O error occurs.
	 */
	@Override
	public final void writeBoolean(boolean v) throws IOException {
		out.write(v ? 1 : 0);
		incWritten(1);
	}

	/**
	 * Writes a {@code byte} value to the output stream. If no exception is thrown,
	 * {@code written} is incremented by 1.
	 *
	 * @param v the {@code byte} value.
	 * @throws IOException if an I/O error occurs.
	 */
	@Override
	public final void writeByte(int v) throws IOException {
		out.write(v);
		incWritten(1);
	}

	/**
	 * Writes a {@code short} value to the output stream. If no exception is thrown,
	 * {@code written} is incremented by 2.
	 *
	 * @param v the {@code short} value.
	 * @throws IOException if an I/O error occurs.
	 * @see BitConverter#putShort(byte[], int, ByteOrder, short)
	 */
	@Override
	public final void writeShort(int v) throws IOException {
		BitConverter.putShort(buffer, 0, byteOrder, (short) v);
		out.write(buffer, 0, 2);
		incWritten(2);
	}

	/**
	 * Writes a {@code char} value to the output stream. If no exception is thrown,
	 * {@code written} is incremented by 2.
	 *
	 * @param v the {@code char} value.
	 * @throws IOException if an I/O error occurs.
	 * @see BitConverter#putChar(byte[], int, ByteOrder, char)
	 */
	@Override
	public final void writeChar(int v) throws IOException {
		BitConverter.putChar(buffer, 0, byteOrder, (char) v);
		out.write(buffer, 0, 2);
		incWritten(2);
	}

	/**
	 * Writes a {@code int} value to the output stream. If no exception is thrown,
	 * {@code written} is incremented by 4.
	 *
	 * @param v the {@code int} value.
	 * @throws IOException if an I/O error occurs.
	 * @see BitConverter#putInt(byte[], int, ByteOrder, int)
	 */
	@Override
	public final void writeInt(int v) throws IOException {
		BitConverter.putInt(buffer, 0, byteOrder, v);
		out.write(buffer, 0, 4);
		incWritten(4);
	}

	/**
	 * Writes a {@code long} value to the output stream. If no exception is thrown,
	 * {@code written} is incremented by 8.
	 *
	 * @param v the {@code long} value.
	 * @throws IOException if an I/O error occurs.
	 * @see BitConverter#putLong(byte[], int, ByteOrder, long)
	 */
	@Override
	public final void writeLong(long v) throws IOException {
		BitConverter.putLong(buffer, 0, byteOrder, v);
		out.write(buffer, 0, 8);
		incWritten(8);
	}

	/**
	 * Writes a {@code float} value to the output stream. If no exception is thrown,
	 * {@code written} is incremented by 4.
	 *
	 * @param v the {@code float} value.
	 * @throws IOException if an I/O error occurs.
	 * @see BitConverter#putFloat(byte[], int, ByteOrder, float)
	 */
	@Override
	public final void writeFloat(float v) throws IOException {
		BitConverter.putFloat(buffer, 0, byteOrder, v);
		out.write(buffer, 0, 4);
		incWritten(4);
	}

	/**
	 * Writes a {@code double} value to the output stream. If no exception is
	 * thrown, {@code written} is incremented by 8.
	 *
	 * @param v the {@code double} value.
	 * @throws IOException if an I/O error occurs.
	 * @see BitConverter#putDouble(byte[], int, ByteOrder, double)
	 */
	@Override
	public final void writeDouble(double v) throws IOException {
		BitConverter.putDouble(buffer, 0, byteOrder, v);
		out.write(buffer, 0, 8);
		incWritten(8);
	}

	/**
	 * Writes a string to the output stream as a sequence of bytes. For every
	 * character in the string the low eight bits are written. If no exception is
	 * thrown, the counter {@code written} is incremented by the length of
	 * {@code s}.
	 *
	 * @param s a {@code String} value to be written.
	 * @throws IOException if an I/O error occurs.
	 */
	@Override
	public final void writeBytes(String s) throws IOException {
		char[] chars = s.toCharArray();
		int len = chars.length;

		for (int i = 0; i < len; i++) {
			out.write((byte) chars[i]);
		}

		incWritten(len);
	}

	/**
	 * Writes a string to the output stream as a sequence of characters. Each
	 * character is written to the data output stream using {@link #writeChar(int)}.
	 * If no exception is thrown, {@code written} is incremented by twice the length
	 * of {@code s}.
	 *
	 * @param s a {@code String} value to be written.
	 * @throws IOException if an I/O error occurs.
	 */
	@Override
	public final void writeChars(String s) throws IOException {
		char[] chars = s.toCharArray();
		int len = chars.length;

		for (int i = 0; i < len; i++) {
			writeChar(chars[i]);
		}

		incWritten(len * 2);
	}

	/**
	 * Writes a string to the output stream using modified UTF-8 encoding. This
	 * calls {@link DataOutputStream#writeUTF(String)}. See the class documentation
	 * for {@link DataInput} for details on the modified UTF-8 format. If no
	 * exception is thrown, {@code written} is incremented by the amount of encoded
	 * bytes written.
	 *
	 * @param s the {@code String} value.
	 * @throws IOException if an I/O error occurs.
	 */
	@Override
	public final void writeUTF(String s) throws IOException {
		((DataOutputStream) out).writeUTF(s);
	}

}
