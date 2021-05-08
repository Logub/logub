package com.loghub.loggenerator.model;


public class Quote {
  private String from;
private   String quote;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getQuote() {
    return quote;
  }

  public void setQuote(String quote) {
    this.quote = quote;
  }

  public Quote(String from, String quote) {
    this.from = from;
    this.quote = quote;
  }
}
