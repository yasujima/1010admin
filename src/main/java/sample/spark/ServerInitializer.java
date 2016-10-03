package sample.spark;

import static spark.Spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import spark.ModelAndView;
import spark.template.thymeleaf.*;

import org.thymeleaf.context.*;
import org.thymeleaf.templateresolver.*;
import org.thymeleaf.resourceresolver.ClassLoaderResourceResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializer {

    public ServerInitializer() {

	StatTailer tailer = new StatTailer();
	tailer.start();
	LoggerFactory.getLogger(this.getClass()).info("XXXX tailer start");
	
	TemplateResolver resolv = new TemplateResolver();
	resolv.setTemplateMode("LEGACYHTML5");
	resolv.setPrefix("templates/");
	resolv.setSuffix(".html");
	resolv.setCacheTTLMs(3600000L);
	resolv.setResourceResolver(new ClassLoaderResourceResolver());

	configure();
	setSample(resolv);
	setRoute(resolv);
    }
    
    private void configure() {
	staticFileLocation("/webapp");
    }
    
    private void setSample(TemplateResolver resolv) {
	Map map = new HashMap();
	map.put("name", "Sam");
	map.put("shopName", "myBookStore");
	List<String> books
	    = Arrays.asList("java book", "php book", "python book", "Kotlin book");
	map.put("books", books);

	get("/hello", (req, res) -> "Hello Spark!!");

	get("/name/:name", (req, res) -> {
		map.put("name", req.params("name"));
		return new ModelAndView(map, "name");},
	    new ThymeleafTemplateEngine(resolv));

	get("/echo/:name", (req, res)-> "echo: " + req.params("name"));
    }

    private void setRoute(TemplateResolver resolv) {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	logger.info("################# hello info");
	logger.debug("################# hello debug");
	
	before("/admin/", (req, res) -> {
		res.redirect("/admin/dashboard");});

	get("/admin/:menu", (req, res) -> {
		Map map = new HashMap();
		map.put("menu", req.params("menu"));
		return new ModelAndView(map, "admin/index");},
	    new ThymeleafTemplateEngine(resolv));

    }
	
}
