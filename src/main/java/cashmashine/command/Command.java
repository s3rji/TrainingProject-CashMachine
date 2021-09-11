package cashmashine.command;

import cashmashine.exception.InterruptOperationException;

interface Command {
    void execute() throws InterruptOperationException;
}
