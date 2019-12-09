package json

import play.api.libs.json.{JsPath, Json, Writes}
import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath.write

object JsonTest1 extends App {

  case class Address(rue : String, numero : String)
  case class User(username: String,address : Address)
  implicit val userJsonFormat = Json.format[User]

  val jsonString = """ {"username":"djim","address":{"rue":"rue de paris","numero":"12"}} """
  val jsonObject = Json.parse(jsonString)
  val username = jsonObject \ "address"

  println(username.get)
/*
  val address = Address("rue de paris", "12")
  val user = User("djim", address)


  implicit val addressWrites: Writes[Address] = (
    (JsPath \ "rue").write[String] and
      (JsPath \ "numero").write[String]
    )(unlift(Address.unapply))

  implicit val userWrites : Writes[User] = (
     (JsPath \ "username").write[String] and
      (JsPath \ "address").write[Address]
    )(unlift(User.unapply))

  val sorti = Json.toJson(user)

  println(sorti)
  */

}
