import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TrelloAPIsTestPackage {
    //variables
    public static String Key="4a54da48a15b26cf34cf1cb4c182c694";
    public static String Token="4724d8bbe3644ddaa5061a22590fdc8e8b6ab8a708fcf223f362147e968b735b";
    public static String displayName="Organization Test";
    public static String organizationId;
    public static String memberId;
    public static String boardName="new Board";
    public static String boardId;
    public static String listName="New List";
    public static String listId;

    //Test Cases

    @Test(priority = 1)
    public static void accountCredential(){
        Response response=
                RestAssured
                        .given()
                        .baseUri("https://api.trello.com")
                        .basePath("/1/members/me")
                        .queryParam("key",Key)
                        .queryParam("token",Token)
                        .when()
                        .get();

        response.prettyPrint();
        response.then().statusCode(200);
        memberId = response.then().extract().path("id");
        System.out.println("Member ID= "+ memberId);
    }

    @Test(priority = 2)
    public static void createNewOrganization() {
        Response response = RestAssured
                .given()
                .baseUri("https://api.trello.com")
                .basePath("/1/organizations")
                .header("Content-Type", "application/json")
                .queryParam("displayName", displayName)
                .queryParam("key", Key)
                .queryParam("token", Token)
                .when()
                .post();


        response.prettyPrint();
        response.then().statusCode(200);
        organizationId = response.then().extract().path("id");
        System.out.println("Organization ID= " + organizationId);
    }

    @Test(priority = 3)
    public static void getOrganizationForMember(){
        Response response=
                RestAssured
                        .given()
                        .baseUri("https://api.trello.com")
                        .basePath("/1/members/"+memberId+"/organizations")
                        .queryParam("key",Key)
                        .queryParam("token",Token)
                        .when()
                        .get();

        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 4)
    public static void createBoard() {
        Response response = RestAssured
                .given()
                .baseUri("https://api.trello.com")
                .basePath("/1/boards/")
                .header("Content-Type", "application/json")
                .queryParam("name", boardName)
                .queryParam("key", Key)
                .queryParam("token", Token)
                .queryParam("idOrganization", organizationId)
                .when()
                .post();


        response.prettyPrint();
        response.then().statusCode(200);
        boardId = response.then().extract().path("id");
        System.out.println("Board ID= " + boardId);
    }

    @Test(priority = 5)
    public static void getBoardsInsideOrganization(){
        Response response=
                RestAssured
                        .given()
                        .baseUri("https://api.trello.com")
                        .basePath("/1/organizations/"+organizationId+"/boards")
                        .queryParam("key",Key)
                        .queryParam("token",Token)
                        .when()
                        .get();

        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 6)
    public static void createNewList() {
        Response response = RestAssured
                .given()
                .baseUri("https://api.trello.com")
                .basePath("/1/lists")
                .header("Content-Type", "application/json")
                .queryParam("name", listName)
                .queryParam("idBoard", boardId)
                .queryParam("key", Key)
                .queryParam("token", Token)
                .when()
                .post();


        response.prettyPrint();
        response.then().statusCode(200);
        listId = response.then().extract().path("id");
        System.out.println("Board ID= " + listId);
    }

    @Test(priority = 7)
    public static void getListsOnBoard(){
        Response response=
                RestAssured
                        .given()
                        .baseUri("https://api.trello.com")
                        .basePath("/1/boards/"+boardId+"/lists")
                        .queryParam("key",Key)
                        .queryParam("token",Token)
                        .when()
                        .get();

        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 8)
    public static void archiveOrUnarchiveList() {
        Response response = RestAssured
                .given()
                .baseUri("https://api.trello.com")
                .basePath("/1/lists/"+listId+"/closed")
                .header("Content-Type", "application/json")
                .queryParam("name", listName)
                .queryParam("idBoard", boardId)
                .queryParam("key", Key)
                .queryParam("token", Token)
                .queryParam("value", "true")
                .when()
                .put();


        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 9)
    public static void deleteBoard(){
        Response response=
                RestAssured
                        .given()
                        .baseUri("https://api.trello.com")
                        .basePath("/1/boards/"+boardId)
                        .queryParam("key",Key)
                        .queryParam("token",Token)
                        .when()
                        .delete();

        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 10)
    public static void deleteOrganization(){
        Response response=
                RestAssured
                        .given()
                        .baseUri("https://api.trello.com")
                        .basePath("/1/organizations/"+organizationId)
                        .queryParam("key",Key)
                        .queryParam("token",Token)
                        .when()
                        .delete();

        response.prettyPrint();
        response.then().statusCode(200);
    }
}
