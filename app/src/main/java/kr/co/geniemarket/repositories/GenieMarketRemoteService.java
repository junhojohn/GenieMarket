package kr.co.geniemarket.repositories;

import io.reactivex.rxjava3.core.Single;
import kr.co.geniemarket.core.Const;
import kr.co.geniemarket.models.ProductInfo;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GenieMarketRemoteService {

    @POST("/{customPath}")
    Single<Response<ProductInfo>> getProductItemList(
            @Path(value = Const.KEY_CUSTOM_PATH,    encoded = true) String customPath);

}
