package ru.bilchuk.wordtranslate.data.repositories;

import static ru.bilchuk.wordtranslate.data.datastores.db.DataBaseOpenHelper.DBMetaData.TranslationTableInfo.COLUMN_KEYWORD;
import static ru.bilchuk.wordtranslate.data.datastores.db.DataBaseOpenHelper.DBMetaData.TranslationTableInfo.COLUMN_TRANSLATION;
import static ru.bilchuk.wordtranslate.data.datastores.providers.DictionaryMetaData.TRANSLATES_CONTENT_URI;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.bilchuk.wordtranslate.data.datastores.db.DataBaseOpenHelper;
import ru.bilchuk.wordtranslate.domain.repositories.IDictionaryRepository;
import ru.bilchuk.wordtranslate.domain.repositories.models.DictionaryItemModel;

public class DictionaryRepositoryImpl implements IDictionaryRepository {

    private ContentResolver contentResolver;

    public DictionaryRepositoryImpl(@NonNull Context context) {
        this.contentResolver = context.getContentResolver();
    }

    @Override
    public Completable add(@NonNull DictionaryItemModel dictionaryItemModel) {
        return Completable.fromAction(() -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_KEYWORD, dictionaryItemModel.getKeyword());
            contentValues.put(COLUMN_TRANSLATION, dictionaryItemModel.getTranslation());
            contentResolver.insert(TRANSLATES_CONTENT_URI, contentValues);
        });
    }

    @Override
    public Single<List<DictionaryItemModel>> getList() {
        return Single.fromCallable(() -> {
            List<DictionaryItemModel> items = new ArrayList<>();
            Cursor cursor = null;
            try {
                cursor = contentResolver.query(TRANSLATES_CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    while(cursor.moveToNext()) {
                        items.add(fromCursor(cursor));
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            return items;
        });
    }

    @Override
    public Completable delete(long id) {
        return Completable.complete();
    }

    @SuppressLint("Range")
    private static DictionaryItemModel fromCursor(@NonNull Cursor cursor) {
        DictionaryItemModel model = new DictionaryItemModel();
        model.setId(cursor.getLong(cursor.getColumnIndex(DataBaseOpenHelper.DBMetaData.TranslationTableInfo._ID)));
        model.setKeyword(cursor.getString(cursor.getColumnIndex(COLUMN_KEYWORD)));
        model.setTranslation(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSLATION)));

        return model;
    }
}
