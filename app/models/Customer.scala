package models

/**
  * Created by t.ikawa on 2016/05/26.
  */

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.H2Driver.api._
import slick.driver.JdbcProfile
//import scala.concurrent.ExecutionContext.Implicits.global
import slick.lifted.Tag

case class Customer(ID: Long, name: String, email: String, tel: String, address: String, comment: String)

class CustomerTable(tag: Tag) extends Table[Customer](tag, "customers") {
  def ID = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def email = column[String]("email")
  def tel = column[String]("tel")
  def address = column[String]("address")
  def comment = column[String]("comment")
  def * = (ID, name, email, tel, address, comment) <> (Customer.tupled, Customer.unapply)
}

object CustomerDAO {
  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  lazy val customerQuery = TableQuery[CustomerTable]

  /**
    * キーワード検索
    * @param word
    */
  def search(word: String)(implicit s: Session): List[Customer] = {
    customerQuery.filter(row => (row.name like "%"+word+"%") || (row.email like "%"+word+"%") || (row.tel like "%"+word+"%") || (row.address like "%"+word+"%") || (row.comment like "%"+word+"%")).list
  }

  /**
    * ID検索
    * @param ID
    */
  def searchByID(ID: Long)(implicit s: Session): Customer = {
    customerQuery.filter(_.ID === ID).first
  }

  /**
    * 作成
    * @param customer
    */
  def create(customer: Customer)(implicit s: Session) {
    customerQuery.insert(customer)
  }

  /**
    * 更新
    * @param customer
    */
  def update(customer: Customer)(implicit s: Session) {
    customerQuery.filter(_.ID === customer.ID).update(customer)
  }

  /**
    * 削除
    * @param customer
    */
  def remove(customer: Customer)(implicit s: Session) {
    customerQuery.filter(_.ID === customer.ID).delete
  }
}
