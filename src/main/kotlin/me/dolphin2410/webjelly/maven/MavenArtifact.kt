package me.dolphin2410.webjelly.maven

data class MavenArtifact(val groupId: String, val artifactId: String, val version: String) {
    companion object {
        @JvmStatic
        fun from(artifactName: String): MavenArtifact {
            return artifactName.split(":").run {
                MavenArtifact(this[0], this[1], this[2])
            }
        }
    }

    override fun toString(): String {
        return "$groupId:$artifactId:$version"
    }
}