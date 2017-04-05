package angolodelleidee.catalogovirtuale;

/**
 * Created by diego on 02/04/2017.
 */

public final class Resource {
    private Resource(){
        throw new AssertionError();
    }
    public static final String BASE_URL = "http://192.168.0.7/adi_cv/";
    public static final String LOGIN_PAGE = "login.php";
    public static final String SEND_ORDER_PAGE = "saveOrder.php";
}
