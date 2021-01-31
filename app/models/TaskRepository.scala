package models

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

/**
 * A repository for task.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class TaskRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  private class TaskTable(tag: Tag) extends Table[Task](tag, "task") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    /**
     * This is the tables default "projection".
     *
     * It defines how the columns are converted to and from the Task object.
     */
    def * = (id, title) <> ((Task.apply _).tupled, Task.unapply)
  }

  /**
   * The starting point for all queries on the task table.
   */
  private val task = TableQuery[TaskTable]

  def create(title: String): Future[Task] = db.run {
    (task.map(t => (t.title))
      returning task.map(_.id)
      into ((title, id) => Task(id, title))
      ) += (title)
  }

  def readOneTask(id: Long): Future[Task] = db.run {
    task.filter(_.id === id).take(1).result.head
  }

  def update(updatedTask: Task): Future[Int] = db.run {
    task.filter(_.id === updatedTask.id).map(_.title).update(updatedTask.title)
  }

  def delete(id: Long): Future[Int] = db.run {
    task.filter(_.id === id).delete
  }

  def list(): Future[Seq[Task]] = db.run {
    task.sortBy(_.id).result
  }

}
