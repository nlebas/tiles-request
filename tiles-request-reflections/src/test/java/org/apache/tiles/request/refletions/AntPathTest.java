package org.apache.tiles.request.refletions;

import org.apache.tiles.request.reflections.AntPath;
import org.junit.Assert;
import org.junit.Test;

public class AntPathTest {
	@Test
	public void testBasic() {
		AntPath antPath = new AntPath("/org/apache/tiles/Test.xml");
		Assert.assertTrue(antPath.matches("/org/apache/tiles/Test.xml"));
		Assert.assertFalse(antPath.matches("/org/apache/tiles/Test_xml"));
		Assert.assertTrue(antPath.matches("org/apache/tiles/Test.xml"));
		Assert.assertTrue(antPath.matches("///org//apache/tiles///Test.xml"));
		Assert.assertTrue(antPath.matches("\\org\\apache\\tiles\\\\Test.xml"));
		Assert.assertFalse(antPath.matches("/orgapache/tiles/Test.xml"));
	}
	
	@Test
	public void testWildcard1() {
		AntPath antPath = new AntPath("/org/apache/*/Test.xml");
		Assert.assertTrue(antPath.matches("/org/apache/tiles/Test.xml"));
		Assert.assertFalse(antPath.matches("/org/apache/tiles/Test_xml"));
		Assert.assertTrue(antPath.matches("org/apache/tiles/Test.xml"));
		Assert.assertTrue(antPath.matches("///org//apache/tiles///Test.xml"));
		Assert.assertTrue(antPath.matches("\\org\\apache\\tiles\\\\Test.xml"));
		Assert.assertFalse(antPath.matches("/orgapache/tiles/Test.xml"));
	}
	
	@Test
	public void testWildcard2() {
		AntPath antPath = new AntPath("/org/apache/tiles/*.xml");
		Assert.assertTrue(antPath.matches("/org/apache/tiles/Test.xml"));
		Assert.assertFalse(antPath.matches("/org/apache/tiles/Test_xml"));
		Assert.assertTrue(antPath.matches("org/apache/tiles/Test.xml"));
		Assert.assertTrue(antPath.matches("///org//apache/tiles///Test.xml"));
		Assert.assertTrue(antPath.matches("\\org\\apache\\tiles\\\\Test.xml"));
		Assert.assertFalse(antPath.matches("/orgapache/tiles/Test.xml"));
	}
	
	@Test
	public void testWildcard3() {
		AntPath antPath = new AntPath("/org/apache/tiles/Test.*");
		Assert.assertTrue(antPath.matches("/org/apache/tiles/Test.xml"));
		Assert.assertFalse(antPath.matches("/org/apache/tiles/Test_xml"));
		Assert.assertTrue(antPath.matches("org/apache/tiles/Test.xml"));
		Assert.assertTrue(antPath.matches("///org//apache/tiles///Test.xml"));
		Assert.assertTrue(antPath.matches("\\org\\apache\\tiles\\\\Test.xml"));
		Assert.assertFalse(antPath.matches("/orgapache/tiles/Test.xml"));
	}

	@Test
	public void testWildcard4() {
		AntPath antPath = new AntPath("/org/apache/tiles/Test.?ml");
		Assert.assertTrue(antPath.matches("/org/apache/tiles/Test.xml"));
		Assert.assertFalse(antPath.matches("/org/apache/tiles/Test_xml"));
		Assert.assertTrue(antPath.matches("org/apache/tiles/Test.xml"));
		Assert.assertTrue(antPath.matches("///org//apache/tiles///Test.xml"));
		Assert.assertTrue(antPath.matches("\\org\\apache\\tiles\\\\Test.xml"));
		Assert.assertFalse(antPath.matches("/orgapache/tiles/Test.xml"));
	}
	
	@Test
	public void testWildcard5() {
		AntPath antPath = new AntPath("/org/apache/tiles/Test?xml");
		Assert.assertTrue(antPath.matches("/org/apache/tiles/Test.xml"));
		Assert.assertTrue(antPath.matches("/org/apache/tiles/Test_xml"));
		Assert.assertTrue(antPath.matches("org/apache/tiles/Test.xml"));
		Assert.assertTrue(antPath.matches("///org//apache/tiles///Test.xml"));
		Assert.assertTrue(antPath.matches("\\org\\apache\\tiles\\\\Test.xml"));
		Assert.assertFalse(antPath.matches("/orgapache/tiles/Test.xml"));
	}

	@Test
	public void testEscape() {
		AntPath antPath = new AntPath("/org/apache/tiles/Test\\*.xml");
		Assert.assertFalse(antPath.matches("/org/apache/tiles/Test.xml"));
		Assert.assertTrue(antPath.matches("/org/apache/tiles/Test*.xml"));
	}
}
