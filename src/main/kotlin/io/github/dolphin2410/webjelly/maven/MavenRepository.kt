package io.github.dolphin2410.webjelly.maven

import java.net.URL

class MavenRepository(val url: URL) {
    companion object {
        @JvmStatic
        fun from(url: String): MavenRepository {
            return MavenRepository(URL(url))
        }

        @JvmStatic
        val MAVEN_CENTRAL = MavenRepository(URL("https://repo.maven.apache.org/maven2/"))
    }
}