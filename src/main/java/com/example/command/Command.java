package com.example.command;

import java.util.Map;

public interface Command
{
    Command init(Map<String, String> data);

    String execute();
}
