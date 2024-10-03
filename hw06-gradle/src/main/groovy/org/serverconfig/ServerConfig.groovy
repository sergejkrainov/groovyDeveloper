package org.serverconfig

class ServerConfig {
    public name, description, path
    List<ServerProps> mappings = []
    HttpConf httpConf
    HttpsConf httpsConf

    private static final ServerConfig INSTANCE = new ServerConfig();

    private ServerConfig() {
    }

    static ServerConfig getInstance() {
        return INSTANCE;
    }

    @Override
    String toString() {
        println("""
        ServerConfig:
        name:${name}
        description:${description}
        http.port: ${httpConf.port}   
        http.secure: ${httpConf.secure} 
        https.port: ${httpsConf.port}   
        https.secure: ${httpsConf.secure} 
        """)
        int i = 0
        for(ServerProps sp in mappings) {
            println("""mappings[${i}]:
            url:${sp.url}
            active:${sp.active}
            """)
            i++
        }

    }

}
