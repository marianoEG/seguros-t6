package ar.edu.unahur.obj2.seguros

interface Seguro {
  fun costoPara(vehiculo: Vehiculo): Double

  fun puedeContratar(vehiculo: Vehiculo): Boolean

  fun porcentaje(vehiculo: Vehiculo): Double
}

class ContraTerceros : Seguro {
  override fun costoPara(vehiculo: Vehiculo): Double = vehiculo.costoPara(this)

  override fun porcentaje(vehiculo: Vehiculo): Double = vehiculo.porcentajeContraTercero()

  override fun puedeContratar(vehiculo: Vehiculo): Boolean = vehiculo.seguroContraTercero()
}

class RoboHurto: Seguro {
  override fun costoPara(vehiculo: Vehiculo): Double = vehiculo.costoPara(this)

  override fun porcentaje(vehiculo: Vehiculo): Double = vehiculo.porcentajeContraRobo()

  override fun puedeContratar(vehiculo: Vehiculo): Boolean = vehiculo.seguroContraRobo()
}

class TodoRiesgo: Seguro {

  override fun puedeContratar(vehiculo: Vehiculo): Boolean = vehiculo.seguroContraTodoRiesgo()

  override fun porcentaje(vehiculo: Vehiculo): Double = vehiculo.porcentajeTodoRiesgo()

  override fun costoPara(vehiculo: Vehiculo): Double = vehiculo.costoPara(this)
}
