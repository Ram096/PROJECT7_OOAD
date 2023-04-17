// Copyright = Bruce Montgomery - OOAD Spring 2023 Project 2 - SysOut class

public interface SysOut {
    default void out(String msg) {
        System.out.println(msg);
    }
}

