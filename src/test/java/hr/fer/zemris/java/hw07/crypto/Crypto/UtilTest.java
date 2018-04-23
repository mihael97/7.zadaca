package hr.fer.zemris.java.hw07.crypto.Crypto;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class UtilTest {

	@Test
	public void hextobyte1() {
		assertArrayEquals(new byte[] { 1, -82, 34 }, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void hextobyte2() {
		assertArrayEquals(new byte[] {}, Util.hextobyte(""));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hextobyte3() {
		Util.hextobyte("ae\\k");
	}
	
	
	@Test
	public void bytehex1() {
		assertEquals("01ae22",Util.bytetohex( new byte[] { 1, -82, 34 }));
	}
	
	@Test
	public void bytetohex2() {
		assertEquals("", Util.bytetohex(new byte[] {}));
	}
}
