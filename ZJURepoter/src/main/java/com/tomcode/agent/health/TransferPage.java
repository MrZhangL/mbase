package com.tomcode.agent.health;

class TransferPage {
    private boolean success;
    private String html;

    public TransferPage(boolean success, String html){
        this.success = success;
        this.html = html;
    }

    public static TransferPage OK(String html) {
        return new TransferPage(true, html);
    }

    public static TransferPage Fail(String html) {
        return new TransferPage(false, html);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getHtml() {
        return html;
    }


}
