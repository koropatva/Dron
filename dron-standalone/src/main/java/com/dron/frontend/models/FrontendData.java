package com.dron.frontend.models;

public class FrontendData {

    private static FrontendData INSTANCE;

    public static FrontendData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FrontendData();
        }
        return INSTANCE;
    }

    public FrontendData() {
    }

}
