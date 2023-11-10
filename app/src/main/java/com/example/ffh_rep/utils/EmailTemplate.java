package com.example.ffh_rep.utils;

public class EmailTemplate {

    public EmailTemplate(){

    }

    public String templateRegistroExitosoHunter(String name, String email) {
        String htmlContent = "<body style=\"margin:0; padding:10px 0;\" bgcolor=\"\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">";
        htmlContent += "<br>";


        htmlContent += "<table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"\">";
        htmlContent += "<tbody>";
        htmlContent += "<tr>";
        htmlContent += "<td align=\"center\" valign=\"top\" bgcolor=\"\" style=\"\">";


        htmlContent += "<table border=\"0\" width=\"700\" cellpadding=\"0\" cellspacing=\"0\" class=\"container\" bgcolor=\"#ffffff\" style=\"table-layout:fixed;border: 1px solid #f6f7f6\">";
        htmlContent += "<tbody>";
        htmlContent += "<tr>";
        htmlContent += "<td style=\"padding: 20px 10px 0;\"><a href=\"\" target=\"_blank\"><img src=\"https://iili.io/J2I9Y8P.jpg\" alt=\"Food Hunter Hero\" width=\"150\"></a></td>";
        htmlContent += "</tr>";

        // ### Contenido ###
        htmlContent += "<tr>";
        htmlContent += "<td colspan=\"2\" class=\"container-padding\" bgcolor=\"#ffffff\" style=\"\">";
        htmlContent += "<div style=\"padding: 25px 20px;color: #344054;font-size: 18px;line-height: 28px;\">";
        htmlContent += "Hola " + name + ",";
        htmlContent += "</div>";
        htmlContent += "<div style=\"padding: 15px 20px;text-align: justify;color: #344054;font-size: 18px;line-height: 28px;\">";
        htmlContent += "Queremos darte la bienvenida a Food Hunter Hero!";
        htmlContent += "<br><br>";
        htmlContent += "A continuaci&oacute;n, compartimos tus credenciales para poder acceder a la plataforma.";
        htmlContent += "<br><br>";
        htmlContent += "<strong>Usuario: </strong>" + email;
        htmlContent += "</div>";
        htmlContent += "</td>";
        htmlContent += "</tr>";

        htmlContent += "<tr>";
        htmlContent += "<td colspan=\"2\" align=\"center\" style=\"padding: 15px 20px;text-align: justify;color: #344054;font-size: 18px;line-height: 20px;\">";
        htmlContent += "<div style=\"text-align: center;margin-bottom: 15px;margin-top: 15px\"></div>";
        htmlContent += "</td>";
        htmlContent += "</tr>";

        htmlContent += "<tr>";
        htmlContent += "<td class=\"container-padding\" style=\"text-align: justify;color: #344054;font-size: 18px;line-height: 28px;padding: 40px 20px 15px;\" colspan=\"2\">";
        htmlContent += "Que tengas un gran d&iacute;a y una excelente cacer&iacute;a.";
        htmlContent += "<br><br>";
        htmlContent += "Equipo de Food Hunter Hero";
        htmlContent += "</td>";
        htmlContent += "</tr>";
        // ### Fin de Contenido ###

        htmlContent += "<tr>";
        htmlContent += "<td style=\"padding: 20px;\"></td>";
        htmlContent += "</tr>";

        htmlContent += "</tbody>";
        htmlContent += "</table>";

        htmlContent += "</td>";
        htmlContent += "</tr>";
        htmlContent += "</tbody>";
        htmlContent += "</table>";

        htmlContent += "<br>";
        htmlContent += "<br>";
        htmlContent += "</body>";

        return htmlContent;
    }

