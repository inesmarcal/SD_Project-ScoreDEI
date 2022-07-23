//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.formdata;

public class MenuItem {
    private String Option, url;

    public MenuItem(){}

    public MenuItem(String Option, String url) {
        this.Option = Option;
        this.url = url;
    }

    public String getOption() {
        return this.Option;
    }

    public void setOption(String Option) {
        this.Option = Option;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
