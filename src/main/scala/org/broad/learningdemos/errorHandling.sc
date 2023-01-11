/**
  * 1. Option[A]
  * 2. Either[A, B]
  * 3. IO[A]
  * 4. For comprehension
  */
import cats.Functor
import cats.implicits._

def getUserId1(name: String): Int = {
  // if name exists, return userId
  // if name doen't exist, throw exception
  ???
}

// represent the user doesn't exist
def getUserId2(name: String): Option[Int] = {
  if(name == "user1") {
    println("found user1")
    Some(1)
  } else {
    println(s"${name} does not exist")
    None
  }
}

// represent the user desn't exist, we also want to represent DB connection error
final case object UserNotExist extends Throwable
final case object DBConnectionError extends Throwable
def getUserId3(name: String): Either[Throwable, Int] = {
  if(name == "user1") {
    println("found user1")
    Right(1)
  } else {
    Left(UserNotExist)
  }
}

val getUserWithLeftMap = getUserId3("user1").leftMap {
  t =>
    t match {
      case UserNotExist => println("user not exist")
      case DBConnectionError => println("db connection error")
      case _ => println("unknown error")
    }
}

getUserId3("user2").leftMap {
  t =>
    t match {
      case UserNotExist => println("user not exist")
      case DBConnectionError => println("db connection error")
      case _ => println("unknown error")
    }
}


//getUserId3("user1").map(userId => println(s"oh, we got user ${userId}"))
//getUserId3("user2").map(userId => println(s"oh, we got user ${userId}")).map(t => print("asdfaf"))

List(1, 2, 3).map(x => x + 1)

List(1, 2, 3).map(x => List(x + 1)).flatten
List(1, 2, 3).flatMap(x => Option(x + 1))



//getUserId3("user1").flatMap(userId => Right(println(s"oh, we got user ${userId}")))
//getUserId3("user2").flatMap(userId => Right(println(s"oh, we got user ${userId}")))


for {
  userId <- getUserId2("user1")
  _ <- Some(println(s"oh, we got user ${userId}"))
} yield ()

for {
  userId <- getUserId2("user2")
  _ <- Some(println(s"oh, we got user ${userId}"))
  _ <- Some(println(s"oh, we got user ${userId}"))
  _ <- Some(println(s"oh, we got user ${userId}"))
  _ <- Some(println(s"oh, we got user ${userId}"))
  _ <- Some(println(s"oh, we got user ${userId}"))
} yield ()