package main.scala

import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.MongoClient
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.MongoCollection
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter
import main.scala.Helpers._
import org.mongodb.scala.FindObservable
import org.mongodb.scala.bson.collection.immutable.Document

object MongoDB2Files {
  /*
   * args[0]: value of "subject"
   * args[1]: output directory (/home/duytri/Desktop/testMongo/)
   * args[2]: prefix of output file (ex: "sh")
   * args[3]: limit number of result
   * */
  def main(args: Array[String]): Unit = {
    val mongoClient: MongoClient = MongoClient("mongodb://PTNHTTT07:27017")

    val database: MongoDatabase = mongoClient.getDatabase("allvnexpress")
    val collection: MongoCollection[Document] = database.getCollection("all")
    val limit = args(3).toInt
    var data: FindObservable[Document] = null
    if (limit == 0) {
      data = collection.find(equal("subject", args(0))).projection(exclude("_id", "title", "link", "subject"))
    } else {
      data = collection.find(equal("subject", args(0))).limit(limit).projection(exclude("_id", "title", "link", "subject"))
    }
    var iFileName = 0
    println("No. of doc in collection: " + collection.count.headResult())
    //data.first().printResults()
    println("No. of doc in query: " + data.results.length)
    data.results.foreach { doc =>
      {
        val file = new File(args(1) + args(2) + iFileName)
        var content = doc.head._2.asString.getValue
        val bw = new BufferedWriter(new FileWriter(file, true))
        bw.flush()
        bw.write(content.trim)
        bw.close()
        iFileName = iFileName + 1
      }
    }
    mongoClient.close()
  }
}