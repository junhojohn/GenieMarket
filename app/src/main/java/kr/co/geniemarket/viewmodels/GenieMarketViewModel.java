package kr.co.geniemarket.viewmodels;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kr.co.geniemarket.models.ProductInfo;
import kr.co.geniemarket.repositories.GenieMarketRepository;
import lombok.Getter;
import lombok.Setter;

public class GenieMarketViewModel extends ViewModel {

    @Getter
    @Setter
    private GenieMarketRepository mediationAdConfigRepository;

    private MutableLiveData<ProductInfo> newResProductInfo;

    public GenieMarketViewModel() {
        super();
        setMediationAdConfigRepository(GenieMarketRepository.getInstance());
    }

    public MutableLiveData<ProductInfo> requestProductInfo(Context context, long timeoutSec) throws Exception {
        // RxJava subscribe된 모든 Obserever 객체들을 폐기한다.
        getMediationAdConfigRepository().disposeAllObservables();
        if(newResProductInfo != null) {
            newResProductInfo.removeObservers((LifecycleOwner) context);
            newResProductInfo = null;
        }
        newResProductInfo = new MutableLiveData<>();

        newResProductInfo = getMediationAdConfigRepository().subscribeProductInfoOnce(
                context, newResProductInfo, timeoutSec);

        return newResProductInfo;
    }
}
