package ru.job4j;

/**
 * Данный интерфейс описывает сервис.
 * В данном проекте будет описано 2
 * сервиса - queue и topic. Это
 * своеобразные режимы взаимодействия
 * сервера с клиентами.
 */
public interface Service {

    Resp process(Req req);
}
