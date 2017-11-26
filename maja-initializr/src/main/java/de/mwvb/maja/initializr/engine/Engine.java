package de.mwvb.maja.initializr.engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import de.mwvb.maja.web.Template;
import spark.ModelAndView;

public class Engine {
	private final EngineOptions options;
	private Map<String, String> model;
	private String outputRoot = "output/";
	
	public Engine(EngineOptions options) {
		this.options = options;
	}
	
	public void generate() {
		options.validate();
		model = options.getMap();
		
		// Eclipse files
		create("project.txt", ".project");
		create("classpath.txt", ".classpath");
		
		// Build
		create("build/build.gradle", "build.gradle");
		create("build/build.xml", "build.xml");
		copy("build/gradlew.bat", "gradlew.bat");
		copy("build/gradle-wrapper.jar", "gradle/wrapper/gradle-wrapper.jar");
		copy("build/gradle-wrapper.properties", "gradle/wrapper/gradle-wrapper.properties");
		
		// HTML
		copyTemplate("html/index.html", "$R/templates/index.html");
		copyTemplate("html/login.html", "$R/templates/login.html");
		copyTemplate("html/master.html", "$R/templates/master.html");
		copyTemplate("html/menu.html", "$R/templates/menu.html");
		copyTemplate("html/banner.txt", "$R/banner.txt");
		
		// Classes
		create("App.java.txt", "$J/$P/" + options.getAppClassName() + ".java");
		create("Index.java.txt", "$J/$P/actions/Index.java");
		
		// Config
		create("AppConfig.properties", "AppConfig.properties");
	}
	
	private void create(String template, String outputFilename) {
		outputFilename = completeOutputFilename(outputFilename);
		
		String text = Template.render(new ModelAndView(model, "templates/" + template));
		writeFile(text, outputFilename);
	}

	private void copyTemplate(String template, String outputFilename) {
		outputFilename = completeOutputFilename(outputFilename);
		
		try {
			String text = new String(Files.readAllBytes(r(template).toPath()));
			text = text.replace("[projectName]", options.getProjectName());
			writeFile(text, outputFilename);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error create special file " + outputFilename, e);
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
			com.google.common.io.Files.copy(r(src), out(dest));
		} catch (IOException e) {
			throw new RuntimeException("Error copying " + dest, e);
		}
	}
	
	private File r(String dn) {
		return new File("src/main/resources/templates/" + dn);
	}
	
	private File out(String outputFilename) {
		File file = new File(outputRoot + options.getProjectName() + "/" + outputFilename);
		file.getParentFile().mkdirs();
		return file;
	}
	// ## Maja initializr Webapp ##
	// 
	// Auswahl der Module: web, auth, auth-mongo, mongo, redis
	// - Project Name, App-Klasse Name, Package Name
	// - SB Admin 2 verwenden?
	// - Quartz verwenden?
	// - Push
	// - Port
	
	// Aktionen:
	// - Eclipse files anlegen
	// - Gradle Dateien anlegen
	// - App Klasse anlegen
	// - Index Klasse samt index.html anlegen
	// - AppConfig.properties anlegen
	// - Auswahl Facebook, Google, only one user
	// - banner.txt generieren
	// - vielleicht kann man ein favicon generieren wo nur ein Buchstabe drin ist. Farbig. Quadrat oder Kreis.
	//   Das sprengt aber den Rahmen.
	// - Timer Klasse anlegen
	// - Push Klasse anlegen
	
	
	
	
}
