package com.aurumsmods.littlebigio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BinaryIOStreamTest {
	private final byte[] byteValues = { 1, -127, 78, 23 };
	private final int[] unsignedByteValues = { 1, 255, 129, 23 };
	private final short[] shortValues = { 1, 256, 4474, -32000 };
	private final int[] unsignedShortValues = { 1, 65535, 32000, 0 };
	private final int[] intValues = { 1, 48945, 398190874, -498373992 };
	private final long[] unsignedIntValues = { 1, -((long) Integer.MIN_VALUE), 0, 4294967295L };
	private final long[] longValues = { 1, Long.MAX_VALUE, Long.MIN_VALUE, -4386573894564L };
	private final float[] floatValues = { 0f, 1f, -1f, Float.MAX_VALUE, Float.MIN_VALUE, Float.POSITIVE_INFINITY,
			Float.NEGATIVE_INFINITY };
	private final double[] doubleValues = { 0f, 1f, -1f, Double.MAX_VALUE, Double.MIN_VALUE, Double.POSITIVE_INFINITY,
			Double.NEGATIVE_INFINITY };

	private ByteArrayInputStream pin;
	private ByteArrayOutputStream pout;
	private BinaryInputStream in;
	private BinaryOutputStream out;

	@BeforeEach
	void prepare() {
		pout = new ByteArrayOutputStream(256);
		out = new BinaryOutputStream(pout);
	}

	void setupInput() {
		pin = new ByteArrayInputStream(pout.toByteArray());
		in = new BinaryInputStream(pin);
	}

	@Test
	void testByteOrder() {
		setupInput();

		for (ByteOrdered ordered : new ByteOrdered[] { in, out }) {
			// Set big
			ordered.setOrder(ByteOrder.BIG_ENDIAN);
			assertEquals(ByteOrder.BIG_ENDIAN, ordered.getOrder());
			assertTrue(ordered.isBigEndian());
			assertFalse(ordered.isLittleEndian());

			// Set little
			ordered.setOrder(ByteOrder.LITTLE_ENDIAN);
			assertEquals(ByteOrder.LITTLE_ENDIAN, ordered.getOrder());
			assertFalse(ordered.isBigEndian());
			assertTrue(ordered.isLittleEndian());

			// Swap, little to big
			ordered.swapOrder();
			assertEquals(ByteOrder.BIG_ENDIAN, ordered.getOrder());
			assertTrue(ordered.isBigEndian());
			assertFalse(ordered.isLittleEndian());

			// Swap, big to little
			ordered.swapOrder();
			assertEquals(ByteOrder.LITTLE_ENDIAN, ordered.getOrder());
			assertFalse(ordered.isBigEndian());
			assertTrue(ordered.isLittleEndian());

			if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
				assertTrue(ordered.isNativeOrder());
				ordered.swapOrder();
				assertFalse(ordered.isNativeOrder());
			} else {
				assertFalse(ordered.isNativeOrder());
				ordered.swapOrder();
				assertTrue(ordered.isNativeOrder());
			}
		}
	}

	@Test
	void testByte() throws IOException {
		for (byte i : byteValues) {
			out.writeByte(i);
		}

		setupInput();

		for (byte i : byteValues) {
			assertEquals(i, in.readByte());
		}
	}

	@Test
	void testUnsignedByte() throws IOException {
		for (int i : unsignedByteValues) {
			out.writeByte(i);
		}

		setupInput();

		for (int i : unsignedByteValues) {
			assertEquals(i, in.readUnsignedByte());
		}
	}

	@Test
	void testShort() throws IOException {
		for (short i : shortValues) {
			out.writeShort(i);
		}

		setupInput();

		for (short i : shortValues) {
			assertEquals(i, in.readShort());
		}
	}

	@Test
	void testUnsignedShort() throws IOException {
		for (int i : unsignedShortValues) {
			out.writeShort(i);
		}

		setupInput();

		for (int i : unsignedShortValues) {
			assertEquals(i, in.readUnsignedShort());
		}
	}

	@Test
	void testInt() throws IOException {
		for (int i : intValues) {
			out.writeInt(i);
		}

		setupInput();

		for (int i : intValues) {
			assertEquals(i, in.readInt());
		}
	}

	@Test
	void testUnsignedInt() throws IOException {
		for (long i : unsignedIntValues) {
			out.writeInt((int) i);
		}

		setupInput();

		for (long i : unsignedIntValues) {
			assertEquals(i, in.readUnsignedInt());
		}
	}

	@Test
	void testLong() throws IOException {
		for (long i : longValues) {
			out.writeLong(i);
		}

		setupInput();

		for (long i : longValues) {
			assertEquals(i, in.readLong());
		}
	}

	@Test
	void testFloat() throws IOException {
		for (float i : floatValues) {
			out.writeFloat(i);
		}

		setupInput();

		for (float i : floatValues) {
			assertEquals(i, in.readFloat());
		}
	}

	@Test
	void testDouble() throws IOException {
		for (double i : doubleValues) {
			out.writeDouble(i);
		}

		setupInput();

		for (double i : doubleValues) {
			assertEquals(i, in.readDouble());
		}
	}
}
