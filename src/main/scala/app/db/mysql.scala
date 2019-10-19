package app.db

import java.sql.{Connection,DriverManager,Statement}

object mysql {
    val url = "jdbc:mysql://127.0.0.1:3306/sample"
    val driver = "com.mysql.jdbc.Driver"
    val username = "root"
    val password = ""
    Class.forName(driver)
    var connection: Connection = _
    val returnKey = Statement.RETURN_GENERATED_KEYS

    def initialize(): Unit = {
        connection = DriverManager.getConnection(url, username, password)
        println("connected to mysql db!")
    }
}