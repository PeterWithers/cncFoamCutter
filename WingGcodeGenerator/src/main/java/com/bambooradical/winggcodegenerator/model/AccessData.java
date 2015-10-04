/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * @since Oct 4, 2015 3:26:01 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
@Entity
public class AccessData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date accessDate;
    private String remoteAddress;
    private String userAgent;
    private String acceptLanguage;
    private String requestUrl;

    public AccessData() {
    }

    public AccessData(Date accessDate, String remoteAddress, String userAgent, String acceptLanguage, String requestUrl) {
        this.accessDate = accessDate;
        this.remoteAddress = remoteAddress;
        this.userAgent = userAgent;
        this.acceptLanguage = acceptLanguage;
        this.requestUrl = requestUrl;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
