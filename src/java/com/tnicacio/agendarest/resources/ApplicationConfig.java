/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tnicacio.agendarest.resources;

import java.util.Set;

/**
 *
 * @author tnica
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.tnicacio.agendarest.resources.CompromissoResource.class);
        resources.add(com.tnicacio.agendarest.resources.ContatoResource.class);
        resources.add(com.tnicacio.agendarest.resources.LocalResource.class);
        resources.add(com.tnicacio.agendarest.resources.ParticipanteResource.class);
    }
    
}
