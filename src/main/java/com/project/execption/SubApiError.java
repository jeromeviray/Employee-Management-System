package com.project.execption;

public class SubApiError{
    private String object;
    private String field;
    private String message;
    private Object rejectedValue;


    public SubApiError( String object, String field, Object rejectedValue, String message ) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public SubApiError( String object, String message ) {
        this.object = object;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject( String object ) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField( String field ) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue( Object rejectedValue ) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}
