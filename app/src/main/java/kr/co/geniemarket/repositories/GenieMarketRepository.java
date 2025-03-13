package kr.co.geniemarket.repositories;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.co.geniemarket.core.Const;
import kr.co.geniemarket.core.GMLog;
import kr.co.geniemarket.models.ProductInfo;
import kr.co.geniemarket.ui.ConnectionConst;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Response;

public class GenieMarketRepository {

    @Getter
    @Setter
    private CompositeDisposable compositeDisposable;

    private static volatile GenieMarketRepository instance;

    private GenieMarketRepository() {
    }

    public static GenieMarketRepository getInstance() {
        if(instance == null) {
            synchronized (GenieMarketRepository.class) {
                if(instance == null) {
                    instance = new GenieMarketRepository();
                }
            }
        }
        return instance;
    }

    private GenieMarketRemoteService getRemoteClient(long timeoutMS) throws Exception {
        return GenieMarketRemoteClient.createRequest(timeoutMS);
    }

    public MutableLiveData<ProductInfo> subscribeProductInfoOnce(
            Context context,
            MutableLiveData<ProductInfo> newResProductInfo,
            long timeoutSec) throws Exception {

        Single<Response<ProductInfo>> response =
                getRemoteClient(timeoutSec).getProductItemList(ConnectionConst.PRODUCT_INFO_SEARCH_SERVER_URL);

        Disposable disposable =
                response.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(throwable -> {
                            GMLog.e("[QA] ", throwable);
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(() -> {
                                Toast.makeText(context, throwable.toString(), Toast.LENGTH_SHORT).show();
                            }, 0);
                        })
                        .retryWhen(throwable ->
                                throwable.delay(timeoutSec, TimeUnit.SECONDS)
                                        .take(Const.DEFAULT_RETRY_NETWORK_CNT))
                        .subscribe(jsonResult -> {
                            String errorMessage = "처리 중 에러가 발생하였습니다.";
                            if(jsonResult == null) {
                                GMLog.e("[QA] ");
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(() -> {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                }, 0);
                                return;
                            }
                            if(jsonResult.isSuccessful()){
                                if(newResProductInfo != null && jsonResult.body() != null) {
                                    newResProductInfo.setValue(jsonResult.body());
                                }else{
                                    newResProductInfo.setValue(null);
                                }
                            }else{
                                try{
                                    newResProductInfo.setValue(
                                            (ProductInfo) GenieMarketRemoteClient.errorResponseConverter.convert(jsonResult.errorBody()));
                                }catch (Exception ex){
                                    GMLog.e("[QA] ", ex);
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(() -> {
                                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                    }, 0);
                                    newResProductInfo.setValue(null);
                                }
                            }
                        }, error -> {
                            // RxJava에서는 error 핸들링을 try~catch로 잡을 수 없다.
                            // subscriber의 onError()를 통해 잡아야 한다.
                            GMLog.e("[QA] ", error);
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(() -> {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            }, 0);
                        });

        getCompositeDisposable().add(disposable);
        return newResProductInfo;
    }

    public void disposeAllObservables() {

        // RxJava subscribe된 모든 Obserever 객체들을 폐기한다.
        CompositeDisposable disposable = getCompositeDisposable();
        if(disposable != null && disposable.size() != 0 && !disposable.isDisposed()){
            // Using dispose() will
            // clear all and set isDisposed = true, so it will not accept any new disposable.
            // Thus, when restart the app after finish the app, you can't receive stream data.
            GMLog.d("AdCompositDisposable.size() 는? " + disposable.size());
            disposable.dispose();
            // Using clear() will
            // clear all, but can accept new disposable.
            // Thus, when restart the app after finish the app, you can receive stream data.
//            disposable.clear();
            GMLog.d("[hey] AdCompositDisposable.size() 는? " + disposable.size());
            GMLog.e("[QA] All of Ad Observer objects subscribed were disposed successfully.");
        }
        setCompositeDisposable(null);
        setCompositeDisposable(new CompositeDisposable());
    }
}
