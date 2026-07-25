package com.tazhate.sshproxy

data class ServerConfig(
    var profileName: String = "",
    var host: String = "",
    var port: Int = 22,
    var username: String = "",
    var authType: String = "password",
    var password: String = "",
    var privateKey: String = "",
    var tunnelMode: String = "dynamic",
    var localPort: Int = 1080,
    var proxyType: String = "SOCKS5",
    var payload: String = "",
    var sni: String = "",
    var remoteProxyHost: String = "",
    var remoteProxyPort: Int = 80,
    var remoteProxyUser: String = "",
    var remoteProxyPass: String = "",
    var dnsPrimary: String = "8.8.8.8",
    var dnsSecondary: String = "1.1.1.1",
    var note: String = "",
    var expiresAt: String = ""
)