package com.yatoufang.core;

/**
 * @author GongHuang（hse）
 * @since 2021/11/19 19:41
 */
public interface ConsoleService {

    void init();

    void printHead(String info);

    void print(String info);

    void printInfo(String info);

    void printError(String error);

}
