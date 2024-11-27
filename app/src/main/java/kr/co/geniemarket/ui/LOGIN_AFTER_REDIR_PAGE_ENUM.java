package kr.co.geniemarket.ui;

public enum LOGIN_AFTER_REDIR_PAGE_ENUM {
    REGISTER_ACTIVITY("RegisterActivity", 1),
    PURCHASE_ACTIVITY("PurchaseActivity", 2),
    PAGE_MY_GENIE("PageMyGenie", 3),
    MAIN_ACTIVITY("MainActivity", 4);

    private String activityName;
    private int activityIndex;

    private LOGIN_AFTER_REDIR_PAGE_ENUM(String activityName, int activityIndex){
        this.activityName = activityName;
        this.activityIndex = activityIndex;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getActivityIndex() {
        return activityIndex;
    }

    public LOGIN_AFTER_REDIR_PAGE_ENUM getDummyTabHeaderValueWithName(String activityName){
        LOGIN_AFTER_REDIR_PAGE_ENUM[] typeNames = LOGIN_AFTER_REDIR_PAGE_ENUM.values();
        for(LOGIN_AFTER_REDIR_PAGE_ENUM typeName : typeNames){
            if(activityName.contains(typeName.getActivityName())){
                return typeName;
            }
        }
        return null;
    }

    public LOGIN_AFTER_REDIR_PAGE_ENUM getDummyTabHeaderValueWithColumnIndex(int activityIndex){
        LOGIN_AFTER_REDIR_PAGE_ENUM[] typeNames = LOGIN_AFTER_REDIR_PAGE_ENUM.values();
        for(LOGIN_AFTER_REDIR_PAGE_ENUM typeName : typeNames){
            if(activityIndex == typeName.getActivityIndex()){
                return typeName;
            }
        }
        return null;
    }
}
