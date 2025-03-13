package kr.co.geniemarket.core;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

public class Const {

    public static final String SERVICE_NAME = "GENIEMARKET";

    public static final String LOG_TAG = SERVICE_NAME + "-LOGS";

    public static final String BIG_BRACKET_LEFT = "[";

    public static final String BIG_BRACKET_RIGHT = "]";

    public static final String QUESTION_MARK = "?";

    public static final String SEMI_COLON = ":";

    public static final String COMMA = ",";

    public static final String BLANK = " ";

    public static final String EMPTY = "";

    public static final String EMPTY_STR = "";

    public static final String NEXT_LINE = "\n";

    public static final String AMPERSAND = "&";

    public static final String EQUAL = "=";

    public static final String BRACKET_LEFT = "(";

    public static final String BRACKET_RIGHT = ")";

    public static final String KEY_PAGE                             = "page";

    public static final String KEY_ACTION                           = "action";

    public static final String SLASH = "/";

    public static final String UNDERSCORE = "_";

    public static final String KEY_HEX_FORMAT                       = "%02x";

    public static final String KEY_SHA_256                          = "SHA-256";

    public static final SimpleDateFormat DATE_FORMAT_DETAIL      = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT      = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DATE_FORMAT2      = new SimpleDateFormat("yyyy.MM.dd");

    public static final SimpleDateFormat DATE_FORMAT_HH_MM = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_KOR = new SimpleDateFormat("yyyy년 MM월 dd일까지", Locale.KOREAN);

    public static final DecimalFormat PLUS_DECIMAL_FORMAT = new DecimalFormat("+#,###");

    public static final DecimalFormat SIMPLE_DECIMAL_FORMAT = new DecimalFormat("#,###");

    public static final String REG_EXP_EMAIL = "^[a-z0-9\\.\\-_]+@([a-z0-9\\-]+\\.)+[a-z]{2,6}$";
    public static final String REG_EXP_MOBILE_NUMBER = "^01(?:0|1|[6-9])\\d{7,8}$";

    public static final boolean DEFAULT_VALUE_BOOLEAN = false;

    public static final int DEFAULT_TIMEOUT_SEC = 30;

    public static final int DEFAULT_RETRY_NETWORK_CNT = 3;

    public static final int DEFAULT_TIMEOUT_MILLISEC = DEFAULT_TIMEOUT_SEC * 1000;

    public static final int DEFAULT_VALUE_INT = -1;

    public static final long DEFAULT_VALUE_LONG = -1L;

    public static final float DEFAULT_VALUE_FLOAT = -1F;

    public static final String DEFAULT_VALUE_STRING = "";

    public static final Set<String> DEFAULT_VALUE_STRING_NULL = null;

    public static final int DEFAULT_ZERO = 0;

    public static final String DESC = "desc";

    public static final String ASC = "asc";


    public static final String KEY_APP_NAME = "app_name";

    public static final String KEY_APP_VERSION = "app_version";

    public static final String KEY_PACKAGE_NAME = "app_bundle";

    public static final String KEY_STORE_URL = "app_storeurl";

    public static final String KEY_USER_AGENT = "app_ua";

    public static final String KEY_AID = "aid";

    public static final String KEY_IFA = "ifa"; // IFA는 GOOGLE AD ID에 해당

    public static final String KEY_IFV = "ifv"; // IFV는 GOOGLE APP SET ID에 해당

    public static final String KEY_IFV_SCOPE = "ifv_scope"; // IFV는 GOOGLE APP SET ID에 해당

    public static final String KEY_IS_IFA = "is_ifa"; // ID가 IFA인지 IFV인지 여부. IFA이면 true, IFV이면 false

    public static final String KEY_DO_NOT_TRACK = "dnt";

    public static final String KEY_LIMIT_AD_TRACKING = "lmt";

    public static final String KEY_IP = "ip";

    public static final String KEY_MANUFACTURER = "manufacturer";

    public static final String KEY_MODEL = "model";

    public static final String KEY_OS_TYPE = "os_type";

    public static final String KEY_OS_VERSION = "os_version";

    public static final String KEY_HW_VERSION = "hw_version";

    public static final String KEY_IS_EMULATOR = "is_emulator";

    public static final String KEY_TARGET_SDK_VERSION = "target_sdk_version";

    public static final String KEY_SDK_VERSION = "sdk_version";

    public static final String KEY_IS_EFFECTIVE_RENDERING_AVAILABLE = "is_effective_rendering_available";

    public static final String KEY_TELECOM_CARRIER = "telecom_carrier";

    public static final String KEY_NETWORK_CONNECTION_INFO = "network_connection_info";

    public static final String KEY_HEIGHT = "height";

    public static final String KEY_WIDTH = "width";

    public static final String KEY_PIXEL_PER_INCH = "ppi";

    public static final String KEY_DENSITY = "pxratio";

    public static final String KEY_LANGUAGE = "language";

    public static final String KEY_PUBLISHER_UID = "publisher_uid";

    public static final String KEY_PLACEMENT_UID = "placement_uid";

    public static final String KEY_AD_RELOAD_SEC = "ad_reload_sec";

    public static final String KEY_CONFIG_RELOAD_SEC = "config_reload_sec";

    public static final String KEY_TIMEOUT_SEC = "timeout_sec";

    public static final String KEY_MEDIATIONS = "mediations";

    public static final String KEY_TYPE = "type";

    public static final String KEY_NETWORK = "network";

    public static final String KEY_AD_UNIT = "ad_unit";

    public static final String KEY_EXTENSION = "extension";

    public static final String KEY_APP_KEY = "app_key";


    public static final String KEY_CUSTOM_PATH = "customPath";
    public static final String KEY_ADDITIONAL_PRODUCT_INFO = "result";

    public static final String KEY_RESULTS             = "result";

    public static final String KEY_PRODUCT_NAME        = "productName";

    public static final String KEY_SELLER_ID           = "sellerID";

    public static final String KEY_PRODUCT_ITEM_CNT    = "productItemCnt";

    public static final String KEY_PRODUCT_PRICE       = "productPrice";

    public static final String KEY_PRODUCT_DESC        = "productDescription";

    public static final String KEY_PRODUCT_BIG_CATEGORY= "bigCategory";

    public static final String KEY_PRODUCT_MID_CATEGORY= "midCategory";

    public static final String KEY_PRODUCT_SMALL_CATEGORY= "smallCategory";

    public static final String KEY_PRODUCT_IMG         = "imageFilePath";
}
