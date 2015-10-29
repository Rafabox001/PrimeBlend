package com.manguitostudios.primeblend.Utils;

/**
 * Created by manguitodeveloper01 on 10/8/15.
 */
public class Constants {
    /**
     * Urls para proceso de registro y evaluacion
     */
    public static final String registerUrl = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=set_user";
    public static final String validateEmailUrl = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=get_users&email=";
    public static final String postSurvey = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=set_survey";
    public static final String postSurvey2 = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=set_survey_buy";


    /**
     * Urls para consulta de categorias, subcategorias y productos
     */
    public static final String getAllCategories = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=get_categories";
    public static final String getSubcategoriesByCategorY = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=get_categories&parent=";
    public static final String getProductsByCategory = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=get_products&category_id=";
    public static final String getProductsById = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=get_products&product_id=";

    /**
     * Valores para consulta de productos
     */
    public static final String iomabe = "15";
    public static final String monogram = "13";
    public static final String profile = "14";

    public static final String iomabe_coccion = "16";
    public static final String iomabe_colecciones = "20";
    public static final String iomabe_lavavajillas = "19";
    public static final String iomabe_refrigeracion = "17";

    public static final String monogram_coccion = "26";
    public static final String monogram_colecciones = "30";
    public static final String monogram_lavavajillas = "29";
    public static final String monogram_refrigeracion = "27";

    public static final String profile_coccion = "21";
    public static final String profile_colecciones = "25";
    public static final String profile_lavavajillas = "24";
    public static final String profile_refrigeracion = "22";

    /**
     * Urls para calendario
     */
    public static final String calendar_events = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=get_events";
    public static final String register_calendar_mail = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=set_events_email";

    /**
     * Urls para cotizador
     */
    public static final String exchange_rate = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=get_exchange_rate";
    public static final String sendValuation = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=set_quote";

}
