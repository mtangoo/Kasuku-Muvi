package tz.co.hosannahighertech.kasukumuvi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tz.co.hosannahighertech.kasukumuvi.data.api.WebService;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.DatabaseManager;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.MovieDao;
import tz.co.hosannahighertech.kasukumuvi.data.repos.MoviesRepo;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.viewmodel
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 18:05.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MovieViewModel extends AndroidViewModel {
    private MoviesRepo mRepo;
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private MutableLiveData<ResponseDataList> mMoviesListData = new MutableLiveData<>();

    //for single movie -- Error and data comes so fast that one LiveData object cannot correctly publish them
    private MutableLiveData<Movie> mOneMovieData = new MutableLiveData<>();
    private MutableLiveData<Throwable> mOneMovieError = new MutableLiveData<>();

    public MovieViewModel(@NonNull Application application) {
        super(application);

        MovieDao movieDao = DatabaseManager.getInstance(application.getApplicationContext()).movieDao();
        mRepo = MoviesRepo.getInstance(movieDao, WebService.getApi());
    }

    public MutableLiveData<ResponseDataList> getMovies()
    {
        return mMoviesListData;
    }


    public void loadData()
    {
        Disposable d = mRepo.getMovies()
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

        mDisposables.add(d);
    }


    public void search(String query) {
        Disposable d = mRepo.search(query)
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
                            //mMoviesListData.postValue(new ResponseDataList().setStatus(Status.ERROR).setError(error));
                        },
                        () -> {
                            //onComplete
                        }
                );

        mDisposables.add(d);

    }

    public MutableLiveData<Movie> getMovieData()
    {
        return  mOneMovieData;
    }

    public MutableLiveData<Throwable> getMovieError()
    {
        return  mOneMovieError;
    }

    public void loadMovie(int id)
    {
        Disposable d = mRepo.getMovie(id)
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

        mDisposables.add(d);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }

}
