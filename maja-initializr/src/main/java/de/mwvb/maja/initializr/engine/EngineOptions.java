package de.mwvb.maja.initializr.engine;

import java.util.HashMap;
import java.util.Map;

public class EngineOptions {
	/** a-z, 0-9, "-" */
	private String projectName;
	/** with dots, not with slashes */
	private String packageName;
	/** ends usually with 'App' */
	private String appClassName;
	private int port = 8080;
	private boolean majaAuth = false;
	private boolean majaAuthMongo = false;
	private boolean majaMongo = false;
	private boolean majaRedis = false;
	/** e.g. "SBAdmin2" */
	private String theme;
	private boolean facebookLogin = false;
	private boolean googleLogin = false;
	/** e.g. "Facebook#4711" */
	private String singleUser;
	private String author = "YOUR_NAME";
	private String dockerRegistry = "mwvb.de:5000";
	
	public Map<String, Object> getModel() {
		Map<String, Object> model = new HashMap<>();
		model.put("theProjectName", projectName);
		model.put("projectName", projectName.toLowerCase());
		model.put("packageName", packageName);
		model.put("appClassName", appClassName);
		model.put("port", port);
		model.put("author", author);
		model.put("dockerRegistry", dockerRegistry);
		model.put("dependencies", getDependencies());
		model.put("master", "#@master()");
		model.put("auth", isMajaAuth());
		model.put("authMongo", isMajaAuthMongo());
		model.put("mongo", isMajaMongo());
		model.put("redis", isMajaRedis());
		model.put("facebook", isFacebookLogin());
		model.put("google", isGoogleLogin());
		model.put("sbadmin2", "SBAdmin2".equalsIgnoreCase(theme));
		model.put("singleUser", singleUser);
		return model;
	}
	
	private String getDependencies() {
		String artifacts = "[de.mwvb.maja:maja-web:0.1.2]";
		if (isMajaAuth()) {
			artifacts += ",[de.mwvb.maja:maja-auth:0.1.3]";
		}
		if (isMajaAuthMongo()) {
			artifacts += ",[de.mwvb.maja:maja-auth-mongo:0.1.3]";
		}
		if (isMajaMongo()) {
			artifacts += ",[de.mwvb.maja:maja-mongo:0.1.3]";
		}
		if (isMajaRedis()) {
			artifacts += ",[de.mwvb.maja:maja-redis:0.1.0]";
		}
		return artifacts.replace("[", "\tcompile '").replace("]", "'").replace(",", "\r\n");
	}

	public void validate() {
		if (projectName == null || projectName.trim().isEmpty()) {
			throw new RuntimeException("projectName must not be empty!");
		}
		if (packageName == null || packageName.trim().isEmpty()) {
			throw new RuntimeException("packageName must not be empty!");
		}
		if (appClassName == null || appClassName.trim().isEmpty()) {
			throw new RuntimeException("appClassName must not be empty!");
		}
		if (author == null || author.trim().isEmpty()) {
			throw new RuntimeException("author must not be empty!");
		}
	}
	
	public boolean isMajaAuth() {
		return majaAuth;
	}

	public void setMajaAuth(boolean majaAuth) {
		this.majaAuth = majaAuth;
	}

	public boolean isMajaAuthMongo() {
		return majaAuthMongo;
	}

	public void setMajaAuthMongo(boolean majaAuthMongo) {
		this.majaAuthMongo = majaAuthMongo;
	}

	public boolean isMajaMongo() {
		return majaMongo || majaAuthMongo;
	}

	public void setMajaMongo(boolean majaMongo) {
		this.majaMongo = majaMongo;
	}

	public boolean isMajaRedis() {
		return majaRedis;
	}

	public void setMajaRedis(boolean majaRedis) {
		this.majaRedis = majaRedis;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAppClassName() {
		return appClassName;
	}

	public void setAppClassName(String appClassName) {
		this.appClassName = appClassName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isFacebookLogin() {
		return facebookLogin;
	}

	public void setFacebookLogin(boolean facebookLogin) {
		this.facebookLogin = facebookLogin;
	}

	public boolean isGoogleLogin() {
		return googleLogin;
	}

	public void setGoogleLogin(boolean googleLogin) {
		this.googleLogin = googleLogin;
	}

	public String getSingleUser() {
		return singleUser;
	}

	public void setSingleUser(String singleUser) {
		this.singleUser = singleUser;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDockerRegistry() {
		return dockerRegistry;
	}

	public void setDockerRegistry(String dockerRegistry) {
		this.dockerRegistry = dockerRegistry;
	}
}
