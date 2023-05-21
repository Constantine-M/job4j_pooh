package ru.job4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckMaven {

    private static final Logger LOG = LoggerFactory.getLogger(CheckMaven.class);

    public static String ready(String readiness) {
        if (!readiness.equals("yes")) {
            LOG.info("Ooops, something went wrong..");
            throw new IllegalArgumentException("The correct answer - \"yes\". Let's try again!");
        }
        return "Am I ready? - Of course ".concat(readiness);
    }

    public static void main(String[] args) {
        System.out.println(CheckMaven.ready("yes"));
    }
}
