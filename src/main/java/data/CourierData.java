package data;

public class CourierData {
    //Константа для урл
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    //корректные значения ключей
    public static final String LOGIN = "katya_mas_" + System.currentTimeMillis(); //System.currentTimeMillis() показывает частичку(время до секунды такое-то. Например: junit_ninja1779894035821)
    public static final String PASSWORD = "1234";
    public static final String FIRST_NAME = "katya";
    //поле для проверки создания курьера с повторным логином
    public static final String LOGIN_REPLAY = LOGIN;
    //несуществующий и невалидный логин
    public static final String LOGIN_INVALIDE = "===";
    //несуществующий и невалидный пароль
    public static final String PASSWORD_INVALIDE = "!!#";
    //эндпоинт для курьера
    public static final String CREATE_COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_COURIER_PATH =  "/api/v1/courier/login";
    public static final String DELETE_COURIER_ID = "/api/v1/courier/";


}
