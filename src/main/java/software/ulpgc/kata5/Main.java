package software.ulpgc.kata5;

import java.util.HashMap;
import java.util.Map;
import spark.Spark;
import spark.Request;
import spark.Response;

public class Main {
    static Map<String, Command> commands = new HashMap<>();

    public static void main(String[] args) {
        commands.put("calculator", new OperationCommand());

        Spark.port(8080);
        Spark.get("/calculator/:operation/:number", (req,res) ->
                new CommandExecutor(req,res).execute("calculator"));

    }

    private static class CommandExecutor {
        private final Request request;
        private final Response response;

        public CommandExecutor(Request request, Response response) {
            this.request = request;
            this.response = response;
        }

        public String execute(String name) {
            Command command = commands.get(name);
            Command.Output output = command.execute(input());
            return output.result();
        }

        private Command.Input input() {
            return key -> oneOf(request.params(key), request.queryParams(key));
        }

        private String oneOf(String params, String s) {
            return params != null ? params : s;
        }

    }

}
