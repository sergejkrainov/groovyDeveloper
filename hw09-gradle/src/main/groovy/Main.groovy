import groovy.util.logging.Log


@Log
class MainStart {

  static void main(String[] args) {
    HTTP rq = new HTTP()
    rq.sendHttp()

  }
}

