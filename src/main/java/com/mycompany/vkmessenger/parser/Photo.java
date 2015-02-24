/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger.parser;

/**
 *
 * @author odour
 */
public class Photo {

    private String photoID;
    private String urlSmall;
    private String urlBig;

    public Photo(String _urlSmall, String _urlBig, String phid) {
        this.urlSmall = _urlSmall;
        this.urlBig = _urlBig;
        this.photoID = phid;
    }

    public String getSmall() {
        return urlSmall;
    }

    public String getBig() {
        return urlBig;
    }

    public String getPhotoID() {
        return photoID;
    }
}
