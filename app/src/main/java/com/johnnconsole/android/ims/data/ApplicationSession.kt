package com.johnnconsole.android.ims.data

class ApplicationSession {

    companion object {
        var username = ""
        var last_name = ""
        var first_name = ""
        var access = -1

        fun create(username: String, last_name: String, first_name: String, access: Int) {
            this.username = username
            this.last_name = last_name
            this.first_name = first_name
            this.access = access
        }

        fun destroy() {
            this.username = ""
            this.last_name = ""
            this.first_name = ""
            this.access = -1
        }
    }
}