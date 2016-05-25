package com.example.command;

import java.util.Map;

public class CommandFactory
{
    public Command produce(Map<String, String> data)
    {
        Command command = this._factory(data.get("action"));
        return command.init(data);
    }

    private Command _factory(String action)
    {
        if (action == null) {
            return new NullCommand();
        }
        switch (action) {
            case Clear.CODE:
                return new Clear();
            case Fill.CODE:
                return new Fill();
            case Recommend.CODE:
                return new Recommend();
            default:
                return new NullCommand();
        }
    }
}
