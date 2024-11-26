package org.spock.positive

import io.restassured.RestAssured
import spock.lang.*

class SmokeAddAction  extends Specification{

    def setupSpec() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
    }

    def "create action1"() {

    }

    def "create action2"() {

    }

    def "create action3"() {

    }

    def "create action4"() {

    }

}
