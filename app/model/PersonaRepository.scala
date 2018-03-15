package model

import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.concurrent.{ExecutionContext, Future}

/**
  * A repository for people.
  *
  * @param dbConfigProvider The Play db config provider. Play will inject this for you.
  */
@Singleton
class PersonaRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec:ExecutionContext)  {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val persona = lifted.TableQuery[PersonaTable]

  def all(): Future[Seq[Persona]] = db.run(persona.result)
  def byId(id:Long):Future[Seq[Persona]]=db.run(persona.filter(_.id===id).result)
  val schema = persona.schema
  db.run(DBIO.seq(
    schema.create
  ))

  private class PersonaTable(tag: Tag) extends Table[Persona](tag, "persona") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def age = column[Int]("age")


    override def * = (id, name,age) <> ((Persona.apply _).tupled, Persona.unapply)
  }


}
