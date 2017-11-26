package de.mwvb.maja.initializr.engine;

import org.junit.Test;

public class EngineTest {

	@Test
	public void crate() {
		EngineOptions o = new EngineOptions();
		o.setProjectName("Test");
		o.setPackageName("de.mwvb.test");
		o.setAppClassName("TestApp");
		o.setAuthor("Max Mustermann");
		new Engine(o).generate();
	}
}
