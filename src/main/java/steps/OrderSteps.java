package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static data.OrderData.ORDER_LIST_PATH;
import static data.OrderData.ORDER_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Отправление запроса на создание заказа и получение ответа от сервера")
    public static Response responseOrder(OrderModel orderModel){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(ORDER_PATH)
                .then()
                .statusCode(201)
                .extract().response();
    }

    @Step("Получение списка заказов")
    public static Response getOrderList() {
        return given()
                .log().all()
                .when()
                .get(ORDER_LIST_PATH)
                .then()
                .log().all()
                .extract().response();
    }
}
