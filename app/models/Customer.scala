package models

/**
  * Created by t.ikawa on 2016/05/26.
  */

import play.api.db.slick.Config.driver.simple_

case class Customer(ID: Long, name: String, email: String, tel: String, address: String, comment: String)

class CustomerTable(tag: Tag) extends Table[Customer](tag, "customers") {
  def ID = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def email = column[String]("email", O.NotNull)
  def tel = column[String]("tel", O.NotNull )
  def address = column[String]("address", O.NotNull)
  def comment = column[String]("comment", O.NotNull)
  def * = (ID, name, email, tel, address, comment) <> (Customer.tupled, Customer.unapply)
}

object CustomerDAO {
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
