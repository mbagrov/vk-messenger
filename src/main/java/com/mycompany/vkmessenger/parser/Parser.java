/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.vkmessenger.parser;

import java.util.LinkedHashSet;

/**
 *
 * @author odour
 */
public interface Parser {
    public LinkedHashSet getCollection(String uid);
    public <T> T getById(String entid);
}
