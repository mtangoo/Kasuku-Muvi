package tz.co.hosannahighertech.kasukumuvi.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;
import tz.co.hosannahighertech.kasukumuvi.data.repos.MoviesRepo;
import tz.co.hosannahighertech.kasukumuvi.injection.components.DaggerRepositoryComponent;
import tz.co.hosannahighertech.kasukumuvi.injection.modules.AppContextModule;
import tz.co.hosannahighertech.kasukumuvi.injection.modules.DatabaseModule;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.viewmodel
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 18:05.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MovieViewModel extends AndroidViewModel {
    @Inject
    public MoviesRepo mRepo;

    private MutableLiveData<ResponseDataList> mMoviesListData = new MutableLiveData<>();

    //for single movie -- Error and data comes so fast that one LiveData object cannot correctly publish them
    private MutableLiveData<Movie> mOneMovieData = new MutableLiveData<>();
    private MutableLiveData<Throwable> mOneMovieError = new MutableLiveData<>();
    private Disposable mDisposable;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        DaggerRepositoryComponent.builder()
                .databaseModule(new DatabaseModule(application.getApplicationContext()))
                .appContextModule(new AppContextModule(application.getApplicationContext()))
                .build()
                .inject(this);
    }

    public MutableLiveData<ResponseDataList> getMovies() {
        return mMoviesListData;
    }


    public void loadData() {
        disposeDisposable();

        mDisposable = mRepo.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> {
                    subscription.request(Long.MAX_VALUE);
                    mMoviesListData.postValue(new ResponseDataList().setStatus(Status.LOADING));
                })
                .subscribe(data -> {
                            //onNext
                            mMoviesListData.postValue(new ResponseDataList().setStatus(Status.SUCCESS).setData(data));
                        },
                        error -> {
                            //onError
                            mMoviesListData.postValue(new ResponseDataList().setStatus(Status.ERROR).setError(error));
                        },
                        () -> {
                            //onComplete
                        }
                );
    }

    private void disposeDisposable() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }


    public void search(String query) {
        disposeDisposable();

        mDisposable = mRepo.search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> {
                    subscription.request(Long.MAX_VALUE);
                    mMoviesListData.postValue(new ResponseDataList().setStatus(Status.LOADING));
                })
                .subscribe(data -> {
                            //onNext
                            mMoviesListData.postValue(new ResponseDataList().setStatus(Status.SUCCESS).setData(data));
                        },
                        error -> {
                            //onError
                            mMoviesListData.postValue(new ResponseDataList().setStatus(Status.ERROR).setError(error));
                        },
                        () -> {
                            //onComplete
                        }
                );

    }

    public MutableLiveData<Movie> getMovieData() {
        return mOneMovieData;
    }

    public MutableLiveData<Throwable> getMovieError() {
        return mOneMovieError;
    }

    public void loadMovie(int id) {
        disposeDisposable();

        mDisposable = mRepo.getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .doOnSubscribe(subscription -> {
                    subscription.request(Long.MAX_VALUE);
                    //post progress here
                })
                .subscribe(data -> {
                            //onNext
                            mOneMovieData.postValue(data);
                        },
                        error -> {
                            //onError
                            mOneMovieError.postValue(error);
                        },
                        () -> {
                            //onComplete
                        }
                );
    }

    public void clearDatabase() {
        mRepo.clearDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("KASUKU_MUVI", "Deleted Rows:" + data);
                }, throwable -> {
                    Log.d("KASUKU_MUVI", throwable.getMessage());
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposeDisposable();
    }

}
