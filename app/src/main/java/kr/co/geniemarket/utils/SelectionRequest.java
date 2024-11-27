package kr.co.geniemarket.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import kr.co.geniemarket.ui.ConnectionConst;

import java.util.LinkedHashMap;
import java.util.Map;

public class SelectionRequest extends StringRequest {
    private Map<String, String> parameters = new LinkedHashMap<String, String>();

    public SelectionRequest(Response.Listener<String> listener){
        super(Method.POST, ConnectionConst.PRODUCT_INFO_SEARCH_SERVER_URL, listener, null);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
