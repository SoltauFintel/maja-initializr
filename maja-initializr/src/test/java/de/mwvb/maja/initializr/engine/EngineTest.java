package de.mwvb.maja.initializr.engine;

import org.junit.Test;

public class EngineTest {

	@Test
	public void crate() {
		EngineOptions o = new EngineOptions();
		o.setProjectName("Django");
		o.setPackageName("com.anything.django");
		o.setAppClassName("DjangoApp");
		o.setAuthor("Mister Django");
		o.setMajaAuthMongo(true);
		o.setFacebookLogin(true);
		o.setGoogleLogin(true);
		o.setMajaRedis(true);
		o.setIntercoolerJS(true);
		o.setPort(9022);
		o.setTheme("sbadmin2");
		new Engine(o).generate();
	}
}
