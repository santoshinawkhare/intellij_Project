package com.spotify;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SpotifyApi {
    public String token;
    public  String trackId= "5Ox43gIWUNW6pAgx3F3oi7";
    public String userId= "31c37kmb2g4lnavoo5gjrnmrm7xi";
    public String playlistId= "6RMeUE30jw7CDQa8giYmda";



    @BeforeTest
    public void getToken() {
        token="Bearer BQCdq9nJh-JN3F5rxL20jwXSTZYH5kHMsy1YEXN-BZ3lzO_RfLmuRcXjsnDTBv5EhNfl2t7DbHlvamFckCINhQ4hJwuSttSZ1-ycndMouHGauRQJQ9ZjnvAT3_SNRNcAkF9W0ohH-z8YoqFReYZc9Z5f7rzQgBeVlF32XtFU_DWXuDmhwNiSSiBzqL0Px5_A_GdMlk5mKjAIc1OkWkpU6IuMbBHTsEzhPlnQSPUGawrL5_5tBf2KMXsn3C_1YrLl1CABGSikkKMLdTzGwrzKjIY3s5wkAMLoNxBFfgopQUvZN4X5Zv-zJlf80EWP-dmYVz-VO1oQMA";
        //   userId = "31ksdb253w3qxomtatkeoj7kvbfm";
        //  playlistId = "2o5I9wP7FH36ebPt6mPjRf";
        // trackId = "spotify:track:0sSapNABKrGwgaMhn0p8uL,spotify:track:2mTFrtswcKRUAC6rwoW63X,spotify:track:2mTFrtswcKRUAC6rwoW63X";
    }

    @Test (priority = 1)
    public void testGet_CurrentUserProfile() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/me");
        response.prettyPrint();
        userId=response.path("Id");
        System.out.println("userID:"+userId);
        int StatusCode = response.getStatusCode();
        System.out.println("StatusCode" +StatusCode);
        Assert.assertEquals(StatusCode,200);
    }
    @Test (priority = 2)
    public void testGet_UserProfile() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/users/"+userId+"");
        response.prettyPrint();
        userId=response.path("Id");
        System.out.println("userID:"+userId);
        int StatusCode = response.getStatusCode();
        System.out.println("StatusCode" +StatusCode);
        response.then().assertThat().statusCode(200);
    }
//    @Test (priority = 3)
//    public void createPlaylist() {
//        Response response = RestAssured.given().contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .header("Authorization", token)
//                .body("{\n" +
//                        "  \"name\": \"Spotify automation playlist\",\n" +
//                        "  \"description\": \"New playlist description\",\n" +
//                        "  \"public\": false\n" +
//                        "}")
////                .post ("https://api.spotify.com/v1/users/6RMeUE30jw7CDQa8giYmda/playlists");
//        .post("https://api.spotify.com/v1/users/"+userId+"/playlists");
//        response.prettyPrint();
//        playlistId = response.path("Id");
//        System.out.println("playlistID:"+playlistId);
//        response.then().assertThat().statusCode(201);
//       }
    @Test (priority = 4)
    public void searchForItem() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .queryParam("q","Arijit singh")
                .queryParam("type","track")
                .when()
                .get("https://api.spotify.com/v1/search");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
    @Test (priority = 5)
    public void AddItemInPlaylist() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .queryParam("uris","spotify:track:6IvNrrm0WBlqJYkhmkwk6F")
                .post("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(201);

    }
    @Test (priority = 6)
    public void UpdatePlaylistItem() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"range_start\": 2,\n" +
                        "  \"insert_before\": 0,\n" +
                        "  \"range_length\": 11\n" +
                        "}")
                .put("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test (priority = 7)

    public void ChangePlaylistDetails() {

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"name\": \"Spotify automation playlist\",\n" +
                        "  \"description\": \"Updated playlist description\",\n" +
                        "  \"public\": false\n" +
                        "}")
                .put("https://api.spotify.com/v1/playlists/"+playlistId);

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
    @Test (priority = 8)

    public void GetUserCurrentPlaylist() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/me/playlists");
        response.prettyPrint();
        playlistId=response.path("Id");
        System.out.println("userID:"+playlistId);
        int StatusCode = response.getStatusCode();
        System.out.println("StatusCode" +StatusCode);
        response.then().assertThat().statusCode(200);


    }
    @Test (priority = 18)

    public void GetUserPlaylist() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/users/31c37kmb2g4lnavoo5gjrnmrm7xi/playlists");
        response.prettyPrint();
        playlistId=response.path("Id");
        System.out.println("userID:"+playlistId);
        int StatusCode = response.getStatusCode();
        System.out.println("StatusCode" +StatusCode);
        response.then().assertThat().statusCode(200);


    }
    @Test (priority = 9)

    public void GetPlaylistItem() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        playlistId=response.path("Id");
        System.out.println("userID:"+playlistId);
        response.then().assertThat().statusCode(200);


    }
    @Test (priority = 10)

    public void GetPlaylistCoverImage() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/playlists/"+playlistId+"/images");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 11)

    public void GetPlayList() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/playlists/"+playlistId+"");
        response.prettyPrint();
        playlistId=response.path("Id");
        System.out.println("userID:"+playlistId);
        int StatusCode = response.getStatusCode();
        System.out.println("StatusCode" +StatusCode);
        response.then().assertThat().statusCode(200);
    }
    @Test (priority = 16)

    public void RemovePlaylistItem() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .body("{ \"tracks\": [{ \"uri\": \"spotify:track:5B4YQN1FCuADJ0o4phAtwC\" }] }")
                .delete ("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 12)

    public void GetTrackAudioAnalysis() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/audio-analysis/5Ox43gIWUNW6pAgx3F3oi7");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 13)

    public void GetTracksAudioFeatures() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/audio-features");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 15)
    public void GetSeveralTracks() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .param("ids",trackId)
                .when()
                .get ("https://api.spotify.com/v1/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 17)
    public void GetTracksAudioFeaturesId() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/audio-features/5Ox43gIWUNW6pAgx3F3oi7");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }
    @Test (priority = 14)

    public void GetTrack() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get ("https://api.spotify.com/v1/tracks/5Ox43gIWUNW6pAgx3F3oi7");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
}
