package com.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.command.Command;
import com.example.command.CommandFactory;
import com.example.command.Recommend;
import com.example.util.ServerRunner;
import fi.iki.elonen.NanoHTTPD;


public class App extends NanoHTTPD {

    public static Integer port = 4000;
    private CommandFactory _factory = new CommandFactory();

    public App() {
        super(App.port);
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0] != null) {
            App.port = Integer.parseInt(args[0]);
        }
        Recommender.instance();
        ServerRunner.run(App.class);
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            session.parseBody(new HashMap<String, String>());
        } catch (IOException ioe) {
            return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
        } catch (ResponseException re) {
            return new Response(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
        }
        Map<String, String> data = session.getParms();
        String result = this._factory.produce(data).execute();
        return new Response(result);
    }
}
