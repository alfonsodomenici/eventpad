/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.logging.boundary;

import java.lang.System.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggingSystem {

    @Produces
    public Logger produceLogger(InjectionPoint ip) {
        var loggerName = ip.getMember().getDeclaringClass().getName();
        return System.getLogger(loggerName);
    }
    
}
