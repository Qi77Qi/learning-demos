// example monad:
//  - List: Nil
//  - Option: None
//  - Either (right-biased): Left[A]
//  - IO[A]: IO.raiseError(new Exception("sasss"))
// side effect: println, write to disk, network call, DB call
IO(println("retrieving user 1"))

@ for {
x <- List(1, 2, 3)
y <- List(2, 3, 4)
} yield x + y
res0: List[Int] = List(3, 4, 5, 4, 5, 6, 5, 6, 7)

@ for {
x <- List(1, 2, 3)
y <- nil
} yield x + y
cmd1.sc:3: not found: value nil
y <- nil
^
Compilation Failed

@ for {
x <- List(1, 2, 3)
y <- Nil
} yield x + y
cmd1.sc:4: ambiguous reference to overloaded definition,
both method + in class Int of type (x: Char): Int
and  method + in class Int of type (x: Byte): Int
match argument types (Nothing)
} yield x + y
^
Compilation Failed

@ for {
x <- List(1, 2, 3)
y <- List.empty[Int]
} yield x + y
res1: List[Int] = List()

@ List(1, 2, 3).flatMap(x => List.empty[Int].map(y => x+y))
res2: List[Int] = List()

@

@

@

@ for {
x <- Some(1)
y <- Some(2)
} yield x + y
res3: Option[Int] = Some(value = 3)

@ for {
x <- Some(1)
y <- None
} yield x + y
cmd4.sc:4: ambiguous reference to overloaded definition,
both method + in class Int of type (x: Char): Int
and  method + in class Int of type (x: Byte): Int
match argument types (Nothing)
} yield x + y
^
Compilation Failed

@ import cats.implicits._
import cats.implicits._

@ for {
x <- Some(1)
y <- none[Int]
} yield x + y
res5: Option[Int] = None

@

@

@

@ for {
x <- Right(1)
y <- Right(2)
} yield x + y
res6: Either[Nothing, Int] = Right(value = 3)

@ for {
x <- Right(1)
y <- Left("shit")
} yield x + y
cmd7.sc:4: ambiguous reference to overloaded definition,
both method + in class Int of type (x: Char): Int
and  method + in class Int of type (x: Byte): Int
match argument types (Nothing)
} yield x + y
^
Compilation Failed

@ for {
x <- 1.asRight[String]
y <- "shit".asLeft[Int]
} yield x + y
res7: Either[String, Int] = Left(value = "shit")

@ for {
x <- 1.asRight[String]
y <- "shit".asLeft[Int]
} yield {
y
x
}
res8: Either[String, Int] = Left(value = "shit")

@ for {
x <- 1.asRight[String]
y <- "shit".asLeft[Int]
z <- 2.asRight[String]
foo <- 3.asRight[String]
} yield x + y + z + foo
res9: Either[String, Int] = Left(value = "shit")

@

@

@

@ import cats.effect.IO
import cats.effect.IO

@ for {
x <- IO.pure("hi everyone")
y <- IO.pure("I am Qi")
} yield x + y
res11: IO[String] = FlatMap(ioe = Pure(value = "hi everyone"), f = ammonite.$sess.cmd11$$$Lambda$1592/0x0000000801274460@64e3bc2, event = cats.effect.tracing.TracingEvent$StackTrace)

@

@

@ IO(println("retrieving user 1"))
res12: IO[Unit] = Delay(thunk = ammonite.$sess.cmd12$$$Lambda$1602/0x0000000801275680@9715d26, event = cats.effect.tracing.TracingEvent$StackTrace)

@ res12.unsafeRunSync
cmd13.sc:1: Could not find an implicit IORuntime.

Instead of calling unsafe methods directly, consider using cats.effect.IOApp, which
runs your IO. If integrating with non-functional code or experimenting in a REPL / Worksheet,
add the following import:

import cats.effect.unsafe.implicits.global

Alternatively, you can create an explicit IORuntime value and put it in implicit scope.
This may be useful if you have a pre-existing fixed thread pool and/or scheduler which you
wish to use to execute IO programs. Please be sure to review thread pool best practices to
avoid unintentionally degrading your application performance.

val res13 = res12.unsafeRunSync
^
Compilation Failed

@ import cats.effect.unsafe.implicits.global
import cats.effect.unsafe.implicits.global

@ res12.unsafeRunSync
retrieving user 1


@ res11.unsafeRunSync
res15: String = "hi everyoneI am Qi"

@ Future(println("hi"))
cmd16.sc:1: not found: value Future
val res16 = Future(println("hi"))
^
Compilation Failed

@ for {
_ <- IO(println("hi everyone"))
_ <- IO(println("I am Qi"))
} yield ()
res16: IO[Unit] = FlatMap(
ioe = Delay(thunk = ammonite.$sess.cmd16$$$Lambda$1705/0x00000008012b4000@19c6e821, event = cats.effect.tracing.TracingEvent$StackTrace),
f = ammonite.$sess.cmd16$$$Lambda$1706/0x00000008012b42d8@1dfd3635,
event = cats.effect.tracing.TracingEvent$StackTrace
)

@ res16.unsafeRunSync
hi everyone
I am Qi


@ for {
_ <- IO(println("hi everyone"))
_ <- IO.raiseError(new Exception("shit found"))
_ <- IO(println("I am Qi"))
} yield ()
res18: IO[Unit] = FlatMap(
ioe = Delay(thunk = ammonite.$sess.cmd18$$$Lambda$1729/0x00000008012b6440@5dc90de7, event = cats.effect.tracing.TracingEvent$StackTrace),
f = ammonite.$sess.cmd18$$$Lambda$1730/0x00000008012b6718@3071d086,
event = cats.effect.tracing.TracingEvent$StackTrace
)

@ res18.unsafeRunSync
hi everyone
java.lang.Exception: shit found
ammonite.$sess.cmd18$.$anonfun$res18$2(cmd18.sc:3)
flatMap @ ammonite.$sess.cmd18$.$anonfun$res18$2(cmd18.sc:3)
apply @ ammonite.$sess.cmd18$.<clinit>(cmd18.sc:2)
flatMap @ ammonite.$sess.cmd18$.<clinit>(cmd18.sc:2)


@ for {
x <- {println("first statement"); 1.asRight[String]}
y <- "shit".asLeft[Int]
} yield x + y
first statement
res20: Either[String, Int] = Left(value = "shit")

@ for {
x <- {println("first statement"); 1.asRight[String]}
y <- "shit".asLeft[Int]
} yield x
first statement
res21: Either[String, Int] = Left(value = "shit")

@ for {
_ <- {println("first statement"); 1.asRight[String]}
_ <- "shit".asLeft[Int]
} yield ()
first statement
res22: Either[String, Unit] = Left(value = "shit")

@ for {
_ <- {println("first statement"); 1.asRight[String]}
_ <- "shit".asLeft[Int]
_ <- {println("third statement"); 2.asRight[String]}
} yield ()
first statement
res23: Either[String, Unit] = Left(value = "shit")

@ for {
_ <- IO(println("hi everyone"))
_ <- IO.raiseError(new Exception("shit found")).handleErrorWith(e => ...)
_ <- IO(println("I am Qi"))
} yield ()