/**
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Copyright (C) 2016 Oggetto Web ltd (http://oggettoweb.com/), SFU (http://sfedu.ru)
 */

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
