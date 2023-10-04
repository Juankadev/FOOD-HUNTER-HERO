package com.example.ffh_rep.utils;

public class EmailTemplate {

    public EmailTemplate(){

    }

    public String templateRegistroExitosoHunter(String name, String email, String password) {
        String htmlContent = "<html><body>";
        htmlContent += "<h1>Bienvenidx a FoodHunterHero</h1>";
        htmlContent += "<p>Hola " + name + ",</p>";
        htmlContent += "<p>Tu registro en FoodHunterHero se ha realizado con éxito. Aquí están tus detalles de inicio de sesión:</p>";
        htmlContent += "<p>User    : " + email + "</p>";
        htmlContent += "<p>Password: " + password + "</p>";
        htmlContent += "<p>Gracias por unirte a nuestra plataforma.</p>";
        htmlContent += "</body></html>";

        return htmlContent;
    }

    public String templateRegistroExitosoComercio(String name, String email, String password) {
        String htmlContent = "<html><body>";
        htmlContent += "<h1>Bienvenidx a FoodHunterHero</h1>";
        htmlContent += "<p>Estimadx representante de " + name + ",</p>";
        htmlContent += "<p>El registro de su comercio en FoodHunterHero se ha realizado con éxito. Aquí están tus detalles de inicio de sesión:</p>";
        htmlContent += "<p>User    : " + email + "</p>";
        htmlContent += "<p>Password: " + password + "</p>";
        htmlContent += "<p>Aguarde su activación como comercio adherido</p>";
        htmlContent += "<p>Gracias por unirte a nuestra plataforma y ser parte de la comunidad.</p>";
        htmlContent += "</body></html>";

        return htmlContent;
    }
}
