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
		o.setMajaMongo(true);
//		o.setMajaAuth(true);
//		o.setMajaAuthMongo(true);
//		o.setFacebookLogin(true);
//		o.setGoogleLogin(false);
//		o.setMajaRedis(true);
//		o.setIntercoolerJS(false);
//		o.setTheme("sbadmin2");
		o.setPort(9013);
		Engine e = new Engine(o);
		e.setOutputRoot("C:\\_workspaces\\oxygen\\");
		e.generate();
	}
}
