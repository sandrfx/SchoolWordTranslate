package ru.bilchuk.wordtranslate.presentation.main.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ru.bilchuk.wordtranslate.data.repositories.DictionaryRepositoryImpl;
import ru.bilchuk.wordtranslate.databinding.ActivityMainBinding;
import ru.bilchuk.wordtranslate.domain.converters.DictionaryConverterImpl;
import ru.bilchuk.wordtranslate.domain.interactors.DictionaryInteractorImpl;
import ru.bilchuk.wordtranslate.domain.interactors.IDictionaryInteractor;
import ru.bilchuk.wordtranslate.domain.models.DictionaryItem;
import ru.bilchuk.wordtranslate.domain.repositories.IDictionaryRepository;
import ru.bilchuk.wordtranslate.presentation.main.viewmodel.DictionaryViewModel;
import ru.bilchuk.wordtranslate.utils.ISchedulers;
import ru.bilchuk.wordtranslate.utils.SchedulersImpl;

public class MainActivity extends AppCompatActivity {

    private DictionaryViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createViewModel();
        observeLiveData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.loadDataAsyncRx();
    }

    private void createViewModel() {
        IDictionaryRepository dictionaryRepository = new DictionaryRepositoryImpl(getApplicationContext());
        IDictionaryInteractor dictionaryInteractor = new DictionaryInteractorImpl(dictionaryRepository,
                new DictionaryConverterImpl());
        ISchedulers schedulers = new SchedulersImpl();
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new DictionaryViewModel(dictionaryInteractor, schedulers);
            }
        }).get(DictionaryViewModel.class);
    }

    private void observeLiveData() {
        viewModel.getDictionaryItemsLiveData().observe(this, this::showWords);
    }

    private void showWords(List<DictionaryItem> list) {
        binding.log.setText(list.toString());
    }
}