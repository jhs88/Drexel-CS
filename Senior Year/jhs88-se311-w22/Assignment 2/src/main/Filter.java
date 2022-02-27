package main;

import java.io.FileInputStream;

// Handles standard IO
public abstract class Filter implements Runnable {
    protected Pipe sInput;
    protected Pipe sOutput;

    public Filter(Pipe sInput, Pipe sOutput) {
        this.sInput = sInput;
        this.sOutput = sOutput;
    }

    public Filter(FileInputStream sInput, Pipe sOutput) {
        this.sOutput = sOutput;
    }

    public Filter(Pipe sInput) {
        this.sInput = sInput;
    }
}