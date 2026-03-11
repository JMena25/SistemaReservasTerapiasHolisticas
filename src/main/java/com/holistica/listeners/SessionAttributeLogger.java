/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holistica.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionAttributeLogger implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("intentosFallidos".equals(event.getName())) {
            System.out.println("⚠ Intentos fallidos: " + event.getValue());
        }

        if ("bloqueado".equals(event.getName())) {
            System.out.println("⛔ Sesión bloqueada por intentos fallidos.");
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if ("bloqueado".equals(event.getName())) {
            System.out.println("🟢 Bloqueo eliminado.");
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if ("intentosFallidos".equals(event.getName())) {
            System.out.println("⚠ Intentos fallidos actualizados: "
                    + event.getSession().getAttribute("intentosFallidos"));
        }
    }
}