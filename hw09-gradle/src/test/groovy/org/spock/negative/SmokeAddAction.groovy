package org.spock.negative

import io.restassured.RestAssured
import spock.lang.*

class SmokeAddAction  extends Specification {

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
    }

    def "create action1"() {

    }
}
