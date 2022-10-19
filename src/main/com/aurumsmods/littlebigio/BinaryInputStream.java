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

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * A binary input stream provides readability of primitive data types from an
 * underlying input stream. The order in which the bytes are interpreted can be
 * either little endian or big endian (see {@link ByteOrder}).
 * <p>
 * This implements {@link DataInput} but intentionally violates the
 * specifications prescribed by the contract. This is because {@code DataInput}
 * explicitly states the implementation of big-endian byte ordering.
 * <p>
 * {@code BinaryInputStream} is not necessarily thread-safe due to it using a
 * buffer to read multiple bytes at once.
 */
public class BinaryInputStream extends FilterInputStream implements DataInput, ByteOrdered, UnsignedIntInput {
	/** Specifies in what order the bytes should be interpreted. */
	private ByteOrder byteOrder;

	/** Temporary working buffer that can hold up to 8 bytes. */
	private final byte[] buffer = new byte[8];

	/**
	 * Creates a new {@link BinaryInputStream} that uses the given underlying
	 * {@link InputStream}. The system's native byte order will be used when reading
	 * data.
	 *
	 * @param in the {@link InputStream} to read from.
	 * @see ByteOrder#nativeOrder()
	 */
	public BinaryInputStream(InputStream in) {
		super(Objects.requireNonNull(in));
		byteOrder = ByteOrder.nativeOrder();
	}

	/**
	 * Creates a new {@link BinaryInputStream} that uses the given underlying
	 * {@link InputStream}. The specified byte order will be used when reading data.
	 *
	 * @param in        the {@link InputStream} to read from.
	 * @param byteOrder the {@link ByteOrder} that specifies how to read data.
	 * @throws NullPointerException if {@code byteOrder} is {@code null}.
	 */
	public BinaryInputStream(InputStream in, ByteOrder byteOrder) {
		super(Objects.requireNonNull(in));
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
	 * Reads the specified number of bytes into the working buffer.
	 *
	 * @param n the number of bytes to be read.
	 * @throws IOException if an I/O error occurs.
	 */
	private void readIntoBuffer(int n) throws IOException {
		int read = in.readNBytes(buffer, 0, n);

		if (read != n) {
			throw new EOFException();
		}
	}

	/**
	 * Reads some bytes from the input stream into the given byte array. The number
	 * of bytes read is equal to the length of b. This method blocks until
	 * {@code len} bytes of input data have been read, end of stream is detected, or
	 * an exception is thrown. This does not close the stream.
	 *
	 * @param b the byte array into which the data is read.
	 * @throws NullPointerException if {@code b} is {@code null}.
	 * @throws IOException          if an I/O error occurs.
	 * @throws EOFException         if the input stream reaches the end before
	 *                              reading all bytes.
	 * @see InputStream#readNBytes(byte[], int, int)
	 */
	@Override
	public void readFully(byte[] b) throws IOException {
		in.readNBytes(b, 0, b.length);
	}

	/**
	 * Reads the specified number of bytes from the input stream into the given byte
	 * array. This method blocks until {@code len} bytes of input data have been
	 * read, end of stream is detected, or an exception is thrown. This does not
	 * close the stream.
	 *
	 * @param b   the byte array into which the data is read.
	 * @param off the offset in {@code b} at which the data is written.
	 * @param len the maximum number of bytes to read.
	 * @throws NullPointerException      if {@code b} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} is negative, {@code len} is
	 *                                   negative, or {@code len} is greater than
	 *                                   {@code b.length - off}.
	 * @throws IOException               if an I/O error occurs.
	 * @throws EOFException              if the input stream reaches the end before
	 *                                   reading all bytes.
	 * @see InputStream#readNBytes(byte[], int, int)
	 */
	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		in.readNBytes(b, off, len);
	}

	/**
	 * Skips over and discards exactly {@code n} bytes of data from this input
	 * stream. If {@code n} is zero or negative, then no bytes are skipped. This
	 * method blocks until {@code n} bytes have been skipped, end of stream is
	 * detected, or an exception is thrown.
	 *
	 * @param n the number of bytes to skip.
	 * @return the number of bytes that were skipped.
	 * @throws IOException if an I/O error occurs.
	 */
	@Override
	public int skipBytes(int n) throws IOException {
		if (n < 1) {
			return 0;
		}

		int total = 0;
		int cur;

		while (total < n && (cur = (int) in.skip((long) n - total)) > 0) {
			total += cur;
		}

		return total;
	}

	/**
	 * Reads one input byte from the input stream. and returns {@code true} if that
	 * byte is nonzero, {@code false} if that byte is zero.
	 *
	 * @return the {@code boolean} value read.
	 * @throws EOFException if this stream reached the end before reading the byte.
	 * @throws IOException  if an I/O error occurs.
	 */
	@Override
	public boolean readBoolean() throws IOException {
		int ch = in.read();

		if (ch < 0) {
			throw new EOFException();
		}

		return ch != 0;
	}

	/**
	 * Reads and returns one signed {@code byte} from the input stream.
	 *
	 * @return the signed {@code byte} value read.
	 * @throws EOFException if this stream reached the end before reading the byte.
	 * @throws IOException  if an I/O error occurs.
	 */
	@Override
	public byte readByte() throws IOException {
		int ch = in.read();

		if (ch < 0) {
			throw new EOFException();
		}

		return (byte) ch;
	}

