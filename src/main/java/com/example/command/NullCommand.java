package com.example.command;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class NullCommand
        implements Command {

    @Override
    public Command init(Map<String, String> data) {
        return this;
    }

    @Override
    public String execute() {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get("/home/eduard/IdeaProjects/ServerApp/index.html"));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
