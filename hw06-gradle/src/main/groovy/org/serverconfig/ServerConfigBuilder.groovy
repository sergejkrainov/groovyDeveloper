package org.serverconfig

class ServerConfigBuilder {

    static ServerConfig build(@DelegatesTo(ServerConfig) Closure callable) {
        ServerConfig cfg = new ServerConfig()
        def rehydrate = callable.rehydrate(cfg, this, this)
        rehydrate.resolveStrategy = Closure.DELEGATE_ONLY
        rehydrate()
        cfg
    }


}
