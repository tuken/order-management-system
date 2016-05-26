package models

/**
  * Created by t.ikawa on 2016/05/26.
  */

import play.api.db.slick._

case class Customer(ID: Long, name: String, email: String, tel: String, address: String, comment: String)
