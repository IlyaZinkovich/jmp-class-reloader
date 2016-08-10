package com.epam.jmp.classloading;

public class ClassToReload implements Reloadable {

    @Override
    public String doJob() {
        return "bad job";
    }
}
