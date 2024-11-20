package todolist

class UrlMappings {

    static mappings = {

        "/tasks"(resources:"task") {
            "/actions"(resources:"action")
        }
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        /*"/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')*/
    }
}
