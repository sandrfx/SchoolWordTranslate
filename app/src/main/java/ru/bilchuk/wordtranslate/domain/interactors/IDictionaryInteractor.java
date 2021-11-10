package ru.bilchuk.wordtranslate.domain.interactors;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.bilchuk.wordtranslate.domain.models.DictionaryItem;

public interface IDictionaryInteractor {

    Completable add(@NonNull DictionaryItem dictionaryItem);
    Single<List<DictionaryItem>> getList();
    Completable delete(long id);
}
