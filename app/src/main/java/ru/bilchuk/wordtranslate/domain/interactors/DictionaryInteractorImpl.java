package ru.bilchuk.wordtranslate.domain.interactors;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.bilchuk.wordtranslate.domain.converters.IDictionaryConverter;
import ru.bilchuk.wordtranslate.domain.models.DictionaryItem;
import ru.bilchuk.wordtranslate.domain.repositories.IDictionaryRepository;

public class DictionaryInteractorImpl implements IDictionaryInteractor {

    private final IDictionaryRepository repository;
    private final IDictionaryConverter converter;

    public DictionaryInteractorImpl(@NonNull IDictionaryRepository repository,
                                    @NonNull IDictionaryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Completable add(@NonNull DictionaryItem dictionaryItem) {
        return repository.add(converter.convert(dictionaryItem));
    }

    @Override
    public Single<List<DictionaryItem>> getList() {
        return repository.getList()
                .map(converter::reverseList);
    }

    @Override
    public Completable delete(long id) {
        return repository.delete(id);
    }
}