    public String templateRegistroExitosoComercio(String name, String email) {
        String htmlContent = "<body style=\"margin:0; padding:10px 0;\" bgcolor=\"\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">";
        htmlContent += "<br>";

        htmlContent += "<table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"\">";
        htmlContent += "<tbody>";
        htmlContent += "<tr>";
        htmlContent += "<td align=\"center\" valign=\"top\" bgcolor=\"\" style=\"\">";

        htmlContent += "<table border=\"0\" width=\"700\" cellpadding=\"0\" cellspacing=\"0\" class=\"container\" bgcolor=\"#ffffff\" style=\"table-layout:fixed;border: 1px solid #f6f7f6\">";
        htmlContent += "<tbody>";
        htmlContent += "<tr>";
        htmlContent += "<td style=\"padding: 20px 10px 0;\"><a href=\"\" target=\"_blank\"><img src=\"https://iili.io/J2I9Y8P.jpg\" alt=\"Food Hunter Hero\" width=\"150\"></a></td>";
        htmlContent += "</tr>";

        // ### BEGIN CONTENT ###
        htmlContent += "<tr>";
        htmlContent += "<td colspan=\"2\" class=\"container-padding\" bgcolor=\"#ffffff\" style=\"\">";
        htmlContent += "<div style=\"padding: 25px 20px;color: #344054;font-size: 18px;line-height: 28px;\">";
        htmlContent += "Estimado/a representante de " + name + ",";
        htmlContent += "</div>";
        htmlContent += "<div style=\"padding: 15px 20px;text-align: justify;color: #344054;font-size: 18px;line-height: 28px;\">";
        htmlContent += "El registro de su comercio en Food Hunter Hero se ha realizado con &eacute;xito.";
        htmlContent += "<br><br>";
        htmlContent += "Aqu&iacute; est&aacute;n tus detalles de inicio de sesi&oacute;n:";
        htmlContent += "<br><br>";
        htmlContent += "<strong>Usuario: </strong>" + email;
        htmlContent += "<br>";
        htmlContent += "Aguarde su activaci&oacute;n como comercio adherido.";
        htmlContent += "<br><br>";
        htmlContent += "Gracias por unirte a nuestra plataforma y ser parte de la comunidad.";
        htmlContent += "</div>";
        htmlContent += "</td>";
        htmlContent += "</tr>";

        htmlContent += "<tr>";
        htmlContent += "<td colspan=\"2\" align=\"center\" style=\"padding: 15px 20px;text-align: justify;color: #344054;font-size: 18px;line-height: 20px;\">";
        htmlContent += "<div style=\"text-align: center;margin-bottom: 15px;margin-top: 15px\"></div>";
        htmlContent += "</td>";
        htmlContent += "</tr>";

        htmlContent += "<tr>";
        htmlContent += "<td class=\"container-padding\" style=\"text-align: justify;color: #344054;font-size: 18px;line-height: 28px;padding: 40px 20px 15px;\" colspan=\"2\">";
        htmlContent += "Que tengas un gran d&iacute;a";
        htmlContent += "<br><br>";
        htmlContent += "Equipo de Food Hunter Hero";
        htmlContent += "</td>";
        htmlContent += "</tr>";
        // ### END CONTENT ###

        htmlContent += "<tr>";
        htmlContent += "<td style=\"padding: 20px;\"></td>";
        htmlContent += "</tr>";

        htmlContent += "</tbody>";
        htmlContent += "</table>";

        htmlContent += "</td>";
        htmlContent += "</tr>";
        htmlContent += "</tbody>";
        htmlContent += "</table>";

        htmlContent += "<br>";
        htmlContent += "<br>";
        htmlContent += "</body>";

        return htmlContent;
    }

    public String templateDespedida(String name) {
        String htmlContent = "<body style=\"margin:0; padding:10px 0;\" bgcolor=\"\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">";
        htmlContent += "<br>";

        htmlContent += "<table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"\">";
        htmlContent += "<tbody>";
        htmlContent += "<tr>";
        htmlContent += "<td align=\"center\" valign=\"top\" bgcolor=\"\" style=\"\">";

        htmlContent += "<table border=\"0\" width=\"700\" cellpadding=\"0\" cellspacing=\"0\" class=\"container\" bgcolor=\"#ffffff\" style=\"table-layout:fixed;border: 1px solid #f6f7f6\">";
        htmlContent += "<tbody>";
        htmlContent += "<tr>";
        htmlContent += "<td style=\"padding: 20px 10px 0;\"><a href=\"\" target=\"_blank\"><img src=\"https://iili.io/J2I9Y8P.jpg\" alt=\"Food Hunter Hero\" width=\"150\"></a></td>";
        htmlContent += "</tr>";

        // ### BEGIN CONTENT ###
        htmlContent += "<tr>";
        htmlContent += "<td colspan=\"2\" class=\"container-padding\" bgcolor=\"#ffffff\" style=\"\">";
        htmlContent += "<div style=\"padding: 25px 20px;color: #344054;font-size: 18px;line-height: 28px;\">";
        htmlContent += "Estimado/a " + name + ",";
        htmlContent += "</div>";
        htmlContent += "<div style=\"padding: 15px 20px;text-align: justify;color: #344054;font-size: 18px;line-height: 28px;\">";
        htmlContent += "Lamentamos que tu cuenta en Food Hunter Hero ha sido dada de baja.";
        htmlContent += "<br><br>";
        htmlContent += "</div>";
        htmlContent += "</td>";
        htmlContent += "</tr>";

        htmlContent += "<tr>";
        htmlContent += "<td colspan=\"2\" align=\"center\" style=\"padding: 15px 20px;text-align: justify;color: #344054;font-size: 18px;line-height: 20px;\">";
        htmlContent += "<div style=\"text-align: center;margin-bottom: 15px;margin-top: 15px\"></div>";
        htmlContent += "</td>";
        htmlContent += "</tr>";

        htmlContent += "<tr>";
        htmlContent += "<td class=\"container-padding\" style=\"text-align: justify;color: #344054;font-size: 18px;line-height: 28px;padding: 40px 20px 15px;\" colspan=\"2\">";
        htmlContent += "Agradecemos tu participaci&oacute;n.";
        htmlContent += "<br><br>";
        htmlContent += "Equipo de Food Hunter Hero";
        htmlContent += "</td>";
        htmlContent += "</tr>";
        // ### END CONTENT ###

        htmlContent += "<tr>";
        htmlContent += "<td style=\"padding: 20px;\"></td>";
        htmlContent += "</tr>";

        htmlContent += "</tbody>";
        htmlContent += "</table>";

        htmlContent += "</td>";
        htmlContent += "</tr>";
        htmlContent += "</tbody>";
        htmlContent += "</table>";

        htmlContent += "<br>";
        htmlContent += "<br>";
        htmlContent += "</body>";

        return htmlContent;
    }

