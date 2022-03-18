# littlebigIO
**littlebigIO** is a small Java library that provides I/O streams and bit-conversion utilities with changeable endianness. This is necessary because Java's standard libraries are mostly limited to big-endian data only and do not feature [BitConverter](https://www.google.com/search?q=c%23+bitconverter&rlz=1C1CHBF_deDE926DE926&oq=c%23+bitconverter&aqs=chrome.0.0i512l4j0i22i30l3j69i58.1743j0j4&sourceid=chrome&ie=UTF-8)-like functions to treat binary data with different byte orders. Furthermore, Java's [DataInput](https://docs.oracle.com/javase/8/docs/api/java/io/DataInput.html) contract strictly prescribes the implementation of big-endian data only. *littlebigIO* intentionally violates this contract to implement the interface. The library uses Maven to generate usable builds and is built around Java 17 although it may work with older versions.

## Overview
The libraries key features are:
- **BinaryInputStream**: Input stream to read primary data types with variable endianness.
- **BinaryOutputStream**: Output stream to write primary data types with variable endianness.
- **BitConverter**: Provides methods to convert between bytes and primary data types.
- **ByteOrderUtil**: Utility functions to deal with byte orders and their BOMs.

## Documentation
The Javadocs for this library can be found [on my website](https://aurumsmods.com/littlebigIO/com/aurumsmods/littlebigio/package-summary.html).