package org.esport.presentation;

import org.esport.presentation.menu.MainMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Console {
    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);

    public static void main(String[] args) {
        LOGGER.info("Démarrage de l'application");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        org.h2.tools.Server h2WebServer = context.getBean("h2WebServer", org.h2.tools.Server.class);
        String h2ConsoleUrl = h2WebServer.getURL();
        LOGGER.info("H2 Console URL: {}", h2ConsoleUrl);
        MainMenu mainMenu = context.getBean(MainMenu.class);
        mainMenu.afficherMenuPrincipal();
        LOGGER.info("Fermeture de l'application");
    }
}
