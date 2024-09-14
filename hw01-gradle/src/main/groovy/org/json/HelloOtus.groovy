package org.json

import static com.google.common.base.Strings.*

static void main(String[] args) {
    def str
    println "Hello world in module!"
    str = nullToEmpty(str)
    println "strInModule=" + str
}