	/**
	 * Reads and returns one unsigned {@code byte} from the input stream.
	 *
	 * @return the unsigned {@code byte} value read.
	 * @throws EOFException if this stream reached the end before reading the byte.
	 * @throws IOException  if an I/O error occurs.
	 */
	@Override
	public int readUnsignedByte() throws IOException {
		int ch = in.read();

		if (ch < 0) {
			throw new EOFException();
		}

		return ch;
	}

	/**
	 * Reads two bytes from the input stream and returns the signed {@code short}
	 * value converted from these bytes.
	 *
	 * @return the signed {@code short} value read.
	 * @throws EOFException if this stream reached the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 * @see BitConverter#getShort(byte[], int, ByteOrder)
	 */
	@Override
	public short readShort() throws IOException {
		readIntoBuffer(2);
		return BitConverter.getShort(buffer, 0, byteOrder);
	}

	/**
	 * Reads two bytes from the input stream and returns the unsigned {@code short}
	 * value converted from these bytes.
	 *
	 * @return the unsigned {@code short} value read.
	 * @throws EOFException if this stream reached the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 * @see BitConverter#getUnsignedShort(byte[], int, ByteOrder)
	 */
	@Override
	public int readUnsignedShort() throws IOException {
		readIntoBuffer(2);
		return BitConverter.getUnsignedShort(buffer, 0, byteOrder);
	}

	/**
	 * Reads two bytes from the input stream and returns the {@code char} value
	 * converted from these bytes.
	 *
	 * @return the {@code char} value read.
	 * @throws EOFException if this stream reached the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 */
	@Override
	public char readChar() throws IOException {
		return (char) readShort();
	}

	/**
	 * Reads four bytes from the input stream and returns the signed {@code int}
	 * value converted from these bytes.
	 *
	 * @return the signed {@code int} value read.
	 * @throws EOFException if this stream reached the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 * @see BitConverter#getInt(byte[], int, ByteOrder)
	 */
	@Override
	public int readInt() throws IOException {
		readIntoBuffer(4);
		return BitConverter.getInt(buffer, 0, byteOrder);
	}

	/**
	 * Reads four bytes from the input stream and returns the unsigned {@code int}
	 * value converted from these bytes.
	 *
	 * @return the unsigned {@code int} value read.
	 * @throws EOFException if this stream reached the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 * @see BitConverter#getUnsignedInt(byte[], int, ByteOrder)
	 */
	@Override
	public long readUnsignedInt() throws IOException {
		readIntoBuffer(4);
		return BitConverter.getUnsignedInt(buffer, 0, byteOrder);
	}

	/**
	 * Reads eight bytes from the input stream and returns the signed {@code long}
	 * value converted from these bytes.
	 *
	 * @return the signed {@code long} value read.
	 * @throws EOFException if this stream reached the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 * @see BitConverter#getLong(byte[], int, ByteOrder)
	 */
	@Override
	public long readLong() throws IOException {
		readIntoBuffer(8);
		return BitConverter.getLong(buffer, 0, byteOrder);
	}

	/**
	 * Reads four bytes from the input stream and returns the {@code float} value
	 * converted from these bytes.
	 *
	 * @return the {@code float} value read.
	 * @throws EOFException if this stream reached the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 * @see BitConverter#getFloat(byte[], int, ByteOrder)
	 */
	@Override
	public float readFloat() throws IOException {
		readIntoBuffer(4);
		return BitConverter.getFloat(buffer, 0, byteOrder);
	}

	/**
	 * Reads eight bytes from the input stream and returns the {@code double} value
	 * converted from these bytes.
	 *
	 * @return the {@code double} value read.
	 * @throws EOFException if this stream reached the end before reading all the
	 *                      bytes.
	 * @throws IOException  if an I/O error occurs.
	 * @see BitConverter#getDouble(byte[], int, ByteOrder)
	 */
	@Override
	public double readDouble() throws IOException {
		readIntoBuffer(8);
		return BitConverter.getDouble(buffer, 0, byteOrder);
	}

	/**
	 * Not implemented: use {@link BufferedReader} instead. This function always
	 * throws a {@link UnsupportedOperationException} when invoked.
	 *
	 * @deprecated Use {@link BufferedReader#readLine()} instead.
	 * @return n/a
	 * @throws IOException                   n/a
	 * @throws UnsupportedOperationException always thrown.
	 */
	@Override
	@Deprecated
	public String readLine() throws IOException {
		throw new UnsupportedOperationException("Deprecated; use BufferedReader instead");
	}

	/**
	 * Reads and returns a Unicode character string encoded in modified UTF-8 format
	 * from the input stream. This calls {@link DataInputStream#readUTF(DataInput)}.
	 * See the class documentation for {@link DataInput} for details on the modified
	 * UTF-8 format.
	 *
	 * @return the Unicode string read.
	 * @throws IOException if an I/O error occurs.
	 */
	@Override
	public String readUTF() throws IOException {
		return DataInputStream.readUTF(this);
	}

}
