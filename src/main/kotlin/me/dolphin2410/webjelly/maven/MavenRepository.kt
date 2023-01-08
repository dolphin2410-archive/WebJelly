package me.dolphin2410.webjelly.maven

import java.net.URL

class MavenRepository(val url: URL) {
    companion object {
        @JvmStatic
        fun from(url: String): MavenRepository {
            if (url == "mavenCentral") return MAVEN_CENTRAL
            if (url == "mavenLocal") return MAVEN_LOCAL
            return MavenRepository(URL(url))
        }

        @JvmStatic
        val MAVEN_CENTRAL = MavenRepository(URL("https://repo.maven.apache.org/maven2/"))

        @JvmStatic
        val MAVEN_LOCAL = MavenRepository(URL("${System.getProperty("user.home")}/.m2/repository"))
    }
}