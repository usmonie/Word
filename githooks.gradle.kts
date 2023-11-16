tasks.register("copyGitHooks", Copy::class) {
    description = "Copies the git hooks from team-props/git-hooks to the .git folder."
    from("$rootDir/configs/githooks/") {
        include("**/*.sh")
        exclude { file ->
            val relativePath = file.relativePath
                .pathString
                .substringBefore(delimiter = ".sh")
            val targetFile = File("$rootDir/.git/hooks/", relativePath)
            targetFile.exists()
        }
        rename("(.*).sh", "$1")
    }
    into("$rootDir/.git/hooks")
}

tasks.register("installGitHooks", Exec::class) {
    description = "Installs the pre-commit git hooks from team-props/git-hooks."
    group = "git hooks"
    workingDir = rootDir
    commandLine("chmod")
    args("-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    doLast {
        logger.info("Git hook installed successfully.")
    }
}