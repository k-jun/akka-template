package app.controllers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.Done
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes

import scala.io.StdIn

import scala.concurrent.Future

import app.models.todoModels._
import app.db.mysql.{connection, returnKey}


object todoControllers {
    def readTodo(id: Int): Todo = {
        var st = connection.prepareStatement("SELECT * FROM todo WHERE id = ?")
        st.setInt(1, id)
        val rs = st.executeQuery()
        // var todos: [Todo] = []
        if(rs.next()) {
            new Todo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4))
        } else {
            new Todo(0, "", "", "")
        }
    }

    def createTodo(todo: NewTodo): Todo = {
        var st = connection.prepareStatement("INSERT INTO todo (name) VALUES (?)", returnKey)
        st.setString(1, todo.name)
        val ar = st.executeUpdate()
        if (ar == 0) {
            new Todo(0, "", "", "")
        }

        var keys = st.getGeneratedKeys()
        if (keys.next()) {
            new Todo(keys.getInt(1), todo.name, "", "")
        } else {
            new Todo(0, "", "", "")
        }
    }

    def updateTodo(id: Int, todo: NewTodo): Todo = {
        var st = connection.prepareStatement("UPDATE todo SET name = ? WHERE id = ?", returnKey)
        st.setString(1, todo.name)
        st.setLong(2, id)
        val ar = st.executeUpdate()
        if (ar == 0) {
            new Todo(0, "", "", "")
        }
        new Todo(id, todo.name, "", "")
    }

    def deleteTodo(id: Int): Todo = {
        var st = connection.prepareStatement("DELETE FROM todo WHERE id = ?", returnKey)
        st.setInt(1, id)
        val ar = st.executeUpdate()
        if (ar == 0) {
            new Todo(0, "", "", "")
        }
        new Todo(id, "", "", "")
    }

}