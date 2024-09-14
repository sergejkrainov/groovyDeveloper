package org.json


import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder
import groovy.xml.XmlSlurper

static void main(String[] args) {
    String jsonStr = getClass().getResource("/json/test.json").getText("UTF-8")
    def jsnBld = new JsonBuilder(new JsonSlurper().parseText(jsonStr))
    Map content = jsnBld.getContent()
    println(content.get("name"))
    def writerHtml = new StringWriter()
    def html = new MarkupBuilder(writerHtml)

    html.div {
        div(id: "employee") {
            p "name:" + content.get("name")
            p "age:" + content.get("age")
            p "secretIdentity:" + content.get("secretIdentity")
            ul(id = "powers") {
                li content.get("powers")[0]
                li content.get("powers")[1]
                li content.get("powers")[2]
            }
        }
    }

    def htmlStr = writerHtml.toString()
    println(htmlStr)

    def htmlPath = System.getProperty("user.dir") + "/../../../resources/html/"
    def file = new File(htmlPath + "test.html")
    file.withWriter("UTF-8") {writer ->
         writer << htmlStr
    }


    def writerXml = new StringWriter()
    def xml = new MarkupBuilder(writerXml)

    xml.employee {
        name content.get("name")
        age content.get("age")
        secretIdentity content.get("secretIdentity")
        powers {
            power content.get("powers")[0]
            power content.get("powers")[1]
            power content.get("powers")[2]
        }
    }

    def xmlStr = writerXml.toString()
    println(xmlStr)

    def xmlPath = System.getProperty("user.dir") + "/../../../resources/xml/"
    file = new File(xmlPath + "test.xml")
    file.withWriter("UTF-8") {writer ->
        writer << htmlStr
    }




}