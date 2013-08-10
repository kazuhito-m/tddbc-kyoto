def path = args[0]
//def path = "/Users/kyon_mm/repos/hg/bb/GebSample"
def build = "gradle --daemon build"
def dir = new File(path)
def m = [:]
def isBuildTarget = false
def exlist = [".hg", ".idea", ".gradle", "build", "out"]
def interval = 10 * 1000


println "Start!"
while(true){
    dir.eachFileRecurse{filepath ->

        if(!exlist.any{filepath.absolutePath.startsWith(dir.absolutePath + "/$it")}){
            if(m[filepath.absolutePath] != filepath.lastModified()){
                println filepath.absolutePath

                isBuildTarget = true
                m << [(filepath.absolutePath): filepath.lastModified()]
            }
        }
    }
    if(isBuildTarget){
        println "Build:${new Date().format("yyyy/MM/dd hh:mm:ss")}"
        def p = build.execute()
        p.waitFor()
        "hg parent --template {branch}\n".execute().in.text.contains("default") ? "hg branch ${new Date().format("yyyy_MM_dd_hh_mm_ss")}".execute().waitFor() : null
        "hg addremove".execute().waitFor()
        $/hg ci -m ${p.exitValue() == 0 ? "green" : "red"}/$.execute().waitFor()
    }
    isBuildTarget = false
    sleep(interval)
}
