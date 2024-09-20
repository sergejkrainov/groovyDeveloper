package org.serverconfig

class ServerConfig {
    def name, description, path
    List<ServerProps> mappings = []
    HttpConf httpconf
    HttpsConf httpsConf

}
