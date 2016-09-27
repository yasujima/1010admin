package sample;
import static spark.Spark.*;

public class Hello {

    public static void main(String[] args) {

	get("/hello", (req, res) -> "<h1>hello spark world...!</h1>");

	get("/hello/:param", (req, res) -> "param = " + req.params("param"));

	get("/hello/*/sample/*", (req, res) -> {
		String[] splat = req.splat();

		String text = "splat().length = " + splat.length + "<br>"
		    + "splat()[0] = " + splat[0] + "<br>"
		    + "splat()[1] = " + splat[1];
		return text;
	    });
    }
}
