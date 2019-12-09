package xml
import scala.xml.XML
object ParseXml extends App {


  val book: scala.xml.Elem = <book id="b20234">Magic of scala-xml</book>

  val id = book \@ "id"

  println(id)
  println(book.text)


  val books =
    <books>
      <book id="b1615">Don Quixote</book>
      <book id="b1867">War and Peace</book>
    </books>

  val titles = (books \ "book").map(book => book.text).toList

  val bookSeq = books \ "book"

  println(bookSeq)
}
