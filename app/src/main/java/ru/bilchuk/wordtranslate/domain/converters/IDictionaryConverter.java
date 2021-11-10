package ru.bilchuk.wordtranslate.domain.converters;

import java.util.List;

import ru.bilchuk.wordtranslate.domain.models.DictionaryItem;
import ru.bilchuk.wordtranslate.domain.repositories.models.DictionaryItemModel;

public interface IDictionaryConverter {

    DictionaryItemModel convert(DictionaryItem dictionaryItem);
    DictionaryItem reverse(DictionaryItemModel dictionaryItemModel);

    List<DictionaryItem> reverseList(List<DictionaryItemModel> dictionaryItemModels);
}
