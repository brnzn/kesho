package migration

abstract class DataWriter {
  def insert(values: Array[String])
}