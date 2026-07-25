package com.tazhate.sshproxy

import java.util.Random

object PayloadParser {
    private val random = Random()

    fun parse(
        template: String,
        host: String,
        proxy: String? = null,
        sni: String? = null,
        port: Int = 22
    ): String {
        var result = template

        // Replace [crlf] with \r\n
        result = result.replace("[crlf]", "\r\n")
        result = result.replace("[lf]", "\n")

        // Replace [host] with actual host
        result = result.replace("[host]", host)

        // Replace [proxy] with proxy IP/domain
        result = result.replace("[proxy]", proxy ?: host)

        // Replace [ua] with user-agent
        result = result.replace("[ua]", "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36")

        // Handle [rotate=domain1;domain2;domain3]
        result = handleRotate(result)

        // Handle [host_port] - host:port
        result = result.replace("[host_port]", "$host:$port")

        // Handle [protocol] - HTTP/1.1
        result = result.replace("[protocol]", "HTTP/1.1")

        // Handle [rlb] - random load balancing (use host)
        result = result.replace("[rlb]", host)

        return result
    }

    private fun handleRotate(input: String): String {
        val start = input.indexOf("[rotate=")
        if (start == -1) return input

        val end = input.indexOf("]", start)
        if (end == -1) return input

        val listStr = input.substring(start + 8, end)
        val items = listStr.split(";").filter { it.isNotEmpty() }

        if (items.isEmpty()) {
            return input.replace("[rotate=]", "")
        }

        val selected = items[random.nextInt(items.size)]
        val before = input.substring(0, start)
        val after = input.substring(end + 1)

        return before + selected + after
    }
}