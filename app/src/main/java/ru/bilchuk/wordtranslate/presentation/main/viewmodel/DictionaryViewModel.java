package ru.bilchuk.wordtranslate.presentation.main.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.bilchuk.wordtranslate.domain.interactors.IDictionaryInteractor;
import ru.bilchuk.wordtranslate.domain.models.DictionaryItem;
import ru.bilchuk.wordtranslate.utils.ISchedulers;

public class DictionaryViewModel extends ViewModel {

    private IDictionaryInteractor dictionaryInteractor;
    private ISchedulers schedulers;

    private final MutableLiveData<List<DictionaryItem>> dictionaryItemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();


    private Disposable disposable;

    public DictionaryViewModel(@NonNull IDictionaryInteractor dictionaryInteractor,
                               @NonNull ISchedulers schedulers) {
        this.dictionaryInteractor = dictionaryInteractor;
        this.schedulers = schedulers;
    }

    public void loadDataAsyncRx() {
        disposable = dictionaryInteractor.getList()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(dictionaryItemsLiveData::setValue, errorLiveData::setValue);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    public MutableLiveData<List<DictionaryItem>> getDictionaryItemsLiveData() {
        return dictionaryItemsLiveData;
    }

    public MutableLiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }
}
