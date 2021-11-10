package ru.bilchuk.wordtranslate.domain.repositories;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.bilchuk.wordtranslate.domain.repositories.models.DictionaryItemModel;

public interface IDictionaryRepository {

    Completable add(@NonNull DictionaryItemModel dictionaryItemModel);
    Single<List<DictionaryItemModel>> getList();
    Completable delete(long id);
}
