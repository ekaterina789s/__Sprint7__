package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLoginModel;
import static data.CourierData.DELETE_COURIER_ID;
import static data.CourierData.LOGIN_COURIER_PATH;
import static io.restassured.RestAssured.given;

public class CourierLoginSteps {
    @Step("Возвращение ответа от сервера на ручку /api/v1/courier/login")
    public static Response loginCourier(CourierLoginModel courierLogin) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierLogin)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then()
                .extract().response();
    }


    @Step("Удаление курьера по id, ручка /api/v1/courier/{id}")
    public static void deleteCourier(Integer courierId) {
        given()
                .when()
                .delete(DELETE_COURIER_ID + courierId);
    }

    @Step("Получение ответа от сервера на запрос об авторизации курьера без логина")
    public static Response responseCourierWithoutLogin(CourierLoginModel courierWithoutLogin) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierWithoutLogin)
                .when()
                .post(LOGIN_COURIER_PATH);

    }

    @Step("Получение ответа от сервера на запрос об авторизации курьера без пароля")
    public static Response responseCourierWithoutPassword(CourierLoginModel courierWithoutPassword) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierWithoutPassword)
                .when()
                .post(LOGIN_COURIER_PATH);

    }

    @Step("Получение ответа от сервера на запрос об авторизации курьера с несуществующим логином")
    public static Response responseCourierNonExistentLogin(CourierLoginModel courierInvalideLogin) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierInvalideLogin)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then()
                .extract().response();
    }


    @Step("Получение ответа от сервера на запрос об авторизации курьера с несуществующим паролем")
    public static Response responseCourierNonExistentPassword(CourierLoginModel courierInvalidePassword) {
        return given().
                log().all()
                .contentType(ContentType.JSON)
                .body(courierInvalidePassword)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then()
                .extract().response();
    }

    @Step("Получение ответа от сервера на запрос об авторизации курьера с несуществующимИ логином и паролем")
    public static Response responseCourierNonExistentLoginAndPassword(CourierLoginModel courierInvalideLoginAndPassword) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierInvalideLoginAndPassword)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then()
                .extract().response();
    }

    @Step("Получение ответа от сервера на запрос об авторизации курьера c существующим логином")
    public static Response responseBodyLoginCourier(CourierLoginModel courierLogin) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierLogin)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then()
                .extract().response();

    }
}

