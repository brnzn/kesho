package migration.writer

abstract class DataWriter {
  def insert(values: Array[String])
}