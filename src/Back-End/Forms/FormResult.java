//Inês Martins Marçal 2019215917
//Noémia Quintano Mora Gonçalves 2019219433

package com.example.formdata;


public class FormResult {
    private String  result;
    private String entityType;
 
    public FormResult(){}

    public FormResult(String entityType){
        this.entityType = entityType;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }    

}
