package com.store.application.command;

import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;

public interface Command {
    Menu execute(ConsoleApplication consoleApp);
}