/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsbm.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Lakshitha
 */
@javax.ws.rs.ApplicationPath("WebResources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.nsbm.service.NotificationService.class);
        resources.add(com.nsbm.service.PlayerService.class);
        resources.add(com.nsbm.service.WordService.class);
    }
    
}
