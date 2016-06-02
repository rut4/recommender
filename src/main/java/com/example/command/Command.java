/**
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Copyright (C) 2016 Oggetto Web ltd (http://oggettoweb.com/), SFU (http://sfedu.ru)
 */

package com.example.command;

import java.util.Map;

public interface Command
{
    Command init(Map<String, String> data);

    String execute();
}
