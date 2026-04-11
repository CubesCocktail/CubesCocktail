plugins {
    java
    id ("io.freefair.lombok") version "9.2.0"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"
    id("com.gradleup.shadow") version "9.4.1"
    id("xyz.jpenilla.run-paper") version "3.0.2" 
}

group = "com.github.zamponimarco"
version = "0.1.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    maven { url = uri("https://maven.enginehub.org/repo/") }
    maven {
        url = uri("https://jitpack.io")
        metadataSources {
            artifact()
        }
    }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/groups/public/") }
    mavenCentral()
}

dependencies {
    implementation("com.github.CubesCocktail:JummesLibs:-a106654fb8-1")

    paperweight.paperDevBundle("26.1.1.build.+")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.16")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    compileOnly("org.projectlombok:lombok:1.18.24")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.shadowJar {
    archiveClassifier.set("")

    exclude("META-INF/**")

    relocate("com.github.jummes.libs", "com.github.zamponimarco.cubescocktail.libs")
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}