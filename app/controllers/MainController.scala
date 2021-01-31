package controllers

import models._
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext

class MainController @Inject()(repo: TaskRepository,
                               cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {


  def index: Action[AnyContent] = Action.async { implicit request =>
    repo.list().map { task =>
      Ok(views.html.index(task))
    }
  }

  def addTaskForm: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.taskForm("Добавить задачу", Task(0, ""), "Добавить", routes.MainController.addTask()))
  }

  def addTask: Action[AnyContent] = Action.async { implicit request =>
    val title = request.body.asFormUrlEncoded.get("title").head
    repo.create(title).map { _ =>
      Redirect(routes.MainController.index)
    }
  }

  def updateTaskForm: Action[AnyContent] = Action.async { implicit request =>
    val id = request.queryString.get("id").head.head.toLong
    repo.readOneTask(id).map { task =>
      Ok(views.html.taskForm("Редактировать задачу", task, "Изменить", routes.MainController.updateTask()))
    }
  }

  def updateTask: Action[AnyContent] = Action.async { implicit request =>
    val id = request.body.asFormUrlEncoded.get("id").head.toLong
    val title = request.body.asFormUrlEncoded.get("title").head
    repo.update(Task(id, title)).map { _ =>
      Redirect(routes.MainController.index)
    }
  }

  def deleteTask: Action[AnyContent] = Action.async { implicit request =>
    val id = request.body.asFormUrlEncoded.get("id").head.toLong
    repo.delete(id).map { _ =>
      Redirect(routes.MainController.index)
    }
  }

}
