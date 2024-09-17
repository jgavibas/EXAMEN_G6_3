package org.examen.g6.ex3.aggregates.constants;

public class Constants {
    public static final Boolean ESTADO_ACTIVO=true;
    public static final String CLAIM_ROLE = "rol";
    //public static final String ENPOINTS_USER = "/api/v1/user/**";
    public static final String ENPOINTS_USER = "/api/v1/users";
    public static final String ENPOINTS_USER_HEALTHCHECK = "/api/v1/users/healthcheck";
    public static final String ENPOINTS_USER_LOGIN = "/api/v1/users/login";
    public static final String ENPOINTS_USER_REGISTER = "/api/v1/users/register";
    public static final String CLAVE_AccountNonExpired ="isAccountNonExpired";
    public static final String CLAVE_AccountNonLocked ="isAccountNonLocked";
    public static final String CLAVE_CredentialsNonExpired = "isCredentialsNonExpired";
    public static final String CLAVE_Enabled = "isEnabled";
    public static final Integer ERROR_TRX_CODE = 4009;
    public static final String ERROR_TRX_MESS = "ERROR DURANTE LA TRANSACCION ";
    public static final Integer ERROR_TRX_API_EXCEPTION_CODE = 4006;
    public static final String ERROR_TRX_API_EXCEPTION_MESS = "ERROR DURANTE LA TRANSACCION - EXCEPTION PERSONALIZADA:  ";
    public static final Integer OK_CODE_LIST= 2000;
    public static final String OK_MESS_LIST = "EJECUTADO CORRECTAMENTE";
    public static final Integer ERROR_CODE_LIST_EMPTY= 2009;
    public static final String ERROR_MESS_LIST_EMPTY = "NO HAY REGISTROS!!";
    public static final Integer OK_SAVE = 2000;
    public static final String OK_SAVE_MESS = "EJECUTADO SIN PROBLEMAS!";
    public static final Integer ERROR_SAVE = 2004;
    public static final String ERROR_SAVE_MESS = "ERROR AL EJECUTAR LA OPERACIÓN";
    public static final Integer OK_DNI_CODE = 2000;
    public static final String OK_DNI_MESS = "EJECUTADO SIN PROBLEMAS!";
    public static final Integer ERROR_DNI_CODE = 2004;
    public static final String ERROR_DNI_MESS = "ERROR CON EL DNI";
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_INACTIVE = 0;
    public static final Integer REDIS_EXP = 1;
    public static final String REDIS_KEY_API_PERSON = "MS:EXAMEN:G6:";
}
