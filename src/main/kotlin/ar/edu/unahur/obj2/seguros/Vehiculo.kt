package ar.edu.unahur.obj2.seguros

abstract class Vehiculo(val valor: Int, val antiguedad: Int) {

  val seguros = mutableListOf<Seguro>()

  open fun costoPara(seguro: Seguro): Double = valor.toDouble() * seguro.porcentaje(this)

  //1.-
  abstract fun puedeContratar(seguro: Seguro): Boolean

  //2.-
  open fun cuantoCuesta(seguro: Seguro): Double? {
    if (this.puedeContratar(seguro)) {
      return this.costoPara(seguro)
    } else {
      throw error("El seguro no puede aplicarse a este vehiculo")}
    }

  //3.-
  fun contratar(seguro: Seguro) {
    if (this.puedeContratar(seguro)) {
      seguros.add(seguro)
    } else {
      throw error("El seguro no puede ser contratado para este vehiculo")}
  }

  //4.-
  fun costoTotalDeSeguros(): Int = seguros.sumBy { seguro -> this.costoPara(seguro).toInt() }

  abstract fun porcentajeContraTercero(): Double
  open fun porcentajeContraRobo(): Double = 0.05
  open fun porcentajeTodoRiesgo(): Double = 0.0

  fun seguroContraTercero(): Boolean = true
  open fun seguroContraRobo(): Boolean = true
  open fun seguroContraTodoRiesgo(): Boolean = false
}

class Auto(valor: Int, antiguedad: Int) : Vehiculo(valor, antiguedad) {

  val anioActual = 2020
  val anioFabricacion = anioActual - antiguedad

  override fun porcentajeContraTercero(): Double = 0.01

  override fun porcentajeContraRobo(): Double {
    val porcentaje = when {
      anioFabricacion <= 1995 -> 0.03
      else -> 0.05
    }
    return porcentaje
  }

  override fun porcentajeTodoRiesgo(): Double = 0.07

  override fun seguroContraTodoRiesgo(): Boolean = true

  override fun puedeContratar(seguro: Seguro): Boolean = seguro.puedeContratar(this)
}

class Camion(valor: Int, antiguedad: Int) : Vehiculo(valor, antiguedad) {

  val antiguedadQueNoSeAsegura = 10

  override fun porcentajeContraTercero(): Double {
    val porcentaje = when {
      antiguedad > 10 -> 0.02
      else -> 0.015
    }
    return porcentaje
  }

  override fun seguroContraRobo(): Boolean = !(this.antiguedad > antiguedadQueNoSeAsegura)

  override fun puedeContratar(seguro: Seguro): Boolean = seguro.puedeContratar(this)
}

class Taxi(valor: Int, antiguedad: Int,val tuvoInfracciones: Boolean) : Vehiculo(valor, antiguedad) {

  val antiguedadQueNoSeAsegura = 12

  val recargo = 1000

  //fun tuvoInfracciones() = true

  override fun porcentajeContraTercero(): Double = 0.02

  override fun costoPara(seguro: Seguro): Double = super.costoPara(seguro) + (if (this.tuvoInfracciones) recargo else 0)

  override fun seguroContraRobo(): Boolean = !(this.antiguedad > antiguedadQueNoSeAsegura)

  override fun puedeContratar(seguro: Seguro): Boolean = seguro.puedeContratar(this)
}
