plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PlaceholderAPI
    maven("https://nexus.scarsz.me/content/groups/public/") // DiscordSRV
    maven("https://m2.dv8tion.net/releases") // JDA - Required by DiscordSRV
    maven("https://repo.unnamed.team/repository/unnamed-public/") // creative
    mavenCentral()
}

dependencies {
    val serverApi = "io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT";
    val annotations = "org.jetbrains:annotations:22.0.0";

    // Required libraries
    compileOnly(serverApi)
    compileOnly(annotations)

    // Aho-Corasick implementation (We use this to replace emojis in text)
    implementation("org.ahocorasick:ahocorasick:0.6.3")

    // Creative
    val creativeVersion = "0.7.0-SNAPSHOT"
    compileOnly("team.unnamed:creative-central-api:$creativeVersion")
    testImplementation("team.unnamed:creative-central-api:$creativeVersion")

    // Optional libraries
    compileOnly("net.kyori:adventure-api:4.12.0")
    compileOnly("net.kyori:adventure-text-minimessage:4.12.0")

    // Optional plugin hooks
    compileOnly("me.clip:placeholderapi:2.10.10")
    compileOnly(files("lib/TownyChat-0.91.jar", "lib/EzChat-3.0.3.jar"))
    compileOnly("com.discordsrv:discordsrv:1.26.0")

    // Testing
    testImplementation(serverApi)
    testImplementation(annotations)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    compileTestJava {
        options.encoding = "UTF-8"
    }

    processResources {
        filesMatching("plugin.yml") {
            expand("project" to project)
        }
    }

    shadowJar {
        val pkg = "team.unnamed.emojis.lib"
        relocate("org.ahocorasick", "$pkg.ahocorasick")
    }
}
