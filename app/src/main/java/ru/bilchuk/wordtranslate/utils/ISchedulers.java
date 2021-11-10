package ru.bilchuk.wordtranslate.utils;

import io.reactivex.Scheduler;

public interface ISchedulers {

    Scheduler io();
    Scheduler ui();
}
