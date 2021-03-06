package de.mwvb.maja.initializr.engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import de.mwvb.maja.web.Template;
import spark.ModelAndView;

public class Engine {
	private final EngineOptions options;
	private Map<String, Object> model;
	private String outputRoot = "output/";
	
	public Engine(EngineOptions options) {
		this.options = options;
	}
	
	public void generate() {
		options.validate();
		model = options.getModel();
		
		// Eclipse files
		create("project.txt", ".project");
		// wird erstmal nicht erstellt, soll durch Gradle/eclipse erzeugt werden -- create("classpath.txt", ".classpath");
		
		// Build
		create("build/build.gradle", "build.gradle");
		create("build/build.xml", "build.xml");
		copy("build/gradlew.bat", "gradlew.bat");
		copy("build/gradle-wrapper.jar", "gradle/wrapper/gradle-wrapper.jar");
		copy("build/gradle-wrapper.properties", "gradle/wrapper/gradle-wrapper.properties");
		create("build/gitignore", ".gitignore");
		
		// HTML
		create("html/index.html", "$R/templates/index.html");
		create("html/login.html", "$R/templates/login.html", options.isMajaAuth());
		create("html/master.html", "$R/templates/master.html");
		create("html/menu.html", "$R/templates/menu.html");
		create("html/banner.txt", "$R/banner.txt");
		
		// Classes
		create("App.java.txt", "$J/$P/" + options.getAppClassName() + ".java");
		create("Index.java.txt", "$J/$P/actions/Index.java");
		
		// Config
		create("AppConfig.properties", "AppConfig.properties");
		new File(outputRoot + options.getProjectName().toLowerCase() + "/src/test/java").mkdirs();
		new File(outputRoot + options.getProjectName().toLowerCase() + "/src/main/resources/web").mkdirs();
	}
	
	private void create(String template, String outputFilename) {
		create(template, outputFilename, true);
	}
	
	private void create(String template, String outputFilename, boolean create) {
		outputFilename = completeOutputFilename(outputFilename);
		if (create) {
			String text = Template.render(new ModelAndView(model, "templates/" + template));
			writeFile(text, outputFilename);
		} else {
			new File(outputFilename).delete();
		}
	}

	private void writeFile(String text, String outputFilename) {
		try {
			FileWriter w = new FileWriter(out(outputFilename));
			w.write(text);
			w.close();
		} catch (IOException e) {
			throw new RuntimeException("Error creating " + outputFilename, e);
		}
	}

	private String completeOutputFilename(String outputFilename) {
		outputFilename = outputFilename.replace("$J", "src/main/java");
		outputFilename = outputFilename.replace("$P", options.getPackageName().replace(".", "/"));
		outputFilename = outputFilename.replace("$R", "src/main/resources");
		return outputFilename;
	}
	
	private void copy(String src, String dest) {
		try {
			File srcFile = new File("src/main/resources/templates/" + src); // TODO That does not work in a JAR.
			com.google.common.io.Files.copy(srcFile, out(dest));
		} catch (IOException e) {
			throw new RuntimeException("Error copying " + dest, e);
		}
	}
	
	private File out(String outputFilename) {
		File file = new File(outputRoot + options.getProjectName().toLowerCase() + "/" + outputFilename);
		file.getParentFile().mkdirs();
		return file;
	}

	public String getOutputRoot() {
		return outputRoot;
	}

	public void setOutputRoot(String outputRoot) {
		this.outputRoot = outputRoot;
	}
}