    public String templateCambioPassword(String name, String email, String password) {
        String htmlContent = "<body style=\"margin:0; padding:10px 0;\" bgcolor=\"\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">";
        htmlContent += "<br>";

        htmlContent += "<table border=\"0\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"\">";
        htmlContent += "<tbody>";
        htmlContent += "<tr>";
        htmlContent += "<td align=\"center\" valign=\"top\" bgcolor=\"\" style=\"\">";

        htmlContent += "<table border=\"0\" width=\"700\" cellpadding=\"0\" cellspacing=\"0\" class=\"container\" bgcolor=\"#ffffff\" style=\"table-layout:fixed;border: 1px solid #f6f7f6\">";
        htmlContent += "<tbody>";
        htmlContent += "<tr>";
        htmlContent += "<td style=\"padding: 20px 10px 0;\"><a href=\"\" target=\"_blank\"><img src=\"https://iili.io/J2I9Y8P.jpg\" alt=\"Food Hunter Hero\" width=\"150\"></a></td>";
        htmlContent += "</tr>";

        // ### BEGIN CONTENT ###
        htmlContent += "<tr>";
        htmlContent += "<td colspan=\"2\" class=\"container-padding\" bgcolor=\"#ffffff\" style=\"\">";
        htmlContent += "<div style=\"padding: 25px 20px;color: #344054;font-size: 18px;line-height: 28px;\">";
        htmlContent += "Estimado/a " + name + ",";
        htmlContent += "</div>";
        htmlContent += "<div style=\"padding: 15px 20px;text-align: justify;color: #344054;font-size: 18px;line-height: 28px;\">";
        htmlContent += "Su password ha sido cambiado con &eacute;xito";
        htmlContent += "<br><br>";
        htmlContent += "Credenciales para inicio de sesi&oacute;n:";
        htmlContent += "<br><br>";
        htmlContent += "<strong>Usuario: </strong>" + email;
        htmlContent += "<br>";
        htmlContent += "<strong>Contrase&ntilde;a: </strong>" + password;
        htmlContent += "<br><br>";
        htmlContent += "</div>";
        htmlContent += "</td>";
        htmlContent += "</tr>";

        htmlContent += "<tr>";
        htmlContent += "<td colspan=\"2\" align=\"center\" style=\"padding: 15px 20px;text-align: justify;color: #344054;font-size: 18px;line-height: 20px;\">";
        htmlContent += "<div style=\"text-align: center;margin-bottom: 15px;margin-top: 15px\"></div>";
        htmlContent += "</td>";
        htmlContent += "</tr>";

        htmlContent += "<tr>";
        htmlContent += "<td class=\"container-padding\" style=\"text-align: justify;color: #344054;font-size: 18px;line-height: 28px;padding: 40px 20px 15px;\" colspan=\"2\">";
        htmlContent += "Que tengas un gran d&iacute;a.";
        htmlContent += "<br><br>";
        htmlContent += "Equipo de Food Hunter Hero";
        htmlContent += "</td>";
        htmlContent += "</tr>";
        // ### END CONTENT ###

        htmlContent += "<tr>";
        htmlContent += "<td style=\"padding: 20px;\"></td>";
        htmlContent += "</tr>";

        htmlContent += "</tbody>";
        htmlContent += "</table>";

        htmlContent += "</td>";
        htmlContent += "</tr>";
        htmlContent += "</tbody>";
        htmlContent += "</table>";

        htmlContent += "<br>";
        htmlContent += "<br>";
        htmlContent += "</body>";

        return htmlContent;
    }

}
