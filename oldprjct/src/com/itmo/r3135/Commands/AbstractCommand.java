package com.itmo.r3135.Commands;


import com.itmo.r3135.Control;

public abstract class AbstractCommand {
    protected Control control;

    public AbstractCommand(Control control) {
        this.control = control;
    }

    public abstract void activate(String input);
    public void activate(){
        activate("");
    }
}